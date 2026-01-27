package org.floradex.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import org.floradex.app.core.navigation.AppNavGraph
import org.floradex.app.designsystem.theme.FloraDexTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FloraDexTheme {
                val navController = rememberNavController()
                AppNavGraph(navController = navController)
            }
        }
    }
}