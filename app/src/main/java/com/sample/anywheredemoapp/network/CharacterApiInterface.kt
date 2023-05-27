package com.sample.anywheredemoapp.network

import com.sample.anywheredemoapp.BuildConfig
import retrofit2.Response
import retrofit2.http.GET

interface CharacterApiInterface {


    @GET(BuildConfig.BACKEND_ENDPOINT)
    suspend fun getData(): Response<CharacterResponse>
}