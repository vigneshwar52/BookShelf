package com.learning.bookshelf.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CountryRetrofitInstance {
    private var retrofit: Retrofit? = null

    private fun getRetrofit() : Retrofit{
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl("https://www.jsonkeeper.com/b/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }
    val countryApi:CountryListAPI
        get(){
            if (retrofit == null) {
                retrofit = getRetrofit()
            }
            return retrofit!!.create(CountryListAPI::class.java)
        }
}