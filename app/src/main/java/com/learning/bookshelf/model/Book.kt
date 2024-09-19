package com.learning.bookshelf.model

import com.google.gson.annotations.SerializedName

data class Book(
    @SerializedName("id") val id: String,
    @SerializedName("image") val image: String,
    @SerializedName("score") val score: Double,
    @SerializedName("popularity") val popularity: Int,
    @SerializedName("title") val title: String,
    @SerializedName("publishedChapterDate") val publishedChapterDate: Long
)