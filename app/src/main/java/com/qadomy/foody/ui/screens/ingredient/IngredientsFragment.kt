package com.qadomy.foody.ui.screens.ingredient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.qadomy.foody.R
import com.qadomy.foody.adapter.IngredientAdapter
import com.qadomy.foody.model.Result
import com.qadomy.foody.utils.Constants
import com.qadomy.foody.utils.Constants.Companion.PARCELABLE_KEY
import kotlinx.android.synthetic.main.fragment_ingredients.view.*

class IngredientsFragment : Fragment() {

    private val mAdapter:IngredientAdapter by lazy { IngredientAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_ingredients, container, false)

        // read data from details activity
        val args = arguments
        val myBundle: Result? = args?.getParcelable(PARCELABLE_KEY)

        setupRecyclerView(view)
        myBundle?.extendedIngredients?.let { mAdapter.setData(it) }

        return  view
    }


    /** function for setup recycle view for ingredient list */
    private fun setupRecyclerView(view: View) {
        view.ingredients_recyclerview.adapter = mAdapter
        view.ingredients_recyclerview.layoutManager = LinearLayoutManager(requireContext())
    }
}