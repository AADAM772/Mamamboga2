package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlin.random.Random

/** Shows what the customer is buying and lets them complete the purchase. */
class CartActivity : AppCompatActivity() {

    private lateinit var adapter: CartAdapter
    private lateinit var cartList: RecyclerView
    private lateinit var emptyView: TextView
    private lateinit var checkoutBar: View
    private lateinit var totalText: TextView
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        setupEdgeToEdge(findViewById(R.id.main))

        cartList = findViewById(R.id.cart_list)
        emptyView = findViewById(R.id.empty_view)
        checkoutBar = findViewById(R.id.checkout_bar)
        totalText = findViewById(R.id.total_text)
        bottomNav = findViewById(R.id.bottom_nav)

        adapter = CartAdapter(::render)
        cartList.layoutManager = LinearLayoutManager(this)
        cartList.adapter = adapter

        findViewById<Button>(R.id.checkout_button).setOnClickListener { confirmCheckout() }
        setupBottomNav(bottomNav, R.id.nav_cart)
    }

    override fun onResume() {
        super.onResume()
        bottomNav.selectedItemId = R.id.nav_cart
        render()
    }

    private fun render() {
        adapter.refresh()
        val isEmpty = Cart.itemCount == 0
        emptyView.visibility = if (isEmpty) View.VISIBLE else View.GONE
        cartList.visibility = if (isEmpty) View.GONE else View.VISIBLE
        checkoutBar.visibility = if (isEmpty) View.GONE else View.VISIBLE
        totalText.text = getString(R.string.currency_format, Cart.total)
        updateCartBadge(bottomNav)
    }

    private fun confirmCheckout() {
        val total = getString(R.string.currency_format, Cart.total)
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.confirm_order_title)
            .setMessage(getString(R.string.confirm_order_message, Cart.itemCount, total))
            .setNegativeButton(R.string.cancel, null)
            .setPositiveButton(R.string.place_order) { _, _ -> completeOrder(total) }
            .show()
    }

    private fun completeOrder(total: String) {
        val orderNumber = Random.nextInt(10000, 99999)
        Cart.clear()
        render()
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.order_placed_title)
            .setMessage(getString(R.string.order_placed_message, orderNumber, total))
            .setCancelable(false)
            .setPositiveButton(R.string.continue_shopping) { _, _ ->
                startActivity(
                    Intent(this, MainActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                )
                finish()
            }
            .show()
    }
}
