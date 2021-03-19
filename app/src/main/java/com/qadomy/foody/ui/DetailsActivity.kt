package com.qadomy.foody.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.qadomy.foody.R
import com.qadomy.foody.adapter.PagerAdapter
import com.qadomy.foody.ui.screens.ingredient.IngredientsFragment
import com.qadomy.foody.ui.screens.instructions.InstructionsFragment
import com.qadomy.foody.ui.screens.overview.OverViewFragment
import com.qadomy.foody.utils.Constants.Companion.PARCELABLE_KEY
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    // create safe args
    private val args by navArgs<DetailsActivityArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // create list of fragments
        val fragments = ArrayList<Fragment>()
        fragments.add(OverViewFragment())
        fragments.add(IngredientsFragment())
        fragments.add(InstructionsFragment())

        // create list of titles
        val titles = ArrayList<String>()
        titles.add(getString(R.string.overview))
        titles.add(getString(R.string.ingredients))
        titles.add(getString(R.string.instructions))

        // create bundle
        val resultBundle = Bundle()
        resultBundle.putParcelable(PARCELABLE_KEY, args.result)

        // create pager adapter
        val adapter = PagerAdapter(resultBundle, fragments, titles, supportFragmentManager)

        viewPager.adapter =adapter
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu,menu)
        return true
    }

    // finish activity when click on back icon
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}