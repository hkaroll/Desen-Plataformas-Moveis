package com.example.ecoponto.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ecoponto.model.regionalData
import com.example.ecoponto.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EcopontosListScreen(navController: NavController, regionalId: Int) {
    var mMenuExpanded by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }
    
    val regional = regionalData.find { it.id == regionalId } ?: regionalData[0]

    Scaffold(
        containerColor = Color(0xFFF7FBF7),
        topBar = {
            if (!isSearchActive) {
                TopAppBar(
                    title = { },
                    navigationIcon = {
                        Box {
                            IconButton(onClick = { mMenuExpanded = true }) {
                                Icon(Icons.Default.Menu, "Menu", tint = Color(0xFF2D5A27), modifier = Modifier.size(32.dp))
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
                                    }
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
                    NavigationBarItem(icon = { Icon(Icons.Default.Home, "Home") }, label = { Text("Home") }, selected = false, onClick = { navController.navigate(Screen.Home.route) })
                    NavigationBarItem(icon = { Icon(Icons.Default.Place, "Mapa") }, label = { Text("Mapa") }, selected = false, onClick = { navController.navigate(Screen.Map.createRoute()) })
                    NavigationBarItem(icon = { Icon(Icons.Default.LocationOn, "Pontos") }, label = { Text("Pontos") }, selected = true, onClick = { })
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.background(Color(0xFF2D5A27), CircleShape).size(40.dp)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Voltar", tint = Color.White)
                    }
                    
                    Spacer(modifier = Modifier.width(16.dp))
                    
                    Surface(
                        modifier = Modifier.weight(1f).height(60.dp),
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF2D5A27)),
                        color = Color.White
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(regional.name, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2D5A27))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    items(regional.ecopontos) { ecoponto ->
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF2D5A27)),
                            modifier = Modifier.fillMaxWidth().height(80.dp).clickable {
                                navController.navigate(Screen.Map.createRoute(ecoponto))
                            }
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize().padding(4.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text("Ecoponto", color = Color.White, fontSize = 12.sp)
                                Text(
                                    text = ecoponto,
                                    color = Color.White,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    lineHeight = 16.sp
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
                    placeholder = { Text("Buscar Ecoponto...") },
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
                    val filteredEcopontos = regional.ecopontos.filter { 
                        it.contains(searchQuery, ignoreCase = true) 
                    }

                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                        filteredEcopontos.forEach { ecoponto ->
                            ListItem(
                                headlineContent = { Text(ecoponto) },
                                leadingContent = { Icon(Icons.Default.LocationOn, null, tint = Color(0xFF2D5A27)) },
                                modifier = Modifier.fillMaxWidth().clickable {
                                    isSearchActive = false
                                    navController.navigate(Screen.Map.createRoute(ecoponto))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
