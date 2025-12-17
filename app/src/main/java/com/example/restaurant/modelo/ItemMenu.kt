package com.example.restaurant.modelo

class ItemMenu(val nombre: String, val precio: String) {
    // Función auxiliar para obtener el precio como número (ej: "$12.000" -> 12000)
    fun precioNumerico(): Int {
        val precioLimpio = precio.replace("[^\\d]".toRegex(), "")
        return precioLimpio.toIntOrNull() ?: 0
    }
}