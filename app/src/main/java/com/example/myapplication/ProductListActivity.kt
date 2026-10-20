package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar

/** Shared screen for browsing a list of products. Vegetables and Fruits each extend this. */
abstract class ProductListActivity : AppCompatActivity() {

    protected abstract val products: List<Product>
    @get:StringRes protected abstract val titleRes: Int
    @get:IdRes protected abstract val navItemId: Int

    private lateinit var adapter: ProductAdapter
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)
        setupEdgeToEdge(findViewById(R.id.main))

        findViewById<MaterialToolbar>(R.id.toolbar).setTitle(titleRes)

        adapter = ProductAdapter(products) { product ->
            Cart.add(product)
            updateCartBadge(bottomNav)
            Snackbar.make(bottomNav, getString(R.string.added_to_cart, product.name), Snackbar.LENGTH_SHORT)
                .setAnchorView(bottomNav)
                .setAction(R.string.view_cart) {
                    startActivity(Intent(this, CartActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT))
                }
                .show()
        }
        findViewById<RecyclerView>(R.id.product_list).apply {
            layoutManager = GridLayoutManager(this@ProductListActivity, 2)
            adapter = this@ProductListActivity.adapter
        }

        bottomNav = findViewById(R.id.bottom_nav)
        setupBottomNav(bottomNav, navItemId)
    }

    override fun onResume() {
        super.onResume()
        // The cart may have changed on another screen.
        adapter.notifyDataSetChanged()
        bottomNav.selectedItemId = navItemId
        updateCartBadge(bottomNav)
    }
}
