package com.umarapps.citrudbitsinterviewtest.viewmodels

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.UmarApps.apps.mvvmcountries.utils.NetworkHelper
import com.umarapps.citrudbitsinterviewtest.Interfaces.NetworkResponseCallback
import com.umarapps.citrudbitsinterviewtest.models.PhotosItem


import com.umarapps.citrudbitsinterviewtest.repositories.AlbumsRepository
import com.umarapps.citrudbitsinterviewtest.repositories.PhotosRepository


class PhotoListViewModel(private val app: Application) : AndroidViewModel(app) {
    private var mList: MutableLiveData<List<PhotosItem>> =
        MutableLiveData<List<PhotosItem>>().apply { value = emptyList() }
    val mShowProgressBar = MutableLiveData(true)
    val mShowNetworkError: MutableLiveData<Boolean> = MutableLiveData()
    val mShowApiError = MutableLiveData<String>()
    private var mRepository = PhotosRepository.getInstance()

    fun fetchCountriesFromServer(forceFetch: Boolean,albumId:Int): MutableLiveData<List<PhotosItem>> {
        if (NetworkHelper.isOnline(app.baseContext)) {
            mShowProgressBar.value = true
            mList = mRepository.getPhotos(object : NetworkResponseCallback {
                override fun onNetworkFailure(th: Throwable) {
                    mShowApiError.value = th.message
                }

                override fun onNetworkSuccess() {
                    mShowProgressBar.value = false
                }
            }, forceFetch,albumId)
        } else {
            mShowNetworkError.value = true
        }
        return mList
    }

    fun onRefreshClicked(view: View) {
        fetchCountriesFromServer(true,1)
    }
}