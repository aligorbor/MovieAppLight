package ru.geekbrains.android2.movieapplight.model.rest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BackendRepo {
    val api: BackendAPI by lazy {
        val adapter = Retrofit.Builder()
            .baseUrl(ApiUtils.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(ApiUtils.getOkHTTPBuilderWithHeaders())
            .build()
        adapter.create(BackendAPI::class.java)
    }
}