package com.omega_r.entities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.omega_r.libs.entities.text.Text
import com.omega_r.libs.entities.text.fromRes
import com.omega_r.libs.entities.text.setText
import kotlinx.android.synthetic.main.activity_text_examples.*

class TextExamplesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_examples)

        val adapter = Adapter()
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this)

        adapter.setItems(
            listOf(
                Text.from("Text from String") to Text.from("String Example"),
                Text.from("Text from Resource") to Text.fromRes(R.string.app_name)
            )
        )
    }
}

private class Adapter : RecyclerView.Adapter<Adapter.VH>() {

    private var items: List<Pair<Text, Text>> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_text_example,
                parent,
                false
            )
        )
    }

    fun setItems(items: List<Pair<Text, Text>>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        if (position != RecyclerView.NO_POSITION) {
            val item = items[position]
            holder.descriptionTextView.setText(item.first)
            holder.exampleTextView.setText(item.second)
        }
    }

    private class VH(v: View) : RecyclerView.ViewHolder(v) {
        val descriptionTextView: TextView = v.findViewById(R.id.textview_description)
        val exampleTextView: TextView = v.findViewById(R.id.textview_example)
    }
}