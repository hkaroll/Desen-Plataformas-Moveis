package com.example.ecoponto.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ecoponto.model.regionalData
import com.example.ecoponto.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegionalsScreen(navController: NavController) {
    var mMenuExpanded by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }

    // Filtra as regionais com base na busca para a Grid principal
    val filteredGridRegionals = remember(searchQuery) {
        if (searchQuery.isEmpty()) {
            regionalData
        } else {
            regionalData.filter { regional ->
                regional.name.contains(searchQuery, ignoreCase = true) ||
                regional.ecopontos.any { it.contains(searchQuery, ignoreCase = true) }
            }
        }
    }

    Scaffold(
        containerColor = Color(0xFFF7FBF7),
        topBar = {
            if (!isSearchActive) {
                TopAppBar(
                    title = { Text("Regionais", color = Color(0xFF2D5A27), fontWeight = FontWeight.Bold) },
                    navigationIcon = {
                        Box {
                            IconButton(onClick = { mMenuExpanded = true }) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Menu",
                                    tint = Color(0xFF2D5A27),
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                            DropdownMenu(
                                expanded = mMenuExpanded,
                                onDismissRequest = { mMenuExpanded = false },
                                modifier = Modifier.background(Color(0xFFF7FBF7)).width(200.dp)
                            ) {
                                DropdownMenuItem(
                                    text = { Text("Home", color = Color(0xFF2D5A27)) },
                                    onClick = { 
                                        mMenuExpanded = false
                                        navController.navigate(Screen.Home.route) 
                                    },
                                    leadingIcon = { Icon(Icons.Default.Home, null, tint = Color(0xFF2D5A27)) }
                                )
                                HorizontalDivider(modifier = Modifier.padding(horizontal = 12.dp))
                                DropdownMenuItem(
                                    text = { Text("Mapa", color = Color(0xFF2D5A27)) },
                                    onClick = { 
                                        mMenuExpanded = false
                                        navController.navigate(Screen.Map.route) 
                                    },
                                    leadingIcon = { Icon(Icons.Default.Place, null, tint = Color(0xFF2D5A27)) }
                                )
                            }
                        }
                    },
                    actions = {
                        IconButton(onClick = { isSearchActive = true }) {
                            Icon(Icons.Default.Search, "Buscar", tint = Color(0xFF2D5A27), modifier = Modifier.size(32.dp))
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            }
        },
        bottomBar = {
            if (!isSearchActive) {
                NavigationBar(containerColor = Color.White, contentColor = Color(0xFF2D5A27)) {
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Home, "Home") },
                        label = { Text("Home") },
                        selected = false,
                        onClick = { navController.navigate(Screen.Home.route) }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Place, "Mapa") },
                        label = { Text("Mapa") },
                        selected = false,
                        onClick = { navController.navigate(Screen.Map.route) }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.LocationOn, "Pontos") },
                        label = { Text("Pontos") },
                        selected = true,
                        onClick = { }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                if (filteredGridRegionals.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Nenhuma regional encontrada", color = Color.Gray)
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(bottom = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(filteredGridRegionals) { regional ->
                            Button(
                                onClick = { 
                                    navController.navigate(Screen.EcopontosList.createRoute(regional.id))
                                },
                                modifier = Modifier.fillMaxWidth().height(80.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2D5A27)),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    text = regional.name,
                                    color = Color.White,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    lineHeight = 18.sp
                                )
                            }
                        }
                    }
                }
            }

            if (isSearchActive) {
                SearchBar(
                    query = searchQuery,
                    onQueryChange = { searchQuery = it },
                    onSearch = { isSearchActive = false },
                    active = isSearchActive,
                    onActiveChange = { isSearchActive = it },
                    placeholder = { Text("Buscar Regional ou Bairro...") },
                    leadingIcon = { 
                        IconButton(onClick = { isSearchActive = false }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                        }
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(Icons.Default.Close, contentDescription = "Limpar")
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = SearchBarDefaults.colors(containerColor = Color.White)
                ) {
                    val searchResults = regionalData.filter { regional ->
                        regional.name.contains(searchQuery, ignoreCase = true) ||
                        regional.ecopontos.any { it.contains(searchQuery, ignoreCase = true) }
                    }

                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                        searchResults.forEach { regional ->
                            ListItem(
                                headlineContent = { Text(regional.name) },
                                supportingContent = { 
                                    val matchedEcopontos = regional.ecopontos.filter { it.contains(searchQuery, ignoreCase = true) }
                                    Text(if (matchedEcopontos.isNotEmpty()) matchedEcopontos.joinToString(", ") else regional.ecopontos.take(2).joinToString(", ") + "...")
                                },
                                leadingContent = { Icon(Icons.Default.LocationOn, null, tint = Color(0xFF2D5A27)) },
                                modifier = Modifier.fillMaxWidth().clickable {
                                    isSearchActive = false
                                    navController.navigate(Screen.EcopontosList.createRoute(regional.id))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
