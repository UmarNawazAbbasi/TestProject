package com.umarapps.citrudbitsinterviewtest.viewmodels

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.UmarApps.apps.mvvmcountries.utils.NetworkHelper
import com.umarapps.citrudbitsinterviewtest.Interfaces.NetworkResponseCallback
import com.umarapps.citrudbitsinterviewtest.models.UsersItem
import com.umarapps.citrudbitsinterviewtest.repositories.UsersRepository

class UsersListViewModel(private val app: Application) : AndroidViewModel(app) {
    private var mList: MutableLiveData<List<UsersItem>> =
        MutableLiveData<List<UsersItem>>().apply { value = emptyList() }
    val mShowProgressBar = MutableLiveData(true)
    val mShowNetworkError: MutableLiveData<Boolean> = MutableLiveData()
    val mShowApiError = MutableLiveData<String>()
    private var mRepository = UsersRepository.getInstance()

    fun fetchCountriesFromServer(forceFetch: Boolean): MutableLiveData<List<UsersItem>> {
        if (NetworkHelper.isOnline(app.baseContext)) {
            mShowProgressBar.value = true
            mList = mRepository.getCountries(object : NetworkResponseCallback {
                override fun onNetworkFailure(th: Throwable) {
                    mShowApiError.value = th.message
                }

                override fun onNetworkSuccess() {
                    mShowProgressBar.value = false
                }
            }, forceFetch)
        } else {
            mShowNetworkError.value = true
        }
        return mList
    }

    fun onRefreshClicked(view: View) {
        fetchCountriesFromServer(true)
    }
}