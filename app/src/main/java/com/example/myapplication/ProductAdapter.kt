package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class ProductAdapter(
    private val products: List<Product>,
    private val onAdd: (Product) -> Unit,
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon: ImageView = view.findViewById(R.id.product_icon)
        val name: TextView = view.findViewById(R.id.product_name)
        val price: TextView = view.findViewById(R.id.product_price)
        val inCart: TextView = view.findViewById(R.id.product_in_cart)
        val addButton: MaterialButton = view.findViewById(R.id.add_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = products.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        val context = holder.itemView.context
        holder.icon.setImageResource(product.iconRes)
        holder.name.text = product.name
        holder.price.text = context.getString(R.string.price_per_unit, product.price, product.unit)

        val qty = Cart.quantityOf(product)
        holder.inCart.text = context.getString(R.string.in_cart_count, qty)
        holder.inCart.visibility = if (qty > 0) View.VISIBLE else View.INVISIBLE

        holder.addButton.setOnClickListener {
            onAdd(product)
            notifyItemChanged(holder.bindingAdapterPosition)
        }
    }
}
