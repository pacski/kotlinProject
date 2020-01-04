package com.example.goodbyeworld

import retrofit2.Response
import retrofit2.http.GET


interface UserService {
    @GET("users/info")
    suspend fun getInfo(): Response<UserInfo>

}