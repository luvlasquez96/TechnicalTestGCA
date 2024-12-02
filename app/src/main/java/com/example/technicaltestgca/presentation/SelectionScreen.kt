package com.example.technicaltestgca.presentation

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.technicaltestgca.domain.model.Point
import com.example.technicaltestgca.domain.model.Polygon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectionScreen(
    onNavigateToDesign: (Polygon, Int, Float) -> Unit,
) {
    val viewModel: PolygonsViewModel = hiltViewModel()
    val viewState by viewModel.viewState.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var sidesCount by remember { mutableIntStateOf(3) }
    var scale by remember { mutableStateOf(1.0f) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.fetchPolygons()
    }

    LaunchedEffect(viewState) {
        if (viewState is PolygonsViewModel.ViewState.PolygonSaved) {
            viewModel.fetchPolygons()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Select a Figure") }
            )
        }
    ) { padding ->
        when (viewState) {
            is PolygonsViewModel.ViewState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is PolygonsViewModel.ViewState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text((viewState as PolygonsViewModel.ViewState.Error).errorMessage)
                }
            }

            is PolygonsViewModel.ViewState.PolygonsLoaded -> {
                val polygons =
                    (viewState as PolygonsViewModel.ViewState.PolygonsLoaded).polygonsList
                LazyColumn(
                    contentPadding = padding,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(polygons) { polygon ->
                        ListItem(
                            headlineContent = { Text(polygon.name) },
                            modifier = Modifier.clickable {
                                onNavigateToDesign(polygon, sidesCount, scale)
                            }
                        )
                    }
                    item {
                        OutlinedButton(
                            onClick = {
                                showDialog = true
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        ) {
                            Text("Create Regular Polygon")
                        }
                    }
                }
            }

            else -> {}
        }
    }

    if (showDialog) {
        var sidesCountText by remember { mutableStateOf(sidesCount.toString()) }
        var scaleText by remember { mutableStateOf(scale.toString()) }

        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Enter Number of Sides") },
            text = {
                Column {
                    Text("Number of sides:")
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = sidesCountText,
                        onValueChange = {
                            if (it.all { char -> char.isDigit() }) {
                                sidesCountText = it
                            }
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        ),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Scale:")
                    OutlinedTextField(
                        value = scaleText,
                        onValueChange = {
                            if (it.isEmpty() || it.toFloatOrNull() != null) {
                                scaleText = it
                            }
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        ),
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val newSidesCount = sidesCountText.toIntOrNull()
                        val newScale = scaleText.toFloatOrNull()

                        if (newSidesCount != null && newSidesCount > 2) {
                            sidesCount = newSidesCount
                            scale = newScale ?: 1.0f
                            showDialog = false

                            val radius = 100f
                            val angleStep = (2 * Math.PI / sidesCount).toFloat()
                            val center = Point(0f, 0f)

                            val points = List(sidesCount) { i ->
                                val angle = (i * angleStep).toDouble()
                                val x = center.x + (radius * Math.cos(angle)).toFloat()
                                val y = center.y + (radius * Math.sin(angle)).toFloat()
                                Point(x, y)
                            }

                            val newPolygon = Polygon("Regular Polygon", points)
                            onNavigateToDesign(newPolygon, sidesCount, scale)
                        } else {
                            Toast.makeText(
                                context,
                                "Number of sides must be greater than 2",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }
}