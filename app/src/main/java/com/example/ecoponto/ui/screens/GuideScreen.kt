package com.example.ecoponto.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ecoponto.ui.navigation.Screen

data class GuideItem(
    val title: String,
    val titleColor: Color,
    val containerColor: Color,
    val canRecycle: List<String>,
    val cannotRecycle: List<String>,
    val tip: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuideScreen(navController: NavController) {
    var mMenuExpanded by remember { mutableStateOf(false) }
    
    val guideItems = listOf(
        GuideItem(
            "Papel e Papelão", Color(0xFF1976D2), Color(0xFFE3F2FD),
            listOf("Jornais e revistas", "Caixas de papelão", "Folhas de papel", "Envelopes", "Cadernos sem espiral"),
            listOf("Papel higiênico", "Guardanapos usados", "Papel carbono", "Papel metalizado", "Fotografias"),
            "Dica: Dobre as caixas para economizar espaço. Remova fitas adesivas e grampos."
        ),
        GuideItem(
            "Plástico", Color(0xFFD32F2F), Color(0xFFFFEBEE),
            listOf("Garrafas PET", "Embalagens de limpeza", "Potes de iogurte", "Sacolas plásticas", "Tampas de garrafa"),
            listOf("Plástico filme sujo", "Esponjas", "Cabos de panela", "Espuma", "Fraldas descartáveis"),
            "Dica: Lave as embalagens antes de descartar. Amasse as garrafas para reduzir volume."
        ),
        GuideItem(
            "Vidro", Color(0xFF388E3C), Color(0xFFE8F5E9),
            listOf("Garrafas", "Potes de conserva", "Frascos de perfume", "Copos de vidro", "Cacos de vidro"),
            listOf("Espelhos", "Lâmpadas", "Vidros temperados", "Cerâmica", "Porcelana"),
            "Dica: Remova tampas e rótulos. Embale cacos em jornal para segurança."
        ),
        GuideItem(
            "Metal", Color(0xFFFBC02D), Color(0xFFFFF9C4),
            listOf("Latas de alumínio", "Latas de aço", "Tampinhas de garrafa", "Arames", "Pregos e parafusos"),
            listOf("Esponjas de aço", "Latas de tinta", "Latas de aerosol com pressão", "Pilhas e baterias"),
            "Dica: Amasse as latas para economizar espaço. Limpe resíduos de alimentos."
        ),
        GuideItem(
            "Eletrônicos", Color(0xFF7B1FA2), Color(0xFFF3E5F5),
            listOf("Celulares", "Computadores", "Pilhas e baterias", "Cabos e fios", "Eletrodomésticos pequenos"),
            listOf("Lâmpadas incandescentes", "Espelhos", "Vidros comuns"),
            "Dica: Remova dados pessoais antes de descartar. Procure pontos especializados."
        ),
        GuideItem(
            "Orgânico", Color(0xFF8D6E63), Color(0xFFEFEBE9),
            listOf("Restos de frutas e vegetais", "Cascas de ovos", "Borra de café", "Folhas secas", "Restos de poda"),
            listOf("Restos de carne", "Laticínios", "Óleo de cozinha", "Fezes de animais"),
            "Dica: Ideal para compostagem. Mantenha separado de outros materiais."
        )
    )

    val pagerState = rememberPagerState(pageCount = { guideItems.size })

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
                                onClick = { navController.navigate(Screen.Home.route) }
                            )
                            DropdownMenuItem(
                                text = { Text("Mapa", color = Color(0xFF2D5A27)) },
                                onClick = { navController.navigate(Screen.Map.route) }
                            )
                            DropdownMenuItem(
                                text = { Text("Pontos", color = Color(0xFF2D5A27)) },
                                onClick = { navController.navigate(Screen.Regionals.route) }
                            )
                        }
                    }
                },
                actions = {
                    IconButton(onClick = { }) { Icon(Icons.Default.Search, null, tint = Color(0xFF2D5A27)) }
                    IconButton(onClick = { }) { Icon(Icons.Default.MoreVert, null, tint = Color(0xFF2D5A27)) }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        bottomBar = {
            NavigationBar(containerColor = Color.White, contentColor = Color(0xFF2D5A27)) {
                NavigationBarItem(icon = { Icon(Icons.Default.Home, null) }, label = { Text("Home") }, selected = false, onClick = { navController.navigate(Screen.Home.route) })
                NavigationBarItem(icon = { Icon(Icons.Default.Place, null) }, label = { Text("Mapa") }, selected = false, onClick = { navController.navigate(Screen.Map.route) })
                NavigationBarItem(icon = { Icon(Icons.Default.LocationOn, null) }, label = { Text("Pontos") }, selected = false, onClick = { navController.navigate(Screen.Regionals.route) })
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            
            // Card Fixo: Importância da Reciclagem
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFC8E6C9))
            ) {
                Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "Por que reciclar é importante?",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2D5A27),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        "A reciclagem reduz a poluição, economiza recursos naturais e energia, diminui a quantidade de lixo em aterros e ajuda a preservar o meio ambiente para as futuras gerações.",
                        fontSize = 14.sp,
                        color = Color(0xFF2D5A27),
                        textAlign = TextAlign.Start,
                        lineHeight = 20.sp
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        BenefitCard("Economiza água", "Até 50% menos água")
                        BenefitCard("Economiza energia", "Até 95% menos energia")
                        BenefitCard("Reduz lixo", "Menos aterros sanitários")
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 0.dp),
                pageSpacing = 16.dp
            ) { page ->
                val item = guideItems[page]
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = item.containerColor),
                    shape = RoundedCornerShape(16.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, item.titleColor.copy(alpha = 0.3f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            item.title,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = item.titleColor,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // O que reciclar
                        Surface(
                            color = Color.White, 
                            shape = RoundedCornerShape(12.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, item.titleColor.copy(alpha = 0.2f))
                        ) {
                            Column(modifier = Modifier.padding(12.dp).fillMaxWidth()) {
                                Text("O que reciclar", fontWeight = FontWeight.Bold, color = item.titleColor, fontSize = 15.sp)
                                Spacer(modifier = Modifier.height(8.dp))
                                item.canRecycle.forEach { Text("• $it", fontSize = 14.sp, color = Color.DarkGray) }
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // O que NÃO reciclar
                        Surface(
                            color = Color.White, 
                            shape = RoundedCornerShape(12.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, item.titleColor.copy(alpha = 0.2f))
                        ) {
                            Column(modifier = Modifier.padding(12.dp).fillMaxWidth()) {
                                Text("O que NÃO reciclar", fontWeight = FontWeight.Bold, color = item.titleColor, fontSize = 15.sp)
                                Spacer(modifier = Modifier.height(8.dp))
                                item.cannotRecycle.forEach { Text("• $it", fontSize = 14.sp, color = Color.DarkGray) }
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            "Dica: ${item.tip}", 
                            fontSize = 13.sp, 
                            fontWeight = FontWeight.Bold, 
                            color = item.titleColor,
                            textAlign = TextAlign.Start
                        )
                    }
                }
            }

            Row(
                Modifier.height(40.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(guideItems.size) { iteration ->
                    val color = if (pagerState.currentPage == iteration) Color(0xFF2D5A27) else Color.LightGray
                    Box(modifier = Modifier.padding(4.dp).clip(CircleShape).background(color).size(8.dp))
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Dicas Gerais para Reciclagem", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.Black)
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    GeneralTipItem("Limpe os materiais", "Lave embalagens para remover restos de alimentos e produtos")
                    GeneralTipItem("Separe corretamente", "Mantenha os materiais separados por categoria")
                    GeneralTipItem("Verifique os símbolos", "Procure pelos símbolos de reciclagem nas embalagens")
                    GeneralTipItem("Descarte regularmente", "Não acumule muito material reciclável em casa")
                }
            }
            
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun BenefitCard(title: String, subtitle: String) {
    Surface(
        modifier = Modifier.fillMaxWidth().height(54.dp),
        color = Color.White,
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE0E0E0))
    ) {
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF2D5A27))
            Text(subtitle, fontSize = 12.sp, color = Color.Gray)
        }
    }
}

@Composable
fun GeneralTipItem(title: String, desc: String) {
    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        Text(title, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color.Black)
        Text(desc, fontSize = 14.sp, color = Color.DarkGray, lineHeight = 18.sp)
    }
}
