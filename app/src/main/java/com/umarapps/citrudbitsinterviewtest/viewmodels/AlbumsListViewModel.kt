package com.umarapps.citrudbitsinterviewtest.viewmodels

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.UmarApps.apps.mvvmcountries.utils.NetworkHelper

import com.umarapps.citrudbitsinterviewtest.Interfaces.NetworkResponseCallback
import com.umarapps.citrudbitsinterviewtest.models.AlbumsItem

import com.umarapps.citrudbitsinterviewtest.repositories.AlbumsRepository


class AlbumsListViewModel(private val app: Application) : AndroidViewModel(app) {
    private var mList: MutableLiveData<List<AlbumsItem>> =
        MutableLiveData<List<AlbumsItem>>().apply { value = emptyList() }
    val mShowProgressBar = MutableLiveData(true)
    val mShowNetworkError: MutableLiveData<Boolean> = MutableLiveData()
    val mShowApiError = MutableLiveData<String>()
    private var mRepository = AlbumsRepository.getInstance()

    fun fetchCountriesFromServer(forceFetch: Boolean,userId:Int): MutableLiveData<List<AlbumsItem>> {
        if (NetworkHelper.isOnline(app.baseContext)) {
            mShowProgressBar.value = true
            mList = mRepository.getAlbums(object : NetworkResponseCallback {
                override fun onNetworkFailure(th: Throwable) {
                    mShowApiError.value = th.message
                }

                override fun onNetworkSuccess() {
                    mShowProgressBar.value = false
                }
            }, forceFetch,userId)
        } else {
            mShowNetworkError.value = true
        }
        return mList
    }

    fun onRefreshClicked(view: View) {
        fetchCountriesFromServer(true,1)
    }
}