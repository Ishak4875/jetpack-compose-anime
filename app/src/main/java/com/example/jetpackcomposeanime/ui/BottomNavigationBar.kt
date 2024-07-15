package com.example.jetpackcomposeanime.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.jetpackcomposeanime.ui.theme.Blue700

@Composable
fun BottomNavigationBar(navController: NavController) {
    Surface(
        shadowElevation = 20.dp,
        color = Color.Black,
        modifier = Modifier.fillMaxWidth()
    ) {
        BottomNavigation(
            backgroundColor = Color.White,
            contentColor = Blue700,
            elevation = 20.dp
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            BottomNavItem.values.forEach { item ->
                BottomNavigationItem(
                    selected = currentRoute == item.route,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    },
                    icon = {
                        Icon(
                            if (currentRoute == item.route) item.selectedIcon else item.unselectedIcon,
                            contentDescription = null
                        )
                    },
                    label = { Text(text = item.label) }
                )
            }
        }
    }
}