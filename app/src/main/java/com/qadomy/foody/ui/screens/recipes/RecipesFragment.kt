package com.qadomy.foody.ui.screens.recipes

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.qadomy.foody.R
import com.qadomy.foody.adapter.RecipeAdapter
import com.qadomy.foody.databinding.FragmentRecipesBinding
import com.qadomy.foody.utils.NetworkListener
import com.qadomy.foody.utils.NetworkResult
import com.qadomy.foody.utils.observeOnce
import com.qadomy.foody.viewmodels.MainViewModel
import com.qadomy.foody.viewmodels.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class RecipesFragment : Fragment(), SearchView.OnQueryTextListener {

    // args -> get data from navigation
    private val args by navArgs<RecipesFragmentArgs>()

    // binding
    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private lateinit var recipesViewModel: RecipesViewModel
    private val mAdapter by lazy { RecipeAdapter() }


    private lateinit var networkListener: NetworkListener

    // onDestroy, _binding -> null
    override fun onDestroy() {
        super.onDestroy()
        /**
         * we set null to _binding because this avoid the memory leak
         */
        _binding = null
    }


    // onCreate method called before onCreateView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // init main view model
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        // init recipes view model
        recipesViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        _binding = FragmentRecipesBinding.inflate(inflater, container, false)

        // use this cause we using live data object
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel

        setupRecyclerView()

        recipesViewModel.readBackOnline.observe(viewLifecycleOwner, {
            recipesViewModel.backOnline = it
        })

        // init network listener
        lifecycleScope.launch {
            networkListener = NetworkListener()
            networkListener.checkNetworkAvailability(requireContext())
                .collect { status ->
                    Log.d("NetworkListener", status.toString())
                    recipesViewModel.networkStatus = status
                    recipesViewModel.showNetworkStatus()
                    readDatabase()
                }
        }


        // enable menu
        setHasOptionsMenu(true)

        // navigate to bottom sheet when click on fab button
        binding.recipesFab.setOnClickListener {
            if (recipesViewModel.networkStatus) {
                findNavController().navigate(R.id.action_recipesFragment_to_recipesBottomSheet)
            } else {
                recipesViewModel.showNetworkStatus()
            }
        }

        return binding.root
    }


    // region Menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.recipe_menu, menu)

        // set search icon
        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)

    }


    // on Query Text Listener
    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null)
            searchApiData(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    // endregion


    /*
    function for read data from database, and check if database is empty
    then we request Api Data from internet
     */
    private fun readDatabase() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observeOnce(viewLifecycleOwner, { database ->
                if (database.isNotEmpty() && !args.backFromBottomSheet) {
                    Log.d("RecipesFragment", "read from database")
                    mAdapter.setData(database[0].foodRecipe)
                    hideShimmerEffect()
                } else {
                    requestApiData()
                }
            })
        }
    }


    // function for request data from API
    private fun requestApiData() {
        Log.d("RecipesFragment", "read from Api request")
        mainViewModel.getRecipes(recipesViewModel.applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    /**
                     * if Network Result is success, hide shimmer effect, and set data to adapter
                     */
                    hideShimmerEffect()
                    response.data?.let { mAdapter.setData(it) }
                }

                is NetworkResult.Error -> {
                    /**
                     * if Network Result is error, hide shimmer effect, and show toast message
                     */
                    hideShimmerEffect()
                    loadDataFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is NetworkResult.Loading -> {
                    /**
                     * if Network Result is loading, show shimmer effect
                     */
                    showShimmerEffect()
                }
            }
        })
    }

    /** function for search data from API */
    private fun searchApiData(searchQuery: String) {
        showShimmerEffect()
        mainViewModel.searchRecipes(recipesViewModel.applySearchQuery(searchQuery))
        mainViewModel.searchRecipesResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    val foodRecipe = response.data
                    foodRecipe?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    loadDataFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> showShimmerEffect()
            }
        })
    }

    // function for load data from database
    private fun loadDataFromCache() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observe(viewLifecycleOwner, { database ->
                if (database.isNotEmpty()) {
                    mAdapter.setData(database[0].foodRecipe)
                }
            })
        }
    }


    // function for setup recyclerView with adapter and show shimmer effect before fetch data from API
    private fun setupRecyclerView() {
        binding.recyclerView.adapter = mAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }


    // function for show shimmer effect before loading
    private fun showShimmerEffect() {
        binding.recyclerView.showShimmer()
    }

    // function for hide shimmer effect after loading
    private fun hideShimmerEffect() {
        binding.recyclerView.hideShimmer()
    }

}












