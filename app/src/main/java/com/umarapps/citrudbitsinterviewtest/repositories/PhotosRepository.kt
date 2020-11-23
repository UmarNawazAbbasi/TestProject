package com.umarapps.citrudbitsinterviewtest.repositories

import androidx.lifecycle.MutableLiveData
import com.umarapps.citrudbitsinterviewtest.Interfaces.NetworkResponseCallback
import com.umarapps.citrudbitsinterviewtest.Network.RestClient
import com.umarapps.citrudbitsinterviewtest.models.PhotosItem

import com.umarapps.citrudbitsinterviewtest.models.UsersItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhotosRepository private constructor() {
    private lateinit var mCallback: NetworkResponseCallback
    private var mUsersList: MutableLiveData<List<PhotosItem>> =
        MutableLiveData<List<PhotosItem>>().apply { value = emptyList() }

    companion object {
        private var mInstance: PhotosRepository? = null
        fun getInstance(): PhotosRepository {
            if (mInstance == null) {
                synchronized(this) {
                    mInstance = PhotosRepository()
                }
            }
            return mInstance!!
        }
    }


    private lateinit var mUsersCall: Call<List<PhotosItem>>

    fun getPhotos(callback: NetworkResponseCallback, forceFetch : Boolean,albumId:Int): MutableLiveData<List<PhotosItem>> {
        mCallback = callback
        if (mUsersList.value!!.isNotEmpty() && !forceFetch) {
            mCallback.onNetworkSuccess()
            return mUsersList
        }
        mUsersCall = RestClient.getInstance().getApiService().getAPhotos(albumId)
        mUsersCall.enqueue(object : Callback<List<PhotosItem>> {

            override fun onResponse(call: Call<List<PhotosItem>>, response: Response<List<PhotosItem>>) {
                mUsersList.value = response.body()
                mCallback.onNetworkSuccess()
            }

            override fun onFailure(call: Call<List<PhotosItem>>, t: Throwable) {
                mUsersList.value = emptyList()
                mCallback.onNetworkFailure(t)
            }

        })
        return mUsersList
    }
}