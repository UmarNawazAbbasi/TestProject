package com.umarapps.citrudbitsinterviewtest.repositories

import androidx.lifecycle.MutableLiveData
import com.umarapps.citrudbitsinterviewtest.Interfaces.NetworkResponseCallback
import com.umarapps.citrudbitsinterviewtest.Network.RestClient
import com.umarapps.citrudbitsinterviewtest.models.AlbumsItem
import com.umarapps.citrudbitsinterviewtest.models.UsersItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlbumsRepository private constructor() {
    private lateinit var mCallback: NetworkResponseCallback
    private var mUsersList: MutableLiveData<List<AlbumsItem>> =
        MutableLiveData<List<AlbumsItem>>().apply { value = emptyList() }

    companion object {
        private var mInstance: AlbumsRepository? = null
        fun getInstance(): AlbumsRepository {
            if (mInstance == null) {
                synchronized(this) {
                    mInstance = AlbumsRepository()
                }
            }
            return mInstance!!
        }
    }


    private lateinit var mUsersCall: Call<List<AlbumsItem>>

    fun getAlbums(callback: NetworkResponseCallback, forceFetch : Boolean,userId:Int): MutableLiveData<List<AlbumsItem>> {
        mCallback = callback
        if (mUsersList.value!!.isNotEmpty() && !forceFetch) {
            mCallback.onNetworkSuccess()
            return mUsersList
        }
        mUsersCall = RestClient.getInstance().getApiService().getAlbums(userId)
        mUsersCall.enqueue(object : Callback<List<AlbumsItem>> {

            override fun onResponse(call: Call<List<AlbumsItem>>, response: Response<List<AlbumsItem>>) {
                mUsersList.value = response.body()
                mCallback.onNetworkSuccess()
            }

            override fun onFailure(call: Call<List<AlbumsItem>>, t: Throwable) {
                mUsersList.value = emptyList()
                mCallback.onNetworkFailure(t)
            }

        })
        return mUsersList
    }
}