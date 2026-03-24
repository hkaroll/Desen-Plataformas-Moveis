package com.example.ecoponto.model

data class Regional(
    val id: Int,
    val name: String,
    val ecopontos: List<String>
)

val regionalData = listOf(
    Regional(1, "Regional 1", listOf(
        "Barra do Ceará", "Vila Velha", "Barra do Ceará II", "Vila Velha II",
        "Carlito Pamplona", "Pirambu", "Leste Oeste", "Pirambu II",
        "Jacarecanga", "Cristo Redentor", "Álvaro Weyne", "Vila do Mar I",
        "Lagoa do Urubu I", "Floresta", "Lagoa do Urubu II"
    )),
    Regional(2, "Regional 2", listOf(
        "São João do Tauape", "Varjota", "Verdes Mares", "Vicente Pinzon"
    )),
    Regional(3, "Regional 3", listOf(
        "Jovita Feitosa", "Monte Castelo", "Antônio Bezerra", "Rodolfo Teófilo", "São Gerardo"
    )),
    Regional(4, "Regional 4", listOf(
        "Fátima", "Vila Peri", "Damas", "Parangaba", "Parreão", "Itaoca", "Aguanambi"
    )),
    Regional(5, "Regional 5", listOf(
        "Granja Portugal", "Bonsucesso", "Siqueira", "Granja Lisboa"
    )),
    Regional(6, "Regional 6", listOf(
        "Cid. Funcionários I", "Cid. Funcionários II", "Messejana I", "Lagoa Redonda",
        "São Bento I", "São Bento II", "Paupina", "Tancredo Neves", "Aerolândia", "Lagoa da Zeza"
    )),
    Regional(7, "Regional 7", listOf(
        "Edson Queiroz", "Cidade 2000", "Sapiranga", "Guararapes", "Cocó",
        "Luc. Cavalcante", "Sapiranga II", "Sapiranga III"
    )),
    Regional(8, "Regional 8", listOf(
        "Cj. José Walter", "Serrinha", "Itaperi", "Dias Macedo", "Cidade Jardim II",
        "Jardim União", "José Walter II"
    )),
    Regional(9, "Regional 9", listOf(
        "Jangurussu", "St. São João", "Cajazeiras", "Jardim Glória", "Santa Filomena", "Al. das Palmeiras"
    )),
    Regional(10, "Regional 10", listOf(
        "Cj. Esperança", "Aracapé", "Jardim Cearense", "Mondubim", "Mondubim II", "Cartier"
    )),
    Regional(11, "Regional 11", listOf(
        "Conjunto Ceará", "João XXII", "Cj. Ceará II", "Autran Nunes",
        "Autran Nunes II", "Pici", "Jóquei Clube", "Dias Macedo", "Pq. Dois Irmãos"
    )),
    Regional(12, "Regional 12", listOf(
        "Centro", "Centro II", "São Sebastião"
    ))
)
