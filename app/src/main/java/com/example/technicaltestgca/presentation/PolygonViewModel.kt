package com.example.technicaltestgca.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.technicaltestgca.domain.model.Polygon
import com.example.technicaltestgca.domain.usecase.GetPolygonsUseCase
import com.example.technicaltestgca.domain.usecase.GetSavedPolygonUseCase
import com.example.technicaltestgca.domain.usecase.SavePolygonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PolygonsViewModel @Inject constructor(
    private val getPolygonsUseCase: GetPolygonsUseCase,
    private val getSavedPolygonUseCase: GetSavedPolygonUseCase,
    private val savePolygonUseCase: SavePolygonUseCase
) : ViewModel() {

    private var _viewState = MutableStateFlow<ViewState>(ViewState.Loading)
    val viewState = _viewState.asStateFlow()

    fun fetchPolygons() {
        _viewState.value = ViewState.Loading
        viewModelScope.launch {
            try {
                val polygonsList = getPolygonsUseCase()
                if (polygonsList.isEmpty()) {
                    _viewState.value = ViewState.Error("No polygons available.")
                } else {
                    _viewState.value = ViewState.PolygonsLoaded(polygonsList)
                }
            } catch (e: Exception) {
                _viewState.value = ViewState.Error(e.message ?: "Failed to fetch polygons.")
            }
        }
    }

    fun fetchSavedPolygon() {
        _viewState.value = ViewState.Loading
        viewModelScope.launch {
            try {
                val savedPolygon = getSavedPolygonUseCase()
                if (savedPolygon != null) {
                    _viewState.value = ViewState.SavedPolygonLoaded(savedPolygon)
                } else {
                    _viewState.value = ViewState.Error("No saved polygon found.")
                }
            } catch (e: Exception) {
                _viewState.value = ViewState.Error(e.message ?: "Failed to fetch saved polygon.")
            }
        }
    }

    fun savePolygon(polygon: Polygon) {
        _viewState.value = ViewState.Loading
        viewModelScope.launch {
            try {
                savePolygonUseCase(polygon)
                _viewState.value = ViewState.PolygonSaved
            } catch (e: Exception) {
                _viewState.value = ViewState.Error(e.message ?: "Failed to save polygon.")
            }
        }
    }

    sealed class ViewState {
        object Loading : ViewState()
        data class Error(val errorMessage: String) : ViewState()
        data class PolygonsLoaded(val polygonsList: List<Polygon>) : ViewState()
        data class SavedPolygonLoaded(val polygon: Polygon) : ViewState()
        object PolygonSaved : ViewState()
    }
}