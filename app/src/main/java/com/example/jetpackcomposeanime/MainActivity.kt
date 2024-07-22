package com.example.jetpackcomposeanime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcomposeanime.ui.BottomNavItem
import com.example.jetpackcomposeanime.ui.BottomNavigationBar
import com.example.jetpackcomposeanime.ui.FavoriteUI
import com.example.jetpackcomposeanime.ui.HomeUI
import com.example.jetpackcomposeanime.ui.SearchScreenView
import com.example.jetpackcomposeanime.ui.theme.Blue200
import com.example.jetpackcomposeanime.ui.theme.JetpackComposeAnimeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeAnimeTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                Surface(
                    color = Blue200
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        bottomBar = {
                            if (currentRoute != "search") {
                                BottomNavigationBar(navController = navController)
                            }
                        },

                        ) { innerPadding ->
                        Box(modifier = Modifier.padding(innerPadding)) {
                            NavigationHost(navController = navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NavigationHost(navController: NavHostController) {
    NavHost(
        navController,
        startDestination = BottomNavItem.Home.route,
    ) {
        composable(
            route = BottomNavItem.Home.route,
            enterTransition = {
                slideIntoContainer(
                    animationSpec = tween(300, easing = LinearEasing),
                    towards = AnimatedContentTransitionScope.SlideDirection.Right
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    animationSpec = tween(300, easing = LinearEasing),
                    towards = AnimatedContentTransitionScope.SlideDirection.Left
                )
            }
        ) { HomeUI(navController) }
        composable(
            route = BottomNavItem.Favorite.route,
            enterTransition = {
                slideIntoContainer(
                    animationSpec = tween(300, easing = LinearEasing),
                    towards = AnimatedContentTransitionScope.SlideDirection.Left
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    animationSpec = tween(300, easing = LinearEasing),
                    towards = AnimatedContentTransitionScope.SlideDirection.Right
                )
            }
        ) { FavoriteUI() }
        composable(
            route = "search",
            enterTransition = {
                slideIntoContainer(
                    animationSpec = tween(300, easing = LinearEasing),
                    towards = AnimatedContentTransitionScope.SlideDirection.Left
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    animationSpec = tween(300, easing = LinearEasing),
                    towards = AnimatedContentTransitionScope.SlideDirection.Right
                )
            }
        ) { SearchScreenView(navController) }
    }
}