package com.example.ecoponto.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ecoponto.R
import com.example.ecoponto.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    var mMenuExpanded by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Color(0xFFF7FBF7),
        topBar = {
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
                                .width(220.dp)
                        ) {
                            DropdownMenuItem(
                                text = { Text("Mapa", fontSize = 16.sp, color = Color(0xFF2D5A27), fontWeight = FontWeight.Medium) },
                                onClick = { 
                                    mMenuExpanded = false
                                    navController.navigate(Screen.Map.route) 
                                }
                            )
                            HorizontalDivider(modifier = Modifier.padding(horizontal = 12.dp), thickness = 0.5.dp)
                            DropdownMenuItem(
                                text = { Text("Pontos de Coleta", fontSize = 16.sp, color = Color(0xFF2D5A27), fontWeight = FontWeight.Medium) },
                                onClick = { 
                                    mMenuExpanded = false
                                    navController.navigate(Screen.Regionals.route) 
                                }
                            )
                            HorizontalDivider(modifier = Modifier.padding(horizontal = 12.dp), thickness = 0.5.dp)
                            DropdownMenuItem(
                                text = { Text("Como Reciclar", fontSize = 16.sp, color = Color(0xFF2D5A27), fontWeight = FontWeight.Medium) },
                                onClick = { 
                                    mMenuExpanded = false
                                    navController.navigate(Screen.Guide.route) 
                                }
                            )
                        }
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Search, contentDescription = "Buscar", tint = Color(0xFF2D5A27), modifier = Modifier.size(32.dp))
                    }
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Mais", tint = Color(0xFF2D5A27), modifier = Modifier.size(32.dp))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                contentColor = Color(0xFF2D5A27)
            ) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = true,
                    onClick = { }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Place, contentDescription = "Mapa") },
                    label = { Text("Mapa") },
                    selected = false,
                    onClick = { navController.navigate(Screen.Map.route) }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.LocationOn, contentDescription = "Pontos") },
                    label = { Text("Pontos") },
                    selected = false,
                    onClick = { navController.navigate(Screen.Regionals.route) }
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(10.dp))

                Image(
                    painter = painterResource(id = R.drawable.logo_ecoponto),
                    contentDescription = "Logo Ecoponto",
                    modifier = Modifier.size(140.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Bem-vindo (a)",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2D5A27),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Encontre pontos de coleta de recicláveis próximos de você e contribua para um planeta mais sustentável.",
                    textAlign = TextAlign.Center,
                    fontSize = 17.sp,
                    lineHeight = 24.sp,
                    color = Color(0xFF2D5A27),
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(40.dp))

                Button(
                    onClick = { navController.navigate(Screen.Map.route) },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B4332)),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Icon(Icons.Default.LocationOn, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Encontrar Pontos", fontSize = 16.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedButton(
                    onClick = { navController.navigate(Screen.Guide.route) },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    border = ButtonDefaults.outlinedButtonBorder.copy(brush = androidx.compose.ui.graphics.SolidColor(Color(0xFF1B4332))),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Icon(Icons.Default.Info, contentDescription = null, tint = Color(0xFF1B4332))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Como Reciclar", fontSize = 16.sp, color = Color(0xFF1B4332))
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedButton(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    border = ButtonDefaults.outlinedButtonBorder.copy(brush = androidx.compose.ui.graphics.SolidColor(Color(0xFFD32F2F))),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Icon(Icons.Default.FavoriteBorder, contentDescription = null, tint = Color(0xFFD32F2F))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Meus Favoritos", fontSize = 16.sp, color = Color(0xFFD32F2F))
                }

                Spacer(modifier = Modifier.weight(1f))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF0D2319))
                        .padding(vertical = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Ecoponto - Conectando você à reciclagem",
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Juntos por um futuro mais sustentável",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 11.sp
                    )
                }
            }

            Surface(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 60.dp, end = 16.dp)
                    .size(60.dp),
                shape = CircleShape,
                color = Color(0xFF1B4332),
                shadowElevation = 4.dp
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, tint = Color.White, modifier = Modifier.size(30.dp))
                }
            }
        }
    }
}
