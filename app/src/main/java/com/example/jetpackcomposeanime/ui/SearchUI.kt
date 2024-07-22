package com.example.jetpackcomposeanime.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.asFlow
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.jetpackcomposeanime.search.SearchViewModel
import com.example.jetpackcomposeanime.ui.theme.Blue500
import com.example.jetpackcomposeanime.ui.theme.JetpackComposeAnimeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowSearchScreenView(navController: NavController) {
    JetpackComposeAnimeTheme {
        Surface(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            SearchScreenView(navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreenView(navController: NavController) {
    val searchViewModel: SearchViewModel = hiltViewModel()

    var isSearching by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }

    val context = LocalContext.current

    val searchingAnimePagingDataFlow = searchViewModel.searchingAnime(searchText).asFlow()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Blue500)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .padding(horizontal = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }

                val textStyle = MaterialTheme.typography.headlineSmall
                val textSize = textStyle.fontSize

                var items = remember {
                    mutableListOf(
                        "Fairy Tail",
                        "One Piece"
                    )
                }

                SearchBar(
                    query = searchText,
                    onQueryChange = {
                        searchText = it
                    },
                    onSearch = {
                        isSearching = false
                    },
                    active = isSearching,
                    onActiveChange = { isSearching = it },
                    placeholder = { Text("Search...", color = Color.Gray) },
                    leadingIcon = {
                        IconButton(onClick = {
                            isSearching = false;
                        }) {
                            Icon(
                                Icons.Filled.Search,
                                contentDescription = "Back",
                                tint = Blue500
                            )
                        }
                    },
                    trailingIcon = {
                        IconButton(onClick = { searchText = "" }) {
                            Icon(
                                modifier = Modifier.clickable {
                                    if (searchText.isNotEmpty()) {
                                        searchText = ""
                                    } else {
                                        isSearching = false
                                    }
                                },
                                imageVector = Icons.Filled.Clear,
                                contentDescription = "Clear",
                                tint = Blue500
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth() // Pastikan SearchBar memenuhi lebar Row
                        .heightIn(max = 70.dp) // Batasan tinggi maksimal
                        .background(color = Color.White, shape = RoundedCornerShape(24.dp)),
                    colors = SearchBarDefaults.colors(
                        containerColor = Color.Transparent,
                        inputFieldColors = TextFieldDefaults.textFieldColors(
                            focusedTextColor = Blue500,
                            unfocusedTextColor = Blue500
                        )
                    )
                ) {
                    // Untuk History
                }
            }

            Spacer(modifier = Modifier.padding(top = 8.dp))
            val searchingItems = searchingAnimePagingDataFlow.collectAsLazyPagingItems()
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(count = searchingItems.itemCount) { index ->
                    val searchingAnime = searchingItems[index]
                    AnimeMaxItem(context = context, anime = searchingAnime!!)
                }

                searchingItems.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            items(8) {
                                PlaceHolderDataMaxSize()
                            }
                        }

                        loadState.append is LoadState.Loading -> {
                            item {
                                Box(
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.align(Alignment.Center),
                                        color = Color.White
                                    )
                                }
                            }
                        }

                        loadState.refresh is LoadState.Error -> {
                            val e =
                                searchingItems.loadState.refresh as LoadState.Error
                            item {
                                Box(
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        "Error: ${e.error}",
                                        modifier = Modifier.align(Alignment.Center),
                                        color = Color.Red
                                    )
                                }
                            }
                        }

                        loadState.append is LoadState.Error -> {
                            val e =
                                searchingItems.loadState.append as LoadState.Error
                            item {
                                Box(
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        "Error: ${e.error}",
                                        modifier = Modifier.align(Alignment.Center),
                                        color = Color.Red
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
