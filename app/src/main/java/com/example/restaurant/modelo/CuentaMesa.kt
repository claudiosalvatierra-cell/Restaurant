package com.example.restaurant.modelo

class CuentaMesa(val mesa: Int) {
    private val items: MutableList<ItemMesa> = mutableListOf()
    var aceptaPropina: Boolean = true

    fun agregarItem(itemMenu: ItemMenu, cantidad: Int) {
        val itemMesa = ItemMesa(itemMenu, cantidad)
        items.add(itemMesa)
    }

    // Permite actualizar la cantidad de un item ya agregado según su posición en la lista
    fun actualizarCantidad(indice: Int, nuevaCantidad: Int) {
        if (indice in items.indices) {
            items[indice].cantidad = nuevaCantidad
        }
    }

    fun calcularTotalSinPropina(): Int {
        return items.sumOf { it.calcularSubtotal() }
    }

    fun calcularPropina(): Int {
        val total = calcularTotalSinPropina()
        // La propina es el 10% si el cliente acepta
        return if (aceptaPropina) (total * 0.10).toInt() else 0
    }

    fun calcularTotalConPropina(): Int {
        return calcularTotalSinPropina() + calcularPropina()
    }
}