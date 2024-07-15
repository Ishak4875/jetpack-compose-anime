package com.example.jetpackcomposeanime.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BackgroundUI() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .weight(0.3f)
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Blue500, Blue700),
                        startY = 0f,
                        endY = 1000f
                    ),
                    shape = RoundedCornerShape(
                        bottomEnd = 20.dp,
                        bottomStart = 20.dp
                    )
                )
        )
        Box(
            modifier = Modifier
                .weight(0.7f)
                .fillMaxWidth()
                .background(Color.White)
        )
    }
}

