package com.mpowloka.gsd.data.user

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface UsersApi {

    @GET("/users")
    fun getAllUsers(): Observable<List<UserModel>>

    @GET("users/{username}")
    fun getUser(@Path("username") username: String): Observable<UserModel>

    companion object {

        fun newInstance(): UsersApi {
            return Retrofit.Builder()
                .baseUrl(API_ENDPOINT)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UsersApi::class.java)
        }

        private const val API_ENDPOINT = "https://api.github.com"

    }

}