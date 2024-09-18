package com.learning.bookshelf.api

import com.learning.bookshelf.model.CountryList
import retrofit2.http.GET

interface CountryListAPI {
    @GET("IU1K")
    suspend fun getAllCountries():CountryList
}