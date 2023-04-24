package com.omega_r.libs.entities.examples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.omega_r.libs.entities.examples.databinding.ActivityTextExamplesBinding
import com.omega_r.libs.entities.text.OmegaText
import com.omega_r.libs.entities.text.from
import com.omega_r.libs.entities.text.setText
import com.omega_r.libs.entities.text.styled.styles.OmegaFontStyleTextStyle
import com.omega_r.libs.entities.text.styled.styles.OmegaTextStyle

class TextExamplesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTextExamplesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_examples)
        binding = ActivityTextExamplesBinding.inflate(layoutInflater)

        val adapter = Adapter()
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(this)

        adapter.setItems(
            listOf(
                OmegaText.from("Text from String") to OmegaText.from("String Example")
                    .plus(OmegaTextStyle.from(OmegaFontStyleTextStyle.Style.BOLD)),
                OmegaText.from("Text from Resource") to OmegaText.from(R.string.app_name)
            )
        )
    }
}

private class Adapter : RecyclerView.Adapter<Adapter.VH>() {

    private var items: List<Pair<OmegaText, OmegaText>> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_text_example,
                parent,
                false
            )
        )
    }

    fun setItems(items: List<Pair<OmegaText, OmegaText>>) {
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

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val descriptionTextView: TextView = v.findViewById(R.id.textview_description)
        val exampleTextView: TextView = v.findViewById(R.id.textview_example)
    }
}