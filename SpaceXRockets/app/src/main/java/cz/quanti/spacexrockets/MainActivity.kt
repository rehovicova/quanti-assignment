package cz.quanti.spacexrockets

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import cz.quanti.spacexrockets.ui.AppNavHost
import cz.quanti.spacexrockets.ui.screens.rockets.RocketsScreen
import cz.quanti.spacexrockets.ui.theme.SpaceXRocketsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_SpaceXRockets)

        enableEdgeToEdge()

        setContent {
            SpaceXRocketsTheme {
                AppNavHost()
            }
        }
    }

}