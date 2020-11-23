package com.umarapps.citrudbitsinterviewtest.repositories

import androidx.lifecycle.MutableLiveData
import com.umarapps.citrudbitsinterviewtest.Interfaces.NetworkResponseCallback
import com.umarapps.citrudbitsinterviewtest.Network.RestClient
import com.umarapps.citrudbitsinterviewtest.models.UsersItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersRepository private constructor() {
    private lateinit var mCallback: NetworkResponseCallback
    private var mUsersList: MutableLiveData<List<UsersItem>> =
        MutableLiveData<List<UsersItem>>().apply { value = emptyList() }

    companion object {
        private var mInstance: UsersRepository? = null
        fun getInstance(): UsersRepository {
            if (mInstance == null) {
                synchronized(this) {
                    mInstance = UsersRepository()
                }
            }
            return mInstance!!
        }
    }


    private lateinit var mUsersCall: Call<List<UsersItem>>

    fun getCountries(callback: NetworkResponseCallback, forceFetch : Boolean): MutableLiveData<List<UsersItem>> {
        mCallback = callback
        if (mUsersList.value!!.isNotEmpty() && !forceFetch) {
            mCallback.onNetworkSuccess()
            return mUsersList
        }
        mUsersCall = RestClient.getInstance().getApiService().getUsers()
        mUsersCall.enqueue(object : Callback<List<UsersItem>> {

            override fun onResponse(call: Call<List<UsersItem>>, response: Response<List<UsersItem>>) {
                mUsersList.value = response.body()
                mCallback.onNetworkSuccess()
            }

            override fun onFailure(call: Call<List<UsersItem>>, t: Throwable) {
                mUsersList.value = emptyList()
                mCallback.onNetworkFailure(t)
            }

        })
        return mUsersList
    }
}