package com.example.book_inventory_project.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.book_inventory_project.data.Book
import com.example.book_inventory_project.data.BookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BookViewModel(private val repository: BookRepository) : ViewModel() {
    
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    
    val booksUiState: StateFlow<BooksUiState> = repository.allBooks
        .map<List<Book>, BooksUiState> { BooksUiState.Success(it) }
        .catch { exception -> 
            // Return the Error state directly
            BooksUiState.Error(exception.message ?: "Unknown error")
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = BooksUiState.Loading
        )
    
    val searchResults: StateFlow<BooksUiState> = searchQuery
        .flatMapLatest { query ->
            if (query.isBlank()) {
                repository.allBooks
            } else {
                repository.searchBooks(query)
            }
        }
        .map<List<Book>, BooksUiState> { BooksUiState.Success(it) }
        .catch { exception -> 
            // Return the Error state directly
            BooksUiState.Error(exception.message ?: "Unknown error")
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = BooksUiState.Loading
        )
    
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
    
    fun getBookById(id: Int): Flow<Book> {
        return repository.getBookById(id)
    }
    
    fun addBook(book: Book) {
        viewModelScope.launch {
            repository.insertBook(book)
        }
    }
    
    fun updateBook(book: Book) {
        viewModelScope.launch {
            repository.updateBook(book)
        }
    }
    
    fun deleteBook(book: Book) {
        viewModelScope.launch {
            repository.deleteBook(book)
        }
    }
}

sealed class BooksUiState {
    data object Loading : BooksUiState()
    data class Success(val books: List<Book>) : BooksUiState()
    data class Error(val message: String) : BooksUiState()
}

class BookViewModelFactory(private val repository: BookRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BookViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}