package com.example.jetpackcomposeanime.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposeanime.R
import com.example.jetpackcomposeanime.ui.theme.Blue200
import com.example.jetpackcomposeanime.ui.theme.Blue500
import com.example.jetpackcomposeanime.ui.theme.Blue700

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun BottomSheetFilterForm(showBottomSheet: Boolean, onDismiss: () -> Unit) {
    val genreList = stringArrayResource(id = R.array.genres)
    var expandedGenre by remember { mutableStateOf(false) }
    var selectedGenre by remember { mutableStateOf("") }

    val yearList = (1990..2024).map { it.toString() }.toMutableList()
    yearList.add(0, "All Year")
    var expandedYear by remember { mutableStateOf(false) }
    var selectedYear by remember { mutableStateOf("") }

    val seasonList = stringArrayResource(id = R.array.season)
    var expandedSeason by remember { mutableStateOf(false) }
    var selectedSeason by remember { mutableStateOf("") }

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
    )

    if (showBottomSheet){
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = sheetState,
            dragHandle = { BottomSheetDefaults.DragHandle() }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            ) {
                Text(
                    text = "Filter",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Blue500
                )
                Spacer(modifier = Modifier.height(16.dp))
                ExposedDropdownMenuBox(
                    expanded = expandedGenre,
                    onExpandedChange = {
                        expandedGenre = !expandedGenre
                    }) {
                    TextField(
                        value = selectedGenre,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text(text = "Genre", color = Blue500) },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedGenre)
                        },
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = Blue500,
                            unfocusedTextColor = Blue500,
                            focusedContainerColor = Blue200,
                            unfocusedContainerColor = Blue200,
                            focusedIndicatorColor = Blue700, // Custom indicator color
                            unfocusedIndicatorColor = Blue500 // Custom indicator color
                        ),
                        textStyle = TextStyle(
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedGenre,
                        onDismissRequest = { expandedGenre = false }
                    ) {
                        genreList.forEach { genre ->
                            DropdownMenuItem(
                                text = { Text(text = genre) },
                                colors = MenuItemColors(
                                    textColor = Blue500,
                                    trailingIconColor = Blue700,
                                    disabledTextColor = Color.Gray,
                                    disabledLeadingIconColor = Color.Gray,
                                    disabledTrailingIconColor = Color.Gray,
                                    leadingIconColor = Blue500
                                ),
                                onClick = {
                                    selectedGenre = genre
                                    expandedGenre = false
                                }
                            )
                        }
                    }

                }
                Spacer(modifier = Modifier.height(8.dp))
                ExposedDropdownMenuBox(
                    expanded = expandedSeason,
                    onExpandedChange = {
                        expandedSeason = !expandedSeason
                    }) {
                    TextField(
                        value = selectedSeason,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text(text = "Season", color = Blue500) },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedSeason)
                        },
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = Blue500,
                            unfocusedTextColor = Blue500,
                            focusedContainerColor = Blue200,
                            unfocusedContainerColor = Blue200,
                            focusedIndicatorColor = Blue700, // Custom indicator color
                            unfocusedIndicatorColor = Blue500 // Custom indicator color
                        ),
                        textStyle = TextStyle(
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedSeason,
                        onDismissRequest = { expandedSeason = false }
                    ) {
                        seasonList.forEach { season ->
                            DropdownMenuItem(
                                text = { Text(text = season) },
                                colors = MenuItemColors(
                                    textColor = Blue500,
                                    trailingIconColor = Blue700,
                                    disabledTextColor = Color.Gray,
                                    disabledLeadingIconColor = Color.Gray,
                                    disabledTrailingIconColor = Color.Gray,
                                    leadingIconColor = Blue500
                                ),
                                onClick = {
                                    selectedSeason = season
                                    expandedSeason = false
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                ExposedDropdownMenuBox(
                    expanded = expandedYear,
                    onExpandedChange = {
                        expandedYear = !expandedYear
                    }) {
                    TextField(
                        value = selectedYear,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text(text = "Year", color = Blue500) },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedYear)
                        },
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = Blue500,
                            unfocusedTextColor = Blue500,
                            focusedContainerColor = Blue200,
                            unfocusedContainerColor = Blue200,
                            focusedIndicatorColor = Blue700, // Custom indicator color
                            unfocusedIndicatorColor = Blue500 // Custom indicator color
                        ),
                        textStyle = TextStyle(
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedYear,
                        onDismissRequest = { expandedYear = false }
                    ) {
                        yearList.forEach { year ->
                            DropdownMenuItem(
                                text = { Text(text = year) },
                                colors = MenuItemColors(
                                    textColor = Blue500,
                                    trailingIconColor = Blue700,
                                    disabledTextColor = Color.Gray,
                                    disabledLeadingIconColor = Color.Gray,
                                    disabledTrailingIconColor = Color.Gray,
                                    leadingIconColor = Blue500
                                ),
                                onClick = {
                                    selectedYear = year
                                    expandedYear = false
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonColors(
                        containerColor = Blue500,
                        contentColor = Color.White,
                        disabledContentColor = Color.Gray,
                        disabledContainerColor = Color.LightGray
                    ),
                    shape = RoundedCornerShape(5.dp)
                ) {
                    Text(text = "Search", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}


@Preview
@Composable
private fun BottomSheetFilterPreview() {
//    BottomSheetFilterForm(true)
}