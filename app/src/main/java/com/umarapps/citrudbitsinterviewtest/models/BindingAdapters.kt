package com.umarapps.citrudbitsinterviewtest.models

import android.app.Activity
import android.nfc.tech.IsoDep.get
import android.util.Log
import android.webkit.WebView
import android.widget.ImageView
import androidx.databinding.BindingAdapter

import com.umarapps.citrudbitsinterviewtest.R
import kotlinx.android.synthetic.main.fragment_photos_list.*
import java.lang.reflect.Array.get


object BindingAdapters {
    @BindingAdapter("app:imageThumb")
    @JvmStatic
    fun loadImage(webview: WebView, imageUrl: String) {

        if(webview!=null)
        {
        Log.i("Image_url","is: "+imageUrl)
            webview.loadUrl(imageUrl)
        }
//        val bytes: ByteArray = Glide.with(imageView.context)
//            .`as`(ByteArray::class.java)
//            .load(imageUrl)
//            .submit()
//            .get()
//        Glide.with(imageView.context).load(bytes).into(imageView)
    }
}