package com.example.myapplication

data class CartLine(val product: Product, val quantity: Int) {
    val total: Int get() = product.price * quantity
}

/** App-wide cart shared by all three activities. Lives for the lifetime of the process. */
object Cart {
    private val quantities = linkedMapOf<Product, Int>()

    val lines: List<CartLine>
        get() = quantities.map { (product, qty) -> CartLine(product, qty) }

    val itemCount: Int get() = quantities.values.sum()

    val total: Int get() = quantities.entries.sumOf { (product, qty) -> product.price * qty }

    fun quantityOf(product: Product): Int = quantities[product] ?: 0

    fun add(product: Product) {
        quantities[product] = quantityOf(product) + 1
    }

    fun decrease(product: Product) {
        val qty = quantityOf(product) - 1
        if (qty <= 0) quantities.remove(product) else quantities[product] = qty
    }

    fun remove(product: Product) {
        quantities.remove(product)
    }

    fun clear() {
        quantities.clear()
    }
}
