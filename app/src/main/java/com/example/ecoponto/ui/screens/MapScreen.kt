package com.example.ecoponto.ui.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ecoponto.ui.navigation.Screen
import com.example.ecoponto.viewmodel.MapUiState
import com.example.ecoponto.viewmodel.MapViewModel
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    navController: NavController, 
    viewModel: MapViewModel = viewModel(),
    ecopontoName: String? = null
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val mapView = remember { MapView(context) }
    
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions.entries.all { it.value }) {
            enableMyLocation(mapView, context)
        }
    }

    LaunchedEffect(Unit) {
        Configuration.getInstance().load(context, context.getSharedPreferences("osmdroid", Context.MODE_PRIVATE))
        Configuration.getInstance().userAgentValue = context.packageName
        
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        
        if (permissions.all { ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED }) {
            enableMyLocation(mapView, context)
        } else {
            locationPermissionLauncher.launch(permissions)
        }
    }

    // Lógica para centralizar no ecoponto vindo por argumento
    LaunchedEffect(uiState, ecopontoName) {
        if (uiState is MapUiState.Success && ecopontoName != null) {
            val ecopontos = (uiState as MapUiState.Success).ecopontos
            val target = ecopontos.find { it.nome.contains(ecopontoName, ignoreCase = true) }
            if (target != null) {
                val point = GeoPoint(target.latitude, target.longitude)
                mapView.controller.setCenter(point)
                mapView.controller.setZoom(17.0)
                
                // Abre o info window após um pequeno delay para garantir que o marcador existe
                mapView.overlays.filterIsInstance<Marker>().find { 
                    it.position.latitude == target.latitude && 
                    it.position.longitude == target.longitude 
                }?.showInfoWindow()
            }
        }
    }

    var mMenuExpanded by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            if (!isSearchActive) {
                TopAppBar(
                    title = { Text("Mapa de Ecopontos", color = Color(0xFF2D5A27), fontWeight = FontWeight.Bold) },
                    navigationIcon = {
                        Box {
                            IconButton(onClick = { mMenuExpanded = true }) {
                                Icon(Icons.Default.Menu, "Menu", tint = Color(0xFF2D5A27), modifier = Modifier.size(32.dp))
                            }
                            DropdownMenu(
                                expanded = mMenuExpanded,
                                onDismissRequest = { mMenuExpanded = false },
                                modifier = Modifier.background(Color(0xFFF7FBF7)).width(220.dp)
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
                            }
                        }
                    },
                    actions = {
                        IconButton(onClick = { isSearchActive = true }) {
                            Icon(Icons.Default.Search, "Buscar", tint = Color(0xFF2D5A27), modifier = Modifier.size(32.dp))
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
                        icon = { Icon(Icons.Default.Home, "Home") },
                        label = { Text("Home") },
                        selected = false,
                        onClick = { navController.navigate(Screen.Home.route) }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Place, "Mapa") },
                        label = { Text("Mapa") },
                        selected = true,
                        onClick = { }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.LocationOn, "Pontos") },
                        label = { Text("Pontos") },
                        selected = false,
                        onClick = { navController.navigate(Screen.Regionals.route) }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            AndroidView(
                factory = { 
                    mapView.apply {
                        setTileSource(TileSourceFactory.MAPNIK)
                        setMultiTouchControls(true)
                        controller.setZoom(13.0)
                        controller.setCenter(GeoPoint(-3.7327, -38.5270))
                    }
                },
                modifier = Modifier.fillMaxSize(),
                update = { mview ->
                    if (uiState is MapUiState.Success) {
                        val ecopontos = (uiState as MapUiState.Success).ecopontos
                        mview.overlays.removeAll { it is Marker && it.relatedObject == "ecoponto" }
                        
                        ecopontos.forEach { ecoponto ->
                            val marker = Marker(mview)
                            marker.position = GeoPoint(ecoponto.latitude, ecoponto.longitude)
                            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                            marker.title = ecoponto.nome
                            marker.snippet = ecoponto.endereco
                            marker.relatedObject = "ecoponto"
                            mview.overlays.add(marker)
                        }
                        mview.invalidate()
                    }
                }
            )

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
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Voltar")
                        }
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(Icons.Default.Close, "Limpar")
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = SearchBarDefaults.colors(containerColor = Color.White)
                ) {
                    if (uiState is MapUiState.Success) {
                        val allEcopontos = (uiState as MapUiState.Success).ecopontos
                        val filteredEcopontos = allEcopontos.filter { 
                            it.nome.contains(searchQuery, ignoreCase = true) ||
                            it.regional.contains(searchQuery, ignoreCase = true)
                        }

                        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                            filteredEcopontos.forEach { ecoponto ->
                                ListItem(
                                    headlineContent = { Text(ecoponto.nome) },
                                    supportingContent = { Text(ecoponto.endereco) },
                                    leadingContent = { Icon(Icons.Default.LocationOn, null, tint = Color(0xFF2D5A27)) },
                                    modifier = Modifier.fillMaxWidth().clickable {
                                        isSearchActive = false
                                        searchQuery = ""
                                        
                                        val point = GeoPoint(ecoponto.latitude, ecoponto.longitude)
                                        mapView.controller.animateTo(point)
                                        mapView.controller.setZoom(17.0)
                                        
                                        mapView.overlays.filterIsInstance<Marker>().find { 
                                            it.position.latitude == ecoponto.latitude && 
                                            it.position.longitude == ecoponto.longitude 
                                        }?.showInfoWindow()
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun enableMyLocation(mapView: MapView, context: Context) {
    val myLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(context), mapView)
    myLocationOverlay.enableMyLocation()
    myLocationOverlay.enableFollowLocation()
    myLocationOverlay.isDrawAccuracyEnabled = true
    mapView.overlays.add(myLocationOverlay)
}
