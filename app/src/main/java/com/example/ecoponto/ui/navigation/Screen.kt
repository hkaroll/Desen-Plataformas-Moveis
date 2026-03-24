package com.example.ecoponto.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Map : Screen("map")
    object Regionals : Screen("regionals")
    object EcopontosList : Screen("ecopontos_list/{regionalId}") {
        fun createRoute(regionalId: Int) = "ecopontos_list/$regionalId"
    }
    object Guide : Screen("guide")
    object Report : Screen("report")
}
