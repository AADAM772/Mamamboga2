package com.example.myapplication

/** Launcher screen: lists the vegetables on sale. */
class MainActivity : ProductListActivity() {
    override val products = Catalog.vegetables
    override val titleRes = R.string.title_vegetables
    override val navItemId = R.id.nav_vegetables
}
