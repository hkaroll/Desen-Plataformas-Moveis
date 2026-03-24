package com.example.ecoponto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ecoponto.ui.navigation.Screen
import com.example.ecoponto.ui.screens.EcopontosListScreen
import com.example.ecoponto.ui.screens.GuideScreen
import com.example.ecoponto.ui.screens.HomeScreen
import com.example.ecoponto.ui.screens.MapScreen
import com.example.ecoponto.ui.screens.RegionalsScreen
import com.example.ecoponto.ui.screens.ReportScreen
import com.example.ecoponto.ui.theme.EcopontoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EcopontoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(Screen.Map.route) {
            MapScreen(navController)
        }
        composable(Screen.Regionals.route) {
            RegionalsScreen(navController)
        }
        
        composable(
            route = Screen.EcopontosList.route,
            arguments = listOf(navArgument("regionalId") { type = NavType.IntType })
        ) { backStackEntry ->
            val regionalId = backStackEntry.arguments?.getInt("regionalId") ?: 1
            EcopontosListScreen(navController, regionalId)
        }

        composable(Screen.Guide.route) {
            GuideScreen(navController)
        }
        composable(Screen.Report.route) {
            ReportScreen(navController)
        }
    }
}
