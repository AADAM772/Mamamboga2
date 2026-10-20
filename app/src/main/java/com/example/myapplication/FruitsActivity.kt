package com.example.myapplication

/** Lists the fruits on sale. */
class FruitsActivity : ProductListActivity() {
    override val products = Catalog.fruits
    override val titleRes = R.string.title_fruits
    override val navItemId = R.id.nav_fruits
}
