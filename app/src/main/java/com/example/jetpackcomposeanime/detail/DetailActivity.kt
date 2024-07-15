package com.example.jetpackcomposeanime.detail

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberImagePainter
import com.example.jetpackcomposeanime.NavigationHost
import com.example.jetpackcomposeanime.R
import com.example.jetpackcomposeanime.core.domain.model.Anime
import com.example.jetpackcomposeanime.ui.BottomNavigationBar
import com.example.jetpackcomposeanime.ui.theme.JetpackComposeAnimeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val anime = intent.getParcelableExtra<Anime>(EXTRA_DATA)
        Log.d("Detail Activity",anime.toString())
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetpackComposeAnimeTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        BottomSheet(anime = anime!!)
                    }
                }
            }
        }
    }

    companion object{
        val EXTRA_DATA = "ANIME_DETAIL"
    }
}
