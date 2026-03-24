package com.example.ecoponto.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ecoponto.R
import com.example.ecoponto.ui.navigation.Screen
import com.example.ecoponto.viewmodel.MapUiState
import com.example.ecoponto.viewmodel.MapViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(navController: NavController, viewModel: MapViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    var mMenuExpanded by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }

    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(androidx.compose.ui.geometry.Offset.Zero) }

    Scaffold(
        topBar = {
            if (!isSearchActive) {
                TopAppBar(
                    title = { Text("Fortaleza", color = Color(0xFF2D5A27), fontWeight = FontWeight.Bold) },
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
                                    .width(220.dp)
                            ) {
                                DropdownMenuItem(
                                    text = { Text("Home", color = Color(0xFF2D5A27)) },
                                    onClick = { 
                                        mMenuExpanded = false
                                        navController.navigate(Screen.Home.route) 
                                    }
                                )
                                HorizontalDivider(modifier = Modifier.padding(horizontal = 12.dp))
                                DropdownMenuItem(
                                    text = { Text("Pontos de Coleta", color = Color(0xFF2D5A27)) },
                                    onClick = { 
                                        mMenuExpanded = false
                                        navController.navigate(Screen.Regionals.route) 
                                    }
                                )
                                HorizontalDivider(modifier = Modifier.padding(horizontal = 12.dp))
                                DropdownMenuItem(
                                    text = { Text("Como Reciclar", color = Color(0xFF2D5A27)) },
                                    onClick = { 
                                        mMenuExpanded = false
                                        navController.navigate(Screen.Guide.route) 
                                    }
                                )
                            }
                        }
                    },
                    actions = {
                        IconButton(onClick = { isSearchActive = true }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Buscar",
                                tint = Color(0xFF2D5A27),
                                modifier = Modifier.size(32.dp)
                            )
                        }
                        IconButton(onClick = { /* Mais */ }) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "Mais",
                                tint = Color(0xFF2D5A27),
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFF7FBF7))
                )
            }
        },
        bottomBar = {
            if (!isSearchActive) {
                NavigationBar(containerColor = Color.White, contentColor = Color(0xFF2D5A27)) {
                    NavigationBarItem(
                        icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Home") },
                        label = { Text("Home") },
                        selected = false,
                        onClick = { navController.navigate(Screen.Home.route) }
                    )
                    NavigationBarItem(
                        icon = { Icon(imageVector = Icons.Default.Place, contentDescription = "Mapa") },
                        label = { Text("Mapa") },
                        selected = true,
                        onClick = { }
                    )
                    NavigationBarItem(
                        icon = { Icon(imageVector = Icons.Default.LocationOn, contentDescription = "Pontos") },
                        label = { Text("Pontos") },
                        selected = false,
                        onClick = { navController.navigate(Screen.Regionals.route) }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize().background(Color(0xFFE0E0E0))) {

            Image(
                painter = painterResource(id = R.drawable.map_fortaleza),
                contentDescription = "Mapa de Fortaleza",
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTransformGestures { _, pan, zoom, _ ->
                            scale *= zoom
                            offset += pan
                        }
                    }
                    .graphicsLayer(
                        scaleX = maxOf(1f, minOf(3f, scale)),
                        scaleY = maxOf(1f, minOf(3f, scale)),
                        translationX = offset.x,
                        translationY = offset.y
                    ),
                contentScale = ContentScale.Crop
            )

            if (uiState is MapUiState.Success) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = Color(0xFF1976D2),
                        modifier = Modifier.size(40.dp).align(Alignment.Center).offset(x = (-50).dp, y = (-20).dp)
                    )
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = Color(0xFF1976D2),
                        modifier = Modifier.size(40.dp).align(Alignment.Center).offset(x = 40.dp, y = 60.dp)
                    )
                }
            }

            if (isSearchActive) {
                SearchBar(
                    query = searchQuery,
                    onQueryChange = { searchQuery = it },
                    onSearch = { isSearchActive = false },
                    active = isSearchActive,
                    onActiveChange = { isSearchActive = it },
                    placeholder = { Text("Buscar Ecoponto ou Bairro...") },
                    leadingIcon = { 
                        IconButton(onClick = { isSearchActive = false }) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Voltar")
                        }
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(imageVector = Icons.Default.Close, contentDescription = "Limpar")
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = SearchBarDefaults.colors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        ListItem(
                            headlineContent = { Text("Ecoponto Vila Velha") },
                            supportingContent = { Text("Regional 1") },
                            leadingContent = { Icon(imageVector = Icons.Default.Search, contentDescription = null) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            if (!isSearchActive) {
                Surface(
                    modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp).size(56.dp),
                    shape = CircleShape,
                    color = Color(0xFF1B4332),
                    shadowElevation = 6.dp
                ) {
                    IconButton(onClick = { 
                        scale = 1f
                        offset = androidx.compose.ui.geometry.Offset.Zero
                    }) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Resetar Vista",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}
