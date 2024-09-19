package com.learning.bookshelf.ui.booklistview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learning.bookshelf.api.BookRetrofitInstance
import com.learning.bookshelf.model.Book
import com.learning.bookshelf.util.DateUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class BookListUiState(
    val books: List<Book> = emptyList(),
    val favorites: Set<String> = emptySet(),
    val selectedYear: Int? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class BookListViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(BookListUiState())
    val uiState: StateFlow<BookListUiState> = _uiState

    init {
        fetchBooks()
    }

    private fun fetchBooks() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            try {
                val books = BookRetrofitInstance.api.getBooks()

                val booksByYear = books.groupBy { DateUtils.unixTimestampToYear(it.publishedChapterDate) }
                val sortedBooks = books.sortedByDescending { DateUtils.unixTimestampToYear(it.publishedChapterDate) }

                _uiState.value = _uiState.value.copy(books = sortedBooks, isLoading = false)

                if (sortedBooks.isNotEmpty()) {
                    val latestYear = DateUtils.unixTimestampToYear(sortedBooks.first().publishedChapterDate)
                    _uiState.value = _uiState.value.copy(selectedYear = latestYear)
                }

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message, isLoading = false)
            }
        }
    }

    fun toggleFavorite(bookId: String) {
        val updatedFavorites = _uiState.value.favorites.toMutableSet()
        if (updatedFavorites.contains(bookId)) {
            updatedFavorites.remove(bookId)
        } else {
            updatedFavorites.add(bookId)
        }
        _uiState.value = _uiState.value.copy(favorites = updatedFavorites)
    }

    fun setSelectedYear(year: Int) {
        _uiState.value = _uiState.value.copy(selectedYear = year)
    }
}