package com.example.jetpackcomposeanime.ui

import android.content.Context
import android.widget.Toast
import androidx.activity.viewModels
import androidx.compose.foundation.background
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
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.CircularProgressIndicator
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetpackcomposeanime.core.data.Resource
import com.example.jetpackcomposeanime.core.domain.model.Anime
import com.example.jetpackcomposeanime.main.MainViewModel
import com.example.jetpackcomposeanime.ui.theme.Blue200
import com.example.jetpackcomposeanime.ui.theme.Blue700
import com.example.jetpackcomposeanime.ui.theme.JetpackComposeAnimeTheme
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.jetpackcomposeanime.R
import com.example.jetpackcomposeanime.ui.theme.BackgroundUI
import com.example.jetpackcomposeanime.ui.theme.Blue500

@Composable
fun HomeUI() {
    val mainViewModel: MainViewModel = hiltViewModel()
    val trendingListState = mainViewModel.trending.observeAsState()
    val animeListState = mainViewModel.anime.observeAsState()
    val context = LocalContext.current

    val textStyle = MaterialTheme.typography.titleLarge
    val textSize = textStyle.fontSize
    JetpackComposeAnimeTheme {
        Surface {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                BackgroundUI()
                Column(
                    modifier = Modifier
                        .padding(4.dp, 8.dp)
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Row {
                        Text(
                            text = "Trending Anime",
                            style = textStyle,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
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
                                CircularProgressIndicator(
                                    modifier = Modifier.align(Alignment.CenterHorizontally),
                                    color = Color.White
                                )
                            }

                            is Resource.Success -> {
                                TrendingList(context,trendingList = resource.data!!)
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
                        color = Color.Black
                    )
                    animeListState.value?.let { resource ->
                        when (resource) {
                            is Resource.Loading -> {
                                CircularProgressIndicator(
                                    modifier = Modifier.align(Alignment.CenterHorizontally),
                                    color = Color.White
                                )
                            }

                            is Resource.Success -> {
                                AnimeList(context,animeList = resource.data!!)
                            }

                            is Resource.Error -> {
                                LaunchedEffect(resource.message) {
                                    Toast.makeText(context, resource.message, Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AnimeList(context: Context,animeList: List<Anime>) {
    LazyColumn {
        itemsIndexed(items = animeList) { index, item ->
            AnimeMaxItem(context, anime = item)
        }
    }
}

@Composable
fun TrendingList(context: Context,trendingList: List<Anime>) {
    LazyRow {
        itemsIndexed(items = trendingList) { index, item ->
            AnimeMinItem(context = context, anime = item)
        }
    }
}