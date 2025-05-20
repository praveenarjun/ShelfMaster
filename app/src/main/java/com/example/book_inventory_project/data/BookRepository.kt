package com.example.book_inventory_project.data

import kotlinx.coroutines.flow.Flow

class BookRepository(private val bookDao: BookDao) {
    
    val allBooks: Flow<List<Book>> = bookDao.getAllBooks()
    
    suspend fun insertBook(book: Book) {
        bookDao.insertBook(book)
    }
    
    suspend fun updateBook(book: Book) {
        bookDao.updateBook(book)
    }
    
    suspend fun deleteBook(book: Book) {
        bookDao.deleteBook(book)
    }
    
    fun getBookById(id: Int): Flow<Book> {
        return bookDao.getBookById(id)
    }
    
    fun searchBooks(query: String): Flow<List<Book>> {
        return bookDao.searchBooks(query)
    }
}