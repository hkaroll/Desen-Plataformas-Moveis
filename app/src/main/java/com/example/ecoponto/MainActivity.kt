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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ecoponto.ui.navigation.Screen
import com.example.ecoponto.ui.screens.GuideScreen
import com.example.ecoponto.ui.screens.HomeScreen
import com.example.ecoponto.ui.screens.MapScreen
import com.example.ecoponto.ui.screens.ReportScreen
import com.example.ecoponto.ui.theme.EcopontoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Ativando Edge-to-Edge para uma UI moderna que ocupa toda a tela
        enableEdgeToEdge()
        
        setContent {
            EcopontoTheme {
                // O Scaffold é o layout básico do Material Design
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Configurando a Navegação Centralizada
                    AppNavigation(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    
    // O NavHost gerencia o ciclo de vida das telas no Compose
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
        composable(Screen.Guide.route) {
            GuideScreen(navController)
        }
        composable(Screen.Report.route) {
            ReportScreen(navController)
        }
    }
}
