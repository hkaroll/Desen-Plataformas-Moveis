package com.example.ecoponto.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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

enum class GuidePageType { INTRO, MATERIAL, TIPS }

data class GuidePage(
    val type: GuidePageType,
    val title: String,
    val color: Color = Color.White,
    val textColor: Color = Color.Black,
    val description: String = "",
    val canRecycle: List<String> = emptyList(),
    val cannotRecycle: List<String> = emptyList(),
    val tip: String = ""
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuideScreen(navController: NavController) {
    var mMenuExpanded by remember { mutableStateOf(false) }
    
    val pages = listOf(
        GuidePage(
            GuidePageType.INTRO,
            "Por que reciclar é importante?",
            Color(0xFFE8F5E9), Color(0xFF2D5A27),
            "A reciclagem reduz a poluição, economiza recursos naturais e energia, diminui a quantidade de lixo em aterros e ajuda a preservar o meio ambiente para as futuras gerações."
        ),
        GuidePage(
            GuidePageType.MATERIAL, "Papel e Papelão", Color(0xFFBBDEFB), Color(0xFF1976D2),
            canRecycle = listOf("Jornais e revistas", "Caixas de papelão", "Folhas de papel", "Envelopes", "Cadernos sem espiral"),
            cannotRecycle = listOf("Papel higiênico", "Guardanapos usados", "Papel carbono", "Papel metalizado", "Fotografias"),
            tip = "Dobre as caixas para economizar espaço. Remova fitas adesivas e grampos."
        ),
        GuidePage(
            GuidePageType.MATERIAL, "Plástico", Color(0xFFFFEBEE), Color(0xFFD32F2F),
            canRecycle = listOf("Garrafas PET", "Embalagens de limpeza", "Potes de iogurte", "Sacolas plásticas", "Tampas de garrafa"),
            cannotRecycle = listOf("Plástico filme sujo", "Esponjas", "Cabos de panela", "Espuma", "Fraldas descartáveis"),
            tip = "Lave as embalagens antes de descartar. Amasse as garrafas para reduzir volume."
        ),
        GuidePage(
            GuidePageType.MATERIAL, "Vidro", Color(0xFFE8F5E9), Color(0xFF388E3C),
            canRecycle = listOf("Garrafas", "Potes de conserva", "Frascos de perfume", "Copos de vidro", "Cacos de vidro"),
            cannotRecycle = listOf("Espelhos", "Lâmpadas", "Vidros temperados", "Cerâmica", "Porcelana"),
            tip = "Remova tampas e rótulos. Embale cacos em jornal para segurança."
        ),
        GuidePage(
            GuidePageType.MATERIAL, "Metal", Color(0xFFFFF9C4), Color(0xFFFBC02D),
            canRecycle = listOf("Latas de alumínio", "Latas de aço", "Tampinhas de garrafa", "Arames", "Pregos e parafusos"),
            cannotRecycle = listOf("Esponjas de aço", "Latas de tinta", "Latas de aerosol com pressão", "Pilhas e baterias"),
            tip = "Amasse as latas para economizar espaço. Limpe resíduos de alimentos."
        ),
        GuidePage(
            GuidePageType.MATERIAL, "Eletrônicos", Color(0xFFF3E5F5), Color(0xFF7B1FA2),
            canRecycle = listOf("Celulares", "Computadores", "Pilhas e baterias", "Cabos e fios", "Eletrodomésticos pequenos"),
            cannotRecycle = listOf("Lâmpadas incandescentes", "Espelhos", "Vidros comuns"),
            tip = "Remova dados pessoais antes de descartar. Procure pontos especializados."
        ),
        GuidePage(
            GuidePageType.MATERIAL, "Orgânico", Color(0xFFEFEBE9), Color(0xFF8D6E63),
            canRecycle = listOf("Restos de frutas e vegetais", "Cascas de ovos", "Borra de café", "Folhas secas", "Restos de poda"),
            cannotRecycle = listOf("Restos de carne", "Laticínios", "Óleo de cozinha", "Fezes de animais"),
            tip = "Ideal para compostagem. Mantenha separado de outros materiais."
        ),
        GuidePage(GuidePageType.TIPS, "Dicas Gerais para Reciclagem")
    )

    val pagerState = rememberPagerState(pageCount = { pages.size })

    Scaffold(
        containerColor = Color(0xFFF7FBF7),
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    Box {
                        IconButton(onClick = { mMenuExpanded = true }) {
                            Icon(Icons.Default.Menu, null, tint = Color(0xFF2D5A27), modifier = Modifier.size(32.dp))
                        }
                        DropdownMenu(
                            expanded = mMenuExpanded,
                            onDismissRequest = { mMenuExpanded = false },
                            modifier = Modifier.background(Color(0xFFF7FBF7)).width(200.dp)
                        ) {
                            DropdownMenuItem(text = { Text("Home") }, onClick = { navController.navigate(Screen.Home.route) })
                            DropdownMenuItem(text = { Text("Mapa") }, onClick = { navController.navigate(Screen.Map.route) })
                            DropdownMenuItem(text = { Text("Pontos") }, onClick = { navController.navigate(Screen.Regionals.route) })
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
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f).padding(16.dp),
                contentPadding = PaddingValues(horizontal = 0.dp),
                pageSpacing = 16.dp
            ) { pageIndex ->
                val page = pages[pageIndex]
                when (page.type) {
                    GuidePageType.INTRO -> IntroPage(page)
                    GuidePageType.MATERIAL -> MaterialPage(page)
                    GuidePageType.TIPS -> TipsPage()
                }
            }

            Row(
                Modifier.height(48.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(pages.size) { iteration ->
                    val color = if (pagerState.currentPage == iteration) Color(0xFF2D5A27) else Color.LightGray
                    Box(modifier = Modifier.padding(4.dp).clip(CircleShape).background(color).size(8.dp))
                }
            }
        }
    }
}

@Composable
fun IntroPage(page: GuidePage) {
    Card(
        modifier = Modifier.fillMaxSize(),
        colors = CardDefaults.cardColors(containerColor = page.color),
        shape = RoundedCornerShape(24.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFC8E6C9))
    ) {
        Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(page.title, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = page.textColor, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(24.dp))
            Text(page.description, fontSize = 16.sp, color = page.textColor, lineHeight = 24.sp)
            Spacer(modifier = Modifier.height(32.dp))
            BenefitItem("Economiza água", "Até 50% menos água")
            Spacer(modifier = Modifier.height(12.dp))
            BenefitItem("Economiza energia", "Até 95% menos energia")
            Spacer(modifier = Modifier.height(12.dp))
            BenefitItem("Reduz lixo", "Menos aterros sanitários")
        }
    }
}

