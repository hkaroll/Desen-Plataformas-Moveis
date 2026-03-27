package com.example.ecoponto.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ecoponto.model.Ecoponto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class MapUiState {
    object Loading : MapUiState()
    data class Success(val ecopontos: List<Ecoponto>) : MapUiState()
    data class Error(val message: String) : MapUiState()
}

class MapViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<MapUiState>(MapUiState.Loading)
    val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()

    init {
        loadEcopontos()
    }

    private fun loadEcopontos() {
        try {
            // Mapeamento preciso de coordenadas baseado na geografia de Fortaleza (Land-based)
            val coords = mapOf(
                // Regional 1
                "Barra do Ceará" to Pair(-3.7100, -38.5830), "Vila Velha" to Pair(-3.7310, -38.5980),
                "Barra do Ceará II" to Pair(-3.7050, -38.5750), "Vila Velha II" to Pair(-3.7250, -38.5900),
                "Carlito Pamplona" to Pair(-3.7180, -38.5590), "Pirambu" to Pair(-3.7120, -38.5460),
                "Leste Oeste" to Pair(-3.7160, -38.5490), "Pirambu II" to Pair(-3.7110, -38.5400),
                "Jacarecanga" to Pair(-3.7200, -38.5370), "Cristo Redentor" to Pair(-3.7150, -38.5610),
                "Álvaro Weyne" to Pair(-3.7240, -38.5670), "Vila do Mar I" to Pair(-3.7080, -38.5800),
                "Lagoa do Urubu I" to Pair(-3.7280, -38.5750), "Floresta" to Pair(-3.7210, -38.5780),
                "Lagoa do Urubu II" to Pair(-3.7290, -38.5720),
                
                // Regional 2
                "São João do Tauape" to Pair(-3.7540, -38.5080), "Varjota" to Pair(-3.7300, -38.4870),
                "Verdes Mares" to Pair(-3.7380, -38.4780), "Vicente Pinzon" to Pair(-3.7190, -38.4760),
                
                // Regional 3
                "Jovita Feitosa" to Pair(-3.7380, -38.5450), "Monte Castelo" to Pair(-3.7240, -38.5520),
                "Antônio Bezerra" to Pair(-3.7340, -38.5810), "Rodolfo Teófilo" to Pair(-3.7450, -38.5530),
                "São Gerardo" to Pair(-3.7310, -38.5560),
                
                // Regional 4
                "Fátima" to Pair(-3.7510, -38.5280), "Vila Peri" to Pair(-3.7780, -38.5550),
                "Damas" to Pair(-3.7560, -38.5430), "Parangaba" to Pair(-3.7740, -38.5610),
                "Parreão" to Pair(-3.7550, -38.5340), "Itaoca" to Pair(-3.7760, -38.5520),
                "Aguanambi" to Pair(-3.7480, -38.5200),
                
                // Regional 5
                "Granja Portugal" to Pair(-3.7810, -38.6030), "Bonsucesso" to Pair(-3.7700, -38.5950),
                "Siqueira" to Pair(-3.7890, -38.6120), "Granja Lisboa" to Pair(-3.7780, -38.6150),
                
                // Regional 6
                "Cid. Funcionários I" to Pair(-3.7890, -38.4870), "Cid. Funcionários II" to Pair(-3.7920, -38.4820),
                "Messejana I" to Pair(-3.8240, -38.4980), "Lagoa Redonda" to Pair(-3.8210, -38.4670),
                "São Bento I" to Pair(-3.8350, -38.4850), "São Bento II" to Pair(-3.8380, -38.4820),
                "Paupina" to Pair(-3.8510, -38.4880), "Tancredo Neves" to Pair(-3.7790, -38.5020),
                "Aerolândia" to Pair(-3.7660, -38.5080), "Lagoa da Zeza" to Pair(-3.7850, -38.4920),
                
                // Regional 7
                "Edson Queiroz" to Pair(-3.7680, -38.4760), "Cidade 2000" to Pair(-3.7410, -38.4730),
                "Sapiranga" to Pair(-3.7910, -38.4630), "Guararapes" to Pair(-3.7640, -38.4810),
                "Cocó" to Pair(-3.7450, -38.4870), "Luc. Cavalcante" to Pair(-3.7780, -38.4830),
                "Sapiranga II" to Pair(-3.7950, -38.4600), "Sapiranga III" to Pair(-3.7980, -38.4580),
                
                // Regional 8
                "Cj. José Walter" to Pair(-3.8050, -38.5530), "Serrinha" to Pair(-3.7810, -38.5430),
                "Itaperi" to Pair(-3.7890, -38.5510), "Dias Macedo" to Pair(-3.7940, -38.5250),
                "Cidade Jardim II" to Pair(-3.8150, -38.5600), "Jardim União" to Pair(-3.8180, -38.5350),
                "José Walter II" to Pair(-3.8080, -38.5500),
                
                // Regional 9
                "Jangurussu" to Pair(-3.8410, -38.5120), "St. São João" to Pair(-3.8480, -38.5050),
                "Cajazeiras" to Pair(-3.8120, -38.5010), "Jardim Glória" to Pair(-3.8550, -38.5180),
                "Santa Filomena" to Pair(-3.8510, -38.5100), "Al. das Palmeiras" to Pair(-3.8580, -38.5200),
                
                // Regional 10
                "Cj. Esperança" to Pair(-3.8120, -38.5910), "Aracapé" to Pair(-3.8340, -38.6010),
                "Jardim Cearense" to Pair(-3.8010, -38.5750), "Mondubim" to Pair(-3.7980, -38.5870),
                "Mondubim II" to Pair(-3.8020, -38.5820), "Cartier" to Pair(-3.8050, -38.5850),
                
                // Regional 11
                "Conjunto Ceará" to Pair(-3.7630, -38.6010), "João XXII" to Pair(-3.7610, -38.5830),
                "Cj. Ceará II" to Pair(-3.7650, -38.5950), "Autran Nunes" to Pair(-3.7480, -38.5910),
                "Autran Nunes II" to Pair(-3.7510, -38.5880), "Pici" to Pair(-3.7430, -38.5710),
                "Jóquei Clube" to Pair(-3.7510, -38.5630), "Pq. Dois Irmãos" to Pair(-3.7980, -38.5350),
                
                // Regional 12
                "Centro" to Pair(-3.7310, -38.5260), "Centro II" to Pair(-3.7280, -38.5240),
                "São Sebastião" to Pair(-3.7250, -38.5210)
            )

            val rawData = listOf(
                "Regional 1" to listOf("Barra do Ceará", "Vila Velha", "Barra do Ceará II", "Vila Velha II", "Carlito Pamplona", "Pirambu", "Leste Oeste", "Pirambu II", "Jacarecanga", "Cristo Redentor", "Álvaro Weyne", "Vila do Mar I", "Lagoa do Urubu I", "Floresta", "Lagoa do Urubu II"),
                "Regional 2" to listOf("São João do Tauape", "Varjota", "Verdes Mares", "Vicente Pinzon"),
                "Regional 3" to listOf("Jovita Feitosa", "Monte Castelo", "Antônio Bezerra", "Rodolfo Teófilo", "São Gerardo"),
                "Regional 4" to listOf("Fátima", "Vila Peri", "Damas", "Parangaba", "Parreão", "Itaoca", "Aguanambi"),
                "Regional 5" to listOf("Granja Portugal", "Bonsucesso", "Siqueira", "Granja Lisboa"),
                "Regional 6" to listOf("Cid. Funcionários I", "Cid. Funcionários II", "Messejana I", "Lagoa Redonda", "São Bento I", "São Bento II", "Paupina", "Tancredo Neves", "Aerolândia", "Lagoa da Zeza"),
                "Regional 7" to listOf("Edson Queiroz", "Cidade 2000", "Sapiranga", "Guararapes", "Cocó", "Luc. Cavalcante", "Sapiranga II", "Sapiranga III"),
                "Regional 8" to listOf("Cj. José Walter", "Serrinha", "Itaperi", "Dias Macedo", "Cidade Jardim II", "Jardim União", "José Walter II"),
                "Regional 9" to listOf("Jangurussu", "St. São João", "Cajazeiras", "Jardim Glória", "Santa Filomena", "Al. das Palmeiras"),
                "Regional 10" to listOf("Cj. Esperança", "Aracapé", "Jardim Cearense", "Mondubim", "Mondubim II", "Cartier"),
                "Regional 11" to listOf("Conjunto Ceará", "João XXII", "Cj. Ceará II", "Autran Nunes", "Autran Nunes II", "Pici", "Jóquei Clube", "Pq. Dois Irmãos"),
                "Regional 12" to listOf("Centro", "Centro II", "São Sebastião")
            )

            val allEcopontos = mutableListOf<Ecoponto>()
            var idCounter = 1

            rawData.forEach { (regionalName, bairros) ->
                bairros.forEach { bairro ->
                    val pair = coords[bairro] ?: Pair(-3.7327, -38.5270)
                    allEcopontos.add(
                        Ecoponto(
                            id = idCounter.toString(),
                            nome = "Ecoponto $bairro",
                            endereco = "Localizado em $bairro, Fortaleza - CE",
                            latitude = pair.first,
                            longitude = pair.second,
                            materiaisAceitos = listOf("Papel", "Plástico", "Metal", "Vidro"),
                            regional = regionalName
                        )
                    )
                    idCounter++
                }
            }

            _uiState.value = MapUiState.Success(allEcopontos)
        } catch (e: Exception) {
            _uiState.value = MapUiState.Error("Falha ao carregar ecopontos: ${e.message}")
        }
    }
}
