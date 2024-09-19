package com.learning.bookshelf.ui.booklistview

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.content.MediaType.Companion.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Tab
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.learning.bookshelf.model.Book
import com.learning.bookshelf.ui.booklistview.ui.theme.BookShelfTheme
import com.learning.bookshelf.util.DateUtils

class BookListViewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BookShelfTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting4(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun BookListScreen(
    navController: NavController,
    bookListViewModel: BookListViewModel = viewModel()
) {
    val uiState by bookListViewModel.uiState.collectAsState()
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when {
            uiState.isLoading -> {
                androidx.compose.material3.CircularProgressIndicator(
                    modifier = Modifier.align(
                        Alignment.Center
                    )
                )
            }

            !uiState.errorMessage.isNullOrEmpty() -> {
                Toast.makeText(context, "Error: ${uiState.errorMessage}", Toast.LENGTH_SHORT).show()
            }

            else -> {
                val groupedBooks =
                    uiState.books.groupBy { DateUtils.unixTimestampToYear(it.publishedChapterDate) }
                val availableYears = groupedBooks.keys.sortedDescending()

                Column(modifier = Modifier.fillMaxSize()) {
                    LazyRow(
                        modifier = Modifier.padding(vertical = 8.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        items(availableYears.size) { index ->
                            val year = availableYears[index]
                            val isSelected = uiState.selectedYear == year

                            Tab(
                                selected = isSelected,
                                onClick = { bookListViewModel.setSelectedYear(year) },
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .background(if (isSelected) Color.Gray else Color.Transparent, shape = CircleShape)
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                            ) {
                                Text(text = year.toString(), fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        val booksForSelectedYear = groupedBooks[uiState.selectedYear] ?: emptyList()

                        items(booksForSelectedYear.size) { index ->
                            val book = booksForSelectedYear[index]
                            BookItem(
                                book = book,
                                isFavorite = uiState.favorites.contains(book.id),
                                onFavoriteToggle = { bookListViewModel.toggleFavorite(book.id) }
                            )

                            if (book.publishedChapterDate.toInt() != uiState.selectedYear) {
                                bookListViewModel.setSelectedYear(DateUtils.unixTimestampToYear(book.publishedChapterDate))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BookItem(book: Book, isFavorite: Boolean, onFavoriteToggle: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF7F2F9),
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = book.image),
                contentDescription = "Book Cover",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .padding(8.dp)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text(text = book.title, fontWeight = FontWeight.Bold)
                Text(text = "Score: ${book.score}", color = Color.Gray)
            }
            IconButton(onClick = onFavoriteToggle) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite Toggle",
                    tint = if (isFavorite) Color.Red else Color.Gray
                )
            }
        }
    }
}

@Composable
fun Greeting4(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview4() {
    BookShelfTheme {
        val navController: NavHostController = rememberNavController()
        BookListScreen(navController)
    }
}