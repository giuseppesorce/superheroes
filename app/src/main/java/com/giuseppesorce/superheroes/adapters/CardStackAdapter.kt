package com.giuseppesorce.superheroes.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import coil.transform.RoundedCornersTransformation

import com.giuseppesorce.superheroes.R
import com.giuseppesorce.superheroes.models.SuperHero
import kotlin.properties.Delegates


class CardStackAdapter(items: List<SuperHero> = emptyList()) :
    RecyclerView.Adapter<CardStackAdapter.ViewHolderItem>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderItem =
        ViewHolderItem(
            LayoutInflater.from(parent?.context).inflate(
                R.layout.item_card,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = itemsList.size

    var itemsList by Delegates.observable(items) { _, _, _ -> notifyDataSetChanged() }

    override fun onBindViewHolder(holder: ViewHolderItem, position: Int) {
        var view = holder.bindItems(itemsList[position])

    }

    class ViewHolderItem(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvName: TextView by lazy { itemView.findViewById<TextView>(R.id.tvName) }
        private val ivHero: ImageView by lazy { itemView.findViewById<ImageView>(R.id.ivHero) }
        private val tvDescriptionLabel: TextView by lazy { itemView.findViewById<TextView>(R.id.tvDescription) }
        private val tvSeries: TextView by lazy { itemView.findViewById<TextView>(R.id.tvSeries) }
        private val tvComics: TextView by lazy { itemView.findViewById<TextView>(R.id.tvComics) }
        private val tvEvents: TextView by lazy { itemView.findViewById<TextView>(R.id.tvEvents) }
        private val tvStories: TextView by lazy { itemView.findViewById<TextView>(R.id.tvStories) }
        var dimenRound = itemView.context.resources.getDimensionPixelSize(R.dimen.round_card)
        fun bindItems(item: SuperHero): View {
            tvName.text = item.name
            tvDescriptionLabel.text = item.description
            tvEvents.text = item.totalEvents
            tvSeries.text = item.totalSeries
            tvComics.text = item.totalComics
            tvStories.text = item.totalStories
            ivHero.load(item.image) {
                size(500, 500)
                scale(Scale.FIT)
                placeholder(R.drawable.placeholder)
                transformations(
                    RoundedCornersTransformation(
                        dimenRound.toFloat(),
                        dimenRound.toFloat(),
                        0f,
                        0f
                    )
                )
            }
            return itemView
        }
    }
}