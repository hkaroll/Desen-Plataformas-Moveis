package com.example.ecoponto.model

data class Ecoponto(
    val id: String,
    val nome: String,
    val endereco: String,
    val latitude: Double,
    val longitude: Double,
    val materiaisAceitos: List<String>,
    val regional: String
)
