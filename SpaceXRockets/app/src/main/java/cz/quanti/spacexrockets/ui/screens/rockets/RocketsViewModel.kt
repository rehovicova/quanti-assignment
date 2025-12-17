package cz.quanti.spacexrockets.ui.screens.rockets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.quanti.spacexrockets.api.RocketRepo
import cz.quanti.spacexrockets.model.Rocket
import cz.quanti.spacexrockets.model.RocketDetail
import cz.quanti.spacexrockets.ui.screens.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RocketsViewModel @Inject constructor(private val repository: RocketRepo) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState<List<Rocket>>())
    val uiState: StateFlow<UiState<List<Rocket>>> = _uiState.asStateFlow()

    init {
        fetchRockets()
    }

    fun fetchRockets() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val rockets = repository.getRockets()
                _uiState.value = _uiState.value.copy(isLoading = false, data = rockets)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, error = e.message)
            }
        }
    }

}