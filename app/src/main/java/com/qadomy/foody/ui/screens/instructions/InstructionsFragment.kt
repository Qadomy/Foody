package com.qadomy.foody.ui.screens.instructions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.qadomy.foody.R
import com.qadomy.foody.model.Result
import com.qadomy.foody.utils.Constants
import com.qadomy.foody.utils.Constants.Companion.PARCELABLE_KEY
import kotlinx.android.synthetic.main.fragment_instructions.view.*

class InstructionsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view  = inflater.inflate(R.layout.fragment_instructions, container, false)

        // read data from details activity
        val args = arguments
        val myBundle: Result? = args?.getParcelable(PARCELABLE_KEY)

        view.instructions_webView.webViewClient = object : WebViewClient() {}
        val websiteUrl: String = myBundle!!.sourceUrl
        view.instructions_webView.loadUrl(websiteUrl)

        return  view
    }

}