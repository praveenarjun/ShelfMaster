package com.example.book_inventory_project

import android.app.Application
import com.example.book_inventory_project.data.BookDatabase
import com.example.book_inventory_project.data.BookRepository

class BookApplication : Application() {
    
    private val database by lazy { BookDatabase.getDatabase(this) }
    val repository by lazy { BookRepository(database.bookDao()) }
}