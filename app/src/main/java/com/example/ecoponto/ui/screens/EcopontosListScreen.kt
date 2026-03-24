package com.example.ecoponto.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EcopontosListScreen(navController: NavController, regionalId: Int) {
    var mMenuExpanded by remember { mutableStateOf(false) }
    val regional = regionalData.find { it.id == regionalId } ?: regionalData[0]

    Scaffold(
        containerColor = Color(0xFFF7FBF7),
        topBar = {
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
                                onClick = { navController.navigate("home") }
                            )
                        }
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Search, "Buscar", tint = Color(0xFF2D5A27), modifier = Modifier.size(32.dp))
                    }
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.MoreVert, "Mais", tint = Color(0xFF2D5A27), modifier = Modifier.size(32.dp))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        bottomBar = {
            NavigationBar(containerColor = Color.White, contentColor = Color(0xFF2D5A27)) {
                NavigationBarItem(icon = { Icon(Icons.Default.Home, "Home") }, label = { Text("Home") }, selected = false, onClick = { navController.navigate("home") })
                NavigationBarItem(icon = { Icon(Icons.Default.Place, "Mapa") }, label = { Text("Mapa") }, selected = false, onClick = { navController.navigate("map") })
                NavigationBarItem(icon = { Icon(Icons.Default.LocationOn, "Pontos") }, label = { Text("Pontos") }, selected = true, onClick = { })
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
                        Icon(Icons.Default.ArrowBack, "Voltar", tint = Color.White)
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
                            modifier = Modifier.fillMaxWidth().height(80.dp)
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

            Surface(
                modifier = Modifier.align(Alignment.BottomEnd).padding(bottom = 20.dp, end = 20.dp).size(50.dp),
                shape = CircleShape,
                color = Color(0xFFE8F5E9),
                shadowElevation = 2.dp
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.LocationOn, null, tint = Color(0xFF2D5A27), modifier = Modifier.size(24.dp))
                }
            }
        }
    }
}
