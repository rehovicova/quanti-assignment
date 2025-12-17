package cz.quanti.spacexrockets.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cz.quanti.spacexrockets.R


@Composable
fun CenterComp(content: @Composable () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        content()
    }
}

@Composable
fun ErrComp(reason: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text ="Houston, we've had problem!", style = MaterialTheme.typography.titleMedium)
        Text(text = reason)
        Spacer(modifier = Modifier.height(4.dp))
        Image(painter = painterResource(R.drawable.img_rocket_error), contentDescription = "Error")
    }
}

@Composable
fun LoadingComp() {
    CenterComp {
        CircularProgressIndicator(modifier = Modifier.fillMaxSize(0.5f))
    }
}