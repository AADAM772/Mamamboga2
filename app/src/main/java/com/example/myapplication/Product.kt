package com.example.myapplication

import androidx.annotation.DrawableRes

enum class Category { VEGETABLE, FRUIT }

data class Product(
    val id: Int,
    val name: String,
    val price: Int,
    val unit: String,
    @DrawableRes val iconRes: Int,
    val category: Category,
)

object Catalog {
    val vegetables = listOf(
        Product(1, "Carrots", 80, "kg", R.drawable.ic_carrot, Category.VEGETABLE),
        Product(2, "Tomatoes", 120, "kg", R.drawable.ic_tomato, Category.VEGETABLE),
        Product(3, "Broccoli", 150, "head", R.drawable.ic_broccoli, Category.VEGETABLE),
        Product(4, "Cabbage", 60, "head", R.drawable.ic_cabbage, Category.VEGETABLE),
        Product(5, "Onions", 100, "kg", R.drawable.ic_onion, Category.VEGETABLE),
        Product(6, "Potatoes", 90, "kg", R.drawable.ic_potato, Category.VEGETABLE),
    )

    val fruits = listOf(
        Product(101, "Apples", 30, "piece", R.drawable.ic_apple, Category.FRUIT),
        Product(102, "Bananas", 15, "piece", R.drawable.ic_banana, Category.FRUIT),
        Product(103, "Oranges", 20, "piece", R.drawable.ic_orange, Category.FRUIT),
        Product(104, "Grapes", 350, "kg", R.drawable.ic_grapes, Category.FRUIT),
        Product(105, "Watermelon", 250, "piece", R.drawable.ic_watermelon, Category.FRUIT),
        Product(106, "Strawberries", 300, "punnet", R.drawable.ic_strawberry, Category.FRUIT),
    )
}