@Composable
fun MaterialPage(page: GuidePage) {
    Box(modifier = Modifier.fillMaxSize()) {
        Card(
            modifier = Modifier.fillMaxSize(),
            colors = CardDefaults.cardColors(containerColor = page.color),
            shape = RoundedCornerShape(24.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, page.textColor.copy(alpha = 0.2f))
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(page.title, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = page.textColor, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(24.dp))
                
                Surface(color = Color.White, shape = RoundedCornerShape(16.dp)) {
                    Column(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
                        Text("O que reciclar", fontWeight = FontWeight.Bold, color = page.textColor)
                        page.canRecycle.forEach { Text("• $it", fontSize = 15.sp, color = Color.DarkGray) }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Surface(color = Color.White, shape = RoundedCornerShape(16.dp)) {
                    Column(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
                        Text("O que NÃO reciclar", fontWeight = FontWeight.Bold, color = page.textColor)
                        page.cannotRecycle.forEach { Text("• $it", fontSize = 15.sp, color = Color.DarkGray) }
                    }
                }
                
                Spacer(modifier = Modifier.weight(1f))
                Text("Dica: ${page.tip}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = page.textColor)
            }
        }
        
        Surface(
            modifier = Modifier.align(Alignment.TopEnd).padding(16.dp).size(48.dp),
            shape = CircleShape,
            color = Color(0xFF1B4332)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(Icons.Default.LocationOn, null, tint = Color.White, modifier = Modifier.size(24.dp))
            }
        }
    }
}

@Composable
fun TipsPage() {
    Card(
        modifier = Modifier.fillMaxSize(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(24.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text("Dicas Gerais para Reciclagem", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(24.dp))
            GeneralTip("Limpe os materiais", "Lave embalagens para remover restos de alimentos e produtos")
            GeneralTip("Separe corretamente", "Mantenha os materiais separados por categoria")
            GeneralTip("Verifique os símbolos", "Procure pelos símbolos de reciclagem nas embalagens")
            GeneralTip("Descarte regularmente", "Não acumule muito material reciclável em casa")
        }
    }
}

@Composable
fun BenefitItem(title: String, subtitle: String) {
    Surface(modifier = Modifier.fillMaxWidth(), color = Color.White, shape = RoundedCornerShape(12.dp)) {
        Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(title, fontWeight = FontWeight.Bold, color = Color(0xFF2D5A27))
            Text(subtitle, fontSize = 13.sp, color = Color.Gray)
        }
    }
}

@Composable
fun GeneralTip(title: String, desc: String) {
    Column(modifier = Modifier.padding(bottom = 20.dp)) {
        Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Text(desc, fontSize = 14.sp, color = Color.DarkGray)
    }
}
