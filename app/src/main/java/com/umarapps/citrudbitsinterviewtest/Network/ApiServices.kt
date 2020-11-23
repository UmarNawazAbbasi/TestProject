package com.umarapps.citrudbitsinterviewtest.Network

import com.umarapps.citrudbitsinterviewtest.models.AlbumsItem
import com.umarapps.citrudbitsinterviewtest.models.PhotosItem
import com.umarapps.citrudbitsinterviewtest.models.UsersItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiServices {
    @GET("users")
    fun getUsers() : Call<List<UsersItem>>
    @GET("albums")
    fun getAlbums(@Query("userId") id: Int) : Call<List<AlbumsItem>>
    @GET("photos")
    fun getAPhotos(@Query("albumId") id: Int) : Call<List<PhotosItem>>

}