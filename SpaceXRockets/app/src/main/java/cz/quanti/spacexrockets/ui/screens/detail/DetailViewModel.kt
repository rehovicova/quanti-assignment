package cz.quanti.spacexrockets.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.quanti.spacexrockets.api.RocketRepo
import cz.quanti.spacexrockets.model.RocketDetail
import cz.quanti.spacexrockets.ui.screens.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: RocketRepo) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState<RocketDetail>())
    val uiState: StateFlow<UiState<RocketDetail>> = _uiState.asStateFlow()

    fun fetchRocket(id: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val rocket = repository.getRocket(id)
                _uiState.value = _uiState.value.copy(isLoading = false, data = rocket)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, error = e.message)
            }
        }
    }

}