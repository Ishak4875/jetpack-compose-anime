package com.example.jetpackcomposeanime.detail

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.example.jetpackcomposeanime.R
import com.example.jetpackcomposeanime.core.domain.model.Anime
import com.example.jetpackcomposeanime.ui.theme.Blue200
import com.example.jetpackcomposeanime.ui.theme.Blue700
import java.util.Locale


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheet(anime: Anime) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val imageHeight = screenHeight / 3
    val halfExpandedHeight = screenHeight - imageHeight

    val bottomSheetState =
        rememberBottomSheetScaffoldState(
            bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
        )

    val detailAnimeViewModel: DetailAnimeViewModel = hiltViewModel()
    val checkFavorite by detailAnimeViewModel.checkFavorite(anime.id)
        .collectAsState(initial = false)
    var isFavorite by remember { mutableStateOf(false) }

    LaunchedEffect(checkFavorite) {
        isFavorite = checkFavorite
    }
    Log.d("Detail Activity", isFavorite.toString())

    BottomSheetScaffold(
        scaffoldState = bottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetPeekHeight = halfExpandedHeight,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min)
                ) {
                    Column(
                        modifier = Modifier
                            .weight(0.4f)
                    ) {
                        Box(modifier = Modifier.fillMaxHeight()) {
                            Image(
                                painter = rememberImagePainter(data = anime.posterImage, builder = {
                                    scale(Scale.FILL)
                                    placeholder(R.drawable.error_outline)
                                }),
                                contentDescription = anime.title,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight() // Ensures the image takes the full height of the Box
                            )
                        }
                    }
                    Column(
                        modifier = Modifier
                            .weight(0.4f)
                            .fillMaxHeight()
                    ) {
                        Text(
                            text = anime.title!!,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
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
                    }
                    Column(
                        modifier = Modifier
                            .weight(0.2f)
                            .fillMaxHeight()
                    ) {
                        FloatingActionButton(
                            onClick = {
                                Log.d("Detail Activity", "Sudah Diklik")
                                detailAnimeViewModel.setFavoriteAnime(anime, isFavorite)
                                isFavorite = !isFavorite
                            },
                            modifier = Modifier
                                .align(Alignment.End),
                            containerColor = Blue700
                        ) {
                            Log.d("Detail Activity", isFavorite.toString())
                            Icon(
                                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                contentDescription = "Favorite",
                                tint = Color.White,
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState())
                ) {
                    CircleWithIcon("Start\nDate", anime.startDate!!, Icons.Filled.DateRange)
                    CircleWithIcon("End\nDate", anime.endDate!!, Icons.Filled.DateRange)
                    CircleWithIcon(
                        "Rating\nRank",
                        anime.ratingRank.toString(),
                        Icons.Filled.Star
                    )
                    CircleWithIcon(
                        "Episode Count",
                        anime.episodeCount.toString(),
                        Icons.Filled.AccountBox
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Synopsis",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = anime.synopsis!!,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }) {
        Box(
            modifier = Modifier
                .height(imageHeight)
                .background(Blue200)
        ) {
            Image(
                painter = rememberImagePainter(
                    data = anime.coverImage,
                    builder = {
                        crossfade(true)
                        placeholder(R.drawable.error_outline)
                    }
                ),
                contentDescription = anime.title,
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun CircleWithIcon(title: String, content: String, imageVector: ImageVector) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(100.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp) // Set the size of the circle
                .background(
                    color = Blue700, // Set the background color of the circle
                    shape = CircleShape // Make the shape a circle
                ),
            contentAlignment = Alignment.Center // Center the icon within the circle
        ) {
            Icon(
                imageVector = imageVector, // Replace with your desired icon
                contentDescription = "Icon",
                tint = Color.White, // Set the color of the icon
                modifier = Modifier.size(25.dp) // Set the size of the icon
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = content,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BottomSheetPreview() {
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
    BottomSheet(anime = anime)
}