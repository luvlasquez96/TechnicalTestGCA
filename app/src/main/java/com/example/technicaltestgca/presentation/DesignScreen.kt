package com.example.technicaltestgca.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.technicaltestgca.domain.model.Point
import com.example.technicaltestgca.domain.model.Polygon

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun DesignScreen(
    polygonName: String,
) {
    val polygonsViewModel: PolygonsViewModel = hiltViewModel()
    val viewState by polygonsViewModel.viewState.collectAsState()
    var points by remember { mutableStateOf(emptyList<Point>()) }

    LaunchedEffect(Unit) {
        polygonsViewModel.fetchSavedPolygon()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Design: $polygonName") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                val modifiedPolygon = Polygon(name = polygonName, points = points)
                polygonsViewModel.savePolygon(modifiedPolygon)
            }) {
                Icon(Icons.Default.Send, contentDescription = "Save Polygon")
            }
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

            is PolygonsViewModel.ViewState.SavedPolygonLoaded -> {
                val polygon = (viewState as PolygonsViewModel.ViewState.SavedPolygonLoaded).polygon
                points = polygon.points
                DrawPolygonEditor(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    points = points,
                    onPointMoved = { index, newPoint ->
                        points = points.toMutableList().apply { set(index, newPoint) }
                    }
                )
            }

            is PolygonsViewModel.ViewState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text((viewState as PolygonsViewModel.ViewState.Error).errorMessage)
                }
            }

            else -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No saved polygon data available.")
                }
            }
        }
    }
}