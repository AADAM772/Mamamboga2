package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

/** Applies edge-to-edge padding to the root view. The bottom nav handles its own bottom inset. */
fun AppCompatActivity.setupEdgeToEdge(root: View) {
    enableEdgeToEdge()
    ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
        insets
    }
}

/** Wires the shared bottom navigation so each tab opens its activity. */
fun Activity.setupBottomNav(nav: BottomNavigationView, selectedId: Int) {
    nav.selectedItemId = selectedId
    nav.setOnItemSelectedListener { item ->
        if (item.itemId == selectedId) return@setOnItemSelectedListener true
        val target = when (item.itemId) {
            R.id.nav_vegetables -> MainActivity::class.java
            R.id.nav_fruits -> FruitsActivity::class.java
            R.id.nav_cart -> CartActivity::class.java
            else -> return@setOnItemSelectedListener false
        }
        startActivity(Intent(this, target).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT))
        // Keep the current tab highlighted; the target activity highlights its own tab.
        false
    }
}

/** Shows the number of items in the cart as a badge on the cart tab. */
fun updateCartBadge(nav: BottomNavigationView) {
    val count = Cart.itemCount
    if (count > 0) {
        nav.getOrCreateBadge(R.id.nav_cart).number = count
    } else {
        nav.removeBadge(R.id.nav_cart)
    }
}
