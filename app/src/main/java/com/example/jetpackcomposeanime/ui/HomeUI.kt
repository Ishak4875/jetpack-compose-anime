package com.example.jetpackcomposeanime.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.asFlow
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.jetpackcomposeanime.R
import com.example.jetpackcomposeanime.core.data.Resource
import com.example.jetpackcomposeanime.core.domain.model.Anime
import com.example.jetpackcomposeanime.main.MainViewModel
import com.example.jetpackcomposeanime.ui.theme.BackgroundUI
import com.example.jetpackcomposeanime.ui.theme.Blue500
import com.example.jetpackcomposeanime.ui.theme.Blue700
import com.example.jetpackcomposeanime.ui.theme.JetpackComposeAnimeTheme


@Composable
fun HomeUI(navController: NavController) {
    val textStyle = MaterialTheme.typography.titleLarge
    val textSize = textStyle.fontSize
    val mainViewModel: MainViewModel = hiltViewModel()
    val trendingListState = mainViewModel.trending.observeAsState()
    val context = LocalContext.current

    val animePagingDataFlow = mainViewModel.anime.asFlow()
    val animeItems = animePagingDataFlow.collectAsLazyPagingItems()

    var showSearchDialog by remember { mutableStateOf(false) }
    JetpackComposeAnimeTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                BackgroundUI()
                Column(
                    modifier = Modifier
                        .padding(start = 4.dp, top = 0.dp, end = 4.dp, bottom = 8.dp)
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.Start,
                ) {
                    TopAppBarUI(navController)
                    Spacer(modifier = Modifier.padding(top = 8.dp))
                    Row(
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                    ) {
                        Text(
                            text = "Trending Anime",
                            style = textStyle,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.ic_fire),
                            contentDescription = null,
                            modifier = Modifier.size(textSize.value.dp),
                            tint = Color.Magenta
                        )
                    }
                    trendingListState.value?.let { resource ->
                        when (resource) {
                            is Resource.Loading -> {
                                LazyRow {
                                    items(8) {
                                        PlaceHolderDataMinSize()
                                    }
                                }
                            }

                            is Resource.Success -> {
                                TrendingList(context, trendingList = resource.data!!)
                            }

                            is Resource.Error -> {
                                LaunchedEffect(resource.message) {
                                    Toast.makeText(context, resource.message, Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                        }
                    }
                    Text(
                        text = "Anime",
                        style = textStyle,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                    )
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(count = animeItems.itemCount) { index ->
                            val anime = animeItems[index]
                            AnimeMaxItem(context = context, anime = anime!!)
                        }

                        animeItems.apply {
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
                                                color = Blue700
                                            )
                                        }
                                    }
                                }

                                loadState.refresh is LoadState.Error -> {
                                    val e =
                                        animeItems.loadState.refresh as LoadState.Error
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
                                        animeItems.loadState.append as LoadState.Error
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
            if (showSearchDialog) {
                ShowSearchScreenView(navController)
            }
        }
    }
}

@Composable
fun AnimeList(context: Context, animeList: List<Anime>) {
    LazyColumn {
        itemsIndexed(items = animeList) { index, item ->
            AnimeMaxItem(context = context, anime = item)
        }
    }
}

@Composable
fun TrendingList(context: Context, trendingList: List<Anime>) {
    LazyRow {
        itemsIndexed(items = trendingList) { index, item ->
            AnimeMinItem(context = context, anime = item)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarUI(navController: NavController) {
    val textStyle = MaterialTheme.typography.headlineSmall
    val textSize = textStyle.fontSize

    var isSearching by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    var searchBarHeight by remember { mutableStateOf(0) }

    var showBottomSheet by remember { mutableStateOf(false) }

    var items = remember {
        mutableListOf(
            "Fairy Tail",
            "One Piece"
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp)
    ) {
        SearchBar(
            query = searchText,
            onQueryChange = {
                searchText = it
            },
            onSearch = {
                items.add(searchText)
                isSearching = false
                navController.navigate("search")
            },
            active = isSearching,
            onActiveChange = {
                isSearching = it
                if (isSearching) {
                    navController.navigate("search")
                }
            },
            placeholder = { Text("Search...", color = Color.Gray) },
            leadingIcon = {
                IconButton(onClick = { isSearching = false; searchText = "" }) {
                    Icon(Icons.Filled.Search, contentDescription = "Back", tint = Blue500)
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
                .background(color = Color.White, shape = RoundedCornerShape(24.dp))
                .padding(all = 0.dp)
                .weight(0.8f)
                .onGloballyPositioned { coordinates ->
                    searchBarHeight = coordinates.size.height
                },
            colors = SearchBarDefaults.colors(
                containerColor = Color.Transparent,
                inputFieldColors = TextFieldDefaults.textFieldColors(
                    focusedTextColor = Blue500,
                    unfocusedTextColor = Blue500
                )
            )
        ) {

        }
        IconButton(
            onClick = { showBottomSheet = true },
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_filter),
                contentDescription = "Filter",
                modifier = Modifier
                    .size(searchBarHeight.dp)
                    .weight(0.2f)
                    .align(Alignment.CenterVertically),
                tint = Color.White
            )
        }

        if (showBottomSheet) {
            BottomSheetFilterForm(
                showBottomSheet = showBottomSheet,
                onDismiss = { showBottomSheet = false }
            )
        }
    }
}


