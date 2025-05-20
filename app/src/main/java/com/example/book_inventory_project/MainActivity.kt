package com.example.book_inventory_project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.book_inventory_project.ui.BookViewModel
import com.example.book_inventory_project.ui.BookViewModelFactory
import com.example.book_inventory_project.ui.navigation.BookNavHost
import com.example.book_inventory_project.ui.theme.Book_Inventory_projectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        val application = application as BookApplication
        val repository = application.repository
        
        setContent {
            Book_Inventory_projectTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BookApp(repository = repository)
                }
            }
        }
    }
}

@Composable
fun BookApp(repository: com.example.book_inventory_project.data.BookRepository) {
    val navController = rememberNavController()
    val viewModel: BookViewModel = viewModel(
        factory = BookViewModelFactory(repository)
    )
    
    BookNavHost(
        navController = navController,
        viewModel = viewModel
    )
}