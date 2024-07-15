package com.example.jetpackcomposeanime.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetpackcomposeanime.favorite.FavoriteViewModel
import com.example.jetpackcomposeanime.ui.theme.BackgroundUI
import com.example.jetpackcomposeanime.ui.theme.JetpackComposeAnimeTheme

@Composable
fun FavoriteUI() {
    val favoriteViewModel: FavoriteViewModel = hiltViewModel()
    val favoriteListState = favoriteViewModel.favorite.observeAsState()
    val context = LocalContext.current

    val textStyle = MaterialTheme.typography.titleLarge

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
                    Text(
                        text = "Favorite Anime",
                        style = textStyle,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    favoriteListState.value?.let { favoriteList ->
                        AnimeList(context,animeList = favoriteList)
                    }
                }
            }
        }
    }
}