package com.example.ecoponto.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
    val regionals = (1..12).toList()

    Scaffold(
        containerColor = Color(0xFFF7FBF7),
        topBar = {
            if (!isSearchActive) {
                TopAppBar(
                    title = { },
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
                                modifier = Modifier
                                    .background(Color(0xFFF7FBF7))
                                    .width(200.dp)
                            ) {
                                val interactionSourceHome = remember { MutableInteractionSource() }
                                val isPressedHome by interactionSourceHome.collectIsPressedAsState()
                                
                                DropdownMenuItem(
                                    text = { Text("Home", color = Color(0xFF2D5A27), fontWeight = FontWeight.Medium) },
                                    onClick = { 
                                        mMenuExpanded = false
                                        navController.navigate(Screen.Home.route) 
                                    },
                                    modifier = Modifier.background(
                                        if (isPressedHome) Color(0xFFE8F5E9) else Color.Transparent
                                    ),
                                    interactionSource = interactionSourceHome,
                                    leadingIcon = { Icon(Icons.Default.Home, null, tint = Color(0xFF2D5A27)) }
                                )
                                
                                HorizontalDivider(modifier = Modifier.padding(horizontal = 12.dp), thickness = 0.5.dp)

                                val interactionSourceMap = remember { MutableInteractionSource() }
                                val isPressedMap by interactionSourceMap.collectIsPressedAsState()
                                
                                DropdownMenuItem(
                                    text = { Text("Mapa", color = Color(0xFF2D5A27), fontWeight = FontWeight.Medium) },
                                    onClick = { 
                                        mMenuExpanded = false
                                        navController.navigate(Screen.Map.route) 
                                    },
                                    modifier = Modifier.background(
                                        if (isPressedMap) Color(0xFFE8F5E9) else Color.Transparent
                                    ),
                                    interactionSource = interactionSourceMap,
                                    leadingIcon = { Icon(Icons.Default.Place, null, tint = Color(0xFF2D5A27)) }
                                )
                            }
                        }
                    },
                    actions = {
                        IconButton(onClick = { isSearchActive = true }) {
                            Icon(Icons.Default.Search, "Buscar", tint = Color(0xFF2D5A27), modifier = Modifier.size(32.dp))
                        }
                        IconButton(onClick = { /* Mais */ }) {
                            Icon(Icons.Default.MoreVert, "Mais", tint = Color(0xFF2D5A27), modifier = Modifier.size(32.dp))
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
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(bottom = 100.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(regionals) { number ->
                        Button(
                            onClick = { 
                                navController.navigate(Screen.EcopontosList.createRoute(number))
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF2D5A27)
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "Regional $number",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
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
                    val filteredRegionals = regionalData.filter { regional ->
                        regional.name.contains(searchQuery, ignoreCase = true) ||
                        regional.ecopontos.any { it.contains(searchQuery, ignoreCase = true) }
                    }

                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                        filteredRegionals.forEach { regional ->
                            ListItem(
                                headlineContent = { Text(regional.name) },
                                supportingContent = { Text(regional.ecopontos.take(2).joinToString(", ") + "...") },
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

            if (!isSearchActive) {
                Surface(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 24.dp, end = 16.dp)
                        .size(64.dp),
                    shape = CircleShape,
                    color = Color(0xFF1B4332),
                    shadowElevation = 4.dp
                ) {
                    IconButton(onClick = { navController.navigate(Screen.Map.route) }) {
                        Icon(
                            imageVector = Icons.Default.Place,
                            contentDescription = "Mapa",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
        }
    }
}
