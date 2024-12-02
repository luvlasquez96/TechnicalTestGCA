package com.example.technicaltestgca.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.technicaltestgca.domain.model.Point
import com.example.technicaltestgca.domain.model.Polygon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DesignScreen(
    polygon: Polygon,
    sidesCount: Int,
    scale: Float,
    navController: NavController
) {
    val polygonsViewModel: PolygonsViewModel = hiltViewModel()

    val viewState by polygonsViewModel.viewState.collectAsState()

    LaunchedEffect(Unit) {
        polygonsViewModel.fetchSavedPolygon()
    }

    val points = remember {
        if (viewState is PolygonsViewModel.ViewState.SavedPolygonLoaded) {
            (viewState as PolygonsViewModel.ViewState.SavedPolygonLoaded).polygon.points.map {
                Point(
                    it.x * scale,
                    it.y * scale
                )
            }.toMutableStateList()
        } else {
            polygon.points.map { Point(it.x * scale, it.y * scale) }.toMutableStateList()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Design: ${polygon.name} - Sides: $sidesCount") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                val modifiedPolygon = Polygon(
                    name = polygon.name,
                    points = points.toList()
                )
                polygonsViewModel.savePolygon(modifiedPolygon)
                navController.popBackStack()
            }) {
                Icon(Icons.Default.Send, contentDescription = "Save Polygon")
            }
        }
    ) { padding ->
        DrawPolygonEditor(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            points = points,
            onPointMoved = { index, newPoint ->
                points[index] = newPoint
            }
        )
    }
}