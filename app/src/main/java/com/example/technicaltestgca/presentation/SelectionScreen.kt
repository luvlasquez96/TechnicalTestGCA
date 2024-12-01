package com.example.technicaltestgca.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.technicaltestgca.domain.model.Polygon
import com.example.technicaltestgca.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectionScreen(
    navController: NavController,
    onNavigateToDesign: (Polygon) -> Unit,
) {
    val viewModel: PolygonsViewModel = hiltViewModel()
    val viewState by viewModel.viewState.collectAsState()

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
                                navController.navigate(Screen.Design.createRoute(polygon.name))
                            }
                        )
                    }
                    item {
                        OutlinedButton(
                            onClick = {
                                navController.navigate(Screen.Design.createRoute("regularPolygon"))
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
}
