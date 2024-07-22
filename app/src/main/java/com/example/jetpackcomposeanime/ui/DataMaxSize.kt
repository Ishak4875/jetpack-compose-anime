package com.example.jetpackcomposeanime.ui

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
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
import java.util.Locale

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AnimeMaxItem(context: Context, anime: Anime) {
    Card(
        modifier = Modifier
            .padding(8.dp, 4.dp)
            .fillMaxWidth()
            .height(130.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        onClick = {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, anime)
            context.startActivity(intent)
        }
    ) {
        Surface {
            Row(
                Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            ) {
                Image(
                    painter = rememberImagePainter(data = anime.posterImage, builder = {
                        scale(Scale.FILL)
                        placeholder(R.drawable.error_outline)
//                        transformations(CircleCropTransformation())
                    }),
                    contentDescription = anime.title,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.2f)
                )
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxHeight()
                        .weight(0.8f)
                ) {
                    Text(
                        text = anime.title!!,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
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

                    Row(
                        modifier = Modifier.padding(0.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = anime.averageRating!!.toString(),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = anime.title,
                            modifier = Modifier.size(20.dp),
                            tint = Color.Magenta
                        )
                    }
                    Text(
                        text = "${anime.episodeCount!!} episode" +
                                if (anime.episodeCount > 1) "s" else "",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Text(
                        text = anime.synopsis!!,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun DataMaxSizePreview() {
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
    AnimeMaxItem(context = context, anime = anime)
}