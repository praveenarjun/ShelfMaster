package com.example.book_inventory_project.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.book_inventory_project.ui.BookViewModel
import com.example.book_inventory_project.ui.BookViewModelFactory
import com.example.book_inventory_project.ui.screens.AddEditBookScreen
import com.example.book_inventory_project.ui.screens.BookDetailScreen
import com.example.book_inventory_project.ui.screens.BookListScreen

sealed class Screen(val route: String) {
    data object BookList : Screen("book_list")
    data object AddBook : Screen("add_book")
    data object EditBook : Screen("edit_book/{bookId}") {
        fun createRoute(bookId: Int) = "edit_book/$bookId"
    }
    data object BookDetail : Screen("book_detail/{bookId}") {
        fun createRoute(bookId: Int) = "book_detail/$bookId"
    }
}

@Composable
fun BookNavHost(
    navController: NavHostController,
    viewModel: BookViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.BookList.route
    ) {
        composable(Screen.BookList.route) {
            val booksUiState by viewModel.booksUiState.collectAsState()
            val searchQuery by viewModel.searchQuery.collectAsState()
            val searchResults by viewModel.searchResults.collectAsState()
            
            BookListScreen(
                booksUiState = if (searchQuery.isBlank()) booksUiState else searchResults,
                onBookClick = { bookId ->
                    navController.navigate(Screen.BookDetail.createRoute(bookId))
                },
                onAddBookClick = {
                    navController.navigate(Screen.AddBook.route)
                },
                onSearchQueryChange = { query ->
                    viewModel.updateSearchQuery(query)
                }
            )
        }
        
        composable(Screen.AddBook.route) {
            AddEditBookScreen(
                onSaveClick = { book ->
                    viewModel.addBook(book)
                    navController.navigateUp()
                },
                onCancelClick = {
                    navController.navigateUp()
                }
            )
        }
        
        composable(
            route = Screen.EditBook.route,
            arguments = listOf(
                navArgument("bookId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getInt("bookId") ?: 0
            AddEditBookScreen(
                bookId = bookId,
                onSaveClick = { book ->
                    viewModel.updateBook(book)
                    navController.navigateUp()
                },
                onCancelClick = {
                    navController.navigateUp()
                }
            )
        }
        
        composable(
            route = Screen.BookDetail.route,
            arguments = listOf(
                navArgument("bookId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getInt("bookId") ?: 0
            BookDetailScreen(
                bookId = bookId,
                viewModel = viewModel,
                onEditClick = { id ->
                    // Navigate to edit screen with the book ID
                    navController.navigate(Screen.EditBook.createRoute(id))
                },
                onDeleteClick = {
                    navController.navigateUp()
                },
                onBackClick = {
                    navController.navigateUp()
                }
            )
        }
    }
}