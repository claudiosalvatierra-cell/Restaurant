package com.example.restaurant.modelo

class ItemMesa(val itemMenu: ItemMenu, var cantidad: Int) {
    fun calcularSubtotal(): Int {
        return itemMenu.precioNumerico() * cantidad
    }
}