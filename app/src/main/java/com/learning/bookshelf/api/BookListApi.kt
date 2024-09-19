package com.learning.bookshelf.api

import com.learning.bookshelf.model.Book
import retrofit2.http.GET

interface BookListApi {
    @GET("CNGI")
    suspend fun getBooks(): List<Book>
}