package cz.quanti.spacexrockets.ui.screens.detail

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import cz.quanti.spacexrockets.R
import cz.quanti.spacexrockets.model.Stage
import cz.quanti.spacexrockets.ui.ErrComp
import cz.quanti.spacexrockets.ui.LoadingComp
import kotlin.math.roundToInt


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    id: String,
    navigateBack: () -> Unit,
    navigateToLaunch: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(id) {
        viewModel.fetchRocket(id)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = state.data?.name ?: "Loading...", fontSize = 16.sp)
                },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    TextButton(onClick = navigateToLaunch) {
                        Text(text = stringResource(R.string.launch))
                    }
                }
            )
        }
    ) { innerPadding ->
        state.data?.let { rocket ->
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
            ) {
                // Overview
                Title(title = R.string.overview)
                Text(text = rocket.description)

                // Parameters
                Title(title = R.string.parameters)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    DetailCard(value = rocket.height.toString(), title = R.string.height, modifier = Modifier.weight(1f))
                    DetailCard(value = rocket.diameter.toString(), title = R.string.diameter, modifier = Modifier.weight(1f))
                    DetailCard(value = rocket.mass.toString(), title = R.string.mass, modifier = Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Stages
                StageComp(title = R.string.first_stage, rocket.firstStage)
                Spacer(modifier = Modifier.height(16.dp))
                StageComp(title = R.string.second_stage, rocket.secondStage)

                // Photos
                Title(title = R.string.photos)
                rocket.images.forEach { url ->
                    AsyncImage(
                        modifier = Modifier.clip(RoundedCornerShape(12.dp)),
                        model = url,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

        if (state.isLoading)
            LoadingComp()


        state.error?.let { err ->
            ErrComp(err)
        }
    }
}

@Composable
private fun Title(@StringRes title: Int) {
    Text(
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp ),
        text = stringResource(title),
        style = MaterialTheme.typography.titleMedium
    )
}

@Composable
private fun StageComp(@StringRes title: Int, stage: Stage) {
    Surface(
        modifier =  Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = stringResource(title), style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            StageLine(icon = R.drawable.ic_reusable, text = stringResource(if (stage.reusable) R.string.reusable else R.string.not_reusable))
            StageLine(icon = R.drawable.ic_engine, text = stringResource(R.string.engine_count, stage.engines))
            StageLine(icon = R.drawable.ic_fuel, text = stringResource(R.string.fuel_count, stage.fuelAmount.roundToInt()))
            stage.burnTime?.let {
                StageLine(icon = R.drawable.ic_burn, text = stringResource(R.string.burn_time, it))
            }
        }
    }
}

@Composable
private fun StageLine(@DrawableRes icon: Int, text: String) {
    Row(
        modifier = Modifier.padding(vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(imageVector = ImageVector.vectorResource(icon), contentDescription = "")
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = text)
    }
}

@Composable
private fun DetailCard(value: String, @StringRes title: Int, modifier: Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.primary
    ) {
        Column(
            modifier = Modifier.padding(top = 16.dp, bottom = 12.dp, start = 12.dp, end = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )
            Text(
                text = stringResource(title),
                color = Color.White
            )
        }
    }
}