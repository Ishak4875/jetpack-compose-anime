package com.example.jetpackcomposeanime.ui

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.example.jetpackcomposeanime.R
import com.example.jetpackcomposeanime.core.domain.model.Anime
import com.example.jetpackcomposeanime.detail.DetailActivity
import java.util.*

@Composable
fun AnimeMinItem(context: Context, anime: Anime) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(150.dp)
            .height(200.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        onClick = {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, anime)
            context.startActivity(intent)
        }
    ) {
        Surface(
            color = Color.White
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxSize()
            ) {
                Image(
                    painter = rememberImagePainter(data = anime.posterImage, builder = {
                        scale(Scale.FILL)
                        placeholder(R.drawable.error_outline)
                        transformations(CircleCropTransformation())
                    }),
                    contentDescription = anime.title,
                    modifier = Modifier
                        .height(80.dp)
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = anime.title ?: "No Title Available",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = anime.status!!.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.ROOT
                        ) else it.toString()
                    },
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .background(Color.LightGray)
                        .padding(4.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.padding(0.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    androidx.compose.material.Text(
                        text = anime.averageRating!!.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    androidx.compose.material.Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = anime.title,
                        modifier = Modifier.size(20.dp),
                        tint = Color.Magenta
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${anime.episodeCount ?: 0} episode" +
                            if ((anime.episodeCount ?: 0) > 1) "s" else "",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

@Preview
@Composable
private fun DataMinSizePreview() {
    val anime = Anime(
        id = "1",
        synopsis = "In the year 2071, humanity has colonized several of the planets and moons of" +
                " the solar system leaving the now uninhabitable surface of planet Earth behind. " +
                "The Inter Solar System Police attempts to keep peace in the galaxy, aided in part " +
                "by outlaw bounty hunters, referred to as \"Cowboys\". The ragtag team aboard " +
                "the spaceship Bebop are two such individuals.\nMellow and carefree Spike Spiegel " +
                "is balanced by his boisterous, pragmatic partner Jet Black as the pair makes a " +
                "living chasing bounties and collecting rewards. Thrown off course by the addition " +
                "of new members that they meet in their travels—Ein, a genetically engineered, " +
                "highly intelligent Welsh Corgi; femme fatale Faye Valentine, an enigmatic trickster" +
                " with memory loss; and the strange computer whiz kid Edward Wong—the crew embarks " +
                "on thrilling adventures that unravel each member's dark and mysterious past little " +
                "by little. \nWell-balanced with high density action and light-hearted comedy, Cowboy" +
                " Bebop is a space Western classic and an homage to the smooth and improvised music " +
                "it is named after.\n\n(Source: MAL Rewrite)",
        title = "Cowboy Bebop",
        averageRating = "82.22",
        startDate = "1998-04-03",
        endDate = "1999-04-24",
        ratingRank = 121,
        status = "finished",
        coverImage = "https://media.kitsu.io/anime/1/cover_image/large-88da0208ac7fdd1a978de8b539008bd8.jpeg",
        posterImage = "https://media.kitsu.io/anime/poster_images/1/large.jpg",
        episodeCount = 26
    )
    val context = LocalContext.current
    AnimeMinItem(context,anime = anime)
}