package com.example.jetpackcomposeanime.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val label: String
) {
    object Home : BottomNavItem("HomeUI", Icons.Filled.Home, Icons.Outlined.Home, "Home")
    object Favorite :
        BottomNavItem(
            "FavoriteUI",
            Icons.Filled.Favorite,
            Icons.Outlined.FavoriteBorder,
            "Favorite"
        )

    companion object {
        val values = listOf(Home, Favorite)
    }
}