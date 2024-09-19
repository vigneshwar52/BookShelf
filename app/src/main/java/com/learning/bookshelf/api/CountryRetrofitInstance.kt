package com.learning.bookshelf.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CountryRetrofitInstance {
    private const val BASE_URL = "https://www.jsonkeeper.com/b/"

    val api: CountryListAPI by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CountryListAPI::class.java)
    }
}