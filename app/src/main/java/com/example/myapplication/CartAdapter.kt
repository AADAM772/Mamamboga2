package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CartAdapter(
    private val onCartChanged: () -> Unit,
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    private var lines: List<CartLine> = Cart.lines

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon: ImageView = view.findViewById(R.id.cart_icon)
        val name: TextView = view.findViewById(R.id.cart_name)
        val unitPrice: TextView = view.findViewById(R.id.cart_unit_price)
        val lineTotal: TextView = view.findViewById(R.id.cart_line_total)
        val quantity: TextView = view.findViewById(R.id.cart_quantity)
        val minus: Button = view.findViewById(R.id.minus_button)
        val plus: Button = view.findViewById(R.id.plus_button)
        val remove: Button = view.findViewById(R.id.remove_button)
    }

    fun refresh() {
        lines = Cart.lines
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = lines.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val line = lines[position]
        val product = line.product
        val context = holder.itemView.context
        holder.icon.setImageResource(product.iconRes)
        holder.name.text = product.name
        holder.unitPrice.text = context.getString(R.string.price_per_unit, product.price, product.unit)
        holder.lineTotal.text = context.getString(R.string.currency_format, line.total)
        holder.quantity.text = line.quantity.toString()

        holder.plus.setOnClickListener { Cart.add(product); onCartChanged() }
        holder.minus.setOnClickListener { Cart.decrease(product); onCartChanged() }
        holder.remove.setOnClickListener { Cart.remove(product); onCartChanged() }
    }
}
