package com.learning.bookshelf.api

import com.learning.bookshelf.model.CountryList

class Repository {
    private val countryAPI = CountryRetrofitInstance.createService(CountryListAPI::class.java)

    suspend fun getCountriesFromApi(): CountryList {
        return countryAPI.getAllCountries()
    }
}