package cz.quanti.spacexrockets.ui.screens.rockets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import cz.quanti.spacexrockets.R
import cz.quanti.spacexrockets.model.Rocket
import cz.quanti.spacexrockets.ui.CenterComp
import cz.quanti.spacexrockets.ui.ErrComp
import cz.quanti.spacexrockets.ui.LoadingComp
import cz.quanti.spacexrockets.ui.theme.GrayText


@Composable
fun RocketsScreen(onItemClick: (String) -> Unit, viewModel: RocketsViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surface)
                .padding(16.dp)
        ) {
            Text(
                modifier = Modifier.padding(top = 64.dp, bottom = 12.dp),
                text = stringResource(R.string.rockets),
                style = MaterialTheme.typography.titleLarge
            )

            state.data?.let { rockets ->
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color.White
                ) {
                    LazyColumn() {
                        itemsIndexed(rockets) { i, rocket ->
                            RocketItem(rocket, onItemClick)
                            if (i < rockets.lastIndex)
                                HorizontalDivider()
                        }
                    }
                }
            }

            if (state.isLoading) {
                LoadingComp()
            }

            state.error?.let { err ->
                ErrComp(err)
            }
        }
    }
}

@Composable
private fun RocketItem(rocket: Rocket, onItemClick: (String) -> Unit) {
    Row(
        modifier = Modifier.padding(8.dp).clickable {onItemClick(rocket.id)},
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.padding(start = 8.dp, end = 16.dp),
            imageVector = ImageVector.vectorResource(R.drawable.ic_rocket),
            contentDescription = rocket.name
        )
        Column(
            modifier = Modifier.weight(1F)
        ) {
            Text(text = rocket.name, fontWeight = FontWeight.Bold)
            Text(text = rocket.firstFlight, color = GrayText, style = MaterialTheme.typography.bodySmall)
        }
        Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "", tint = Color.Gray)
    }
}