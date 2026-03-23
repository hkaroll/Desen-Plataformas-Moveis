package com.example.ecoponto.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Map : Screen("map")
    object Regionals : Screen("regionals")
    object Guide : Screen("guide")
    object Report : Screen("report")
}
