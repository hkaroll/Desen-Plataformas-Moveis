package com.example.ecoponto.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ecoponto.model.Ecoponto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class MapUiState {
    object Loading : MapUiState()
    data class Success(val ecopontos: List<Ecoponto>) : MapUiState()
    data class Error(val message: String) : MapUiState()
}

class MapViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<MapUiState>(MapUiState.Loading)
    val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()

    init {
        loadEcopontos()
    }

    private fun loadEcopontos() {
        try {
            // Mock de dados para o MVP inicial
            val ecopontosMock = listOf(
                Ecoponto("1", "Ecoponto Vila Velha", "Rua X, 123", -3.732, -38.592, listOf("Papel", "Vidro"), "Regional 1"),
                Ecoponto("2", "Ecoponto Bairro Ellery", "Rua Y, 456", -3.725, -38.550, listOf("Óleo", "Plástico"), "Regional 1")
            )
            _uiState.value = MapUiState.Success(ecopontosMock)
        } catch (e: Exception) {
            _uiState.value = MapUiState.Error("Falha ao carregar ecopontos: ${e.message}")
        }
    }
}
