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
import com.giuseppesorce.vodafone.commons.encrypt.show
import kotlin.properties.Delegates


class HerosAdapter(items: List<SuperHero> = emptyList()) :
    RecyclerView.Adapter<HerosAdapter.ViewHolderItem>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderItem =
        ViewHolderItem(
            LayoutInflater.from(parent?.context).inflate(
                R.layout.item_card_hero,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = itemsList.size

    var onActionLikeClickListener: ((item: SuperHero, position: Int) -> Unit)? = null
    var onActionDislikeClickListener: ((item: SuperHero, position: Int) -> Unit)? = null

    var itemsList by Delegates.observable(items) { _, _, _ -> notifyDataSetChanged() }


    override fun onBindViewHolder(holder: ViewHolderItem, position: Int) {
        var view = holder.bindItems(itemsList[position])
        holder.ivLike.setOnClickListener {
            onActionLikeClickListener?.invoke(itemsList[position], position)
        }
        holder.ivDislike.setOnClickListener {
            onActionDislikeClickListener?.invoke(itemsList[position], position)
        }

    }

    class ViewHolderItem(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvName: TextView by lazy { itemView.findViewById<TextView>(R.id.tvName) }
        private val ivHero: ImageView by lazy { itemView.findViewById<ImageView>(R.id.ivHero) }
        val ivLike: ImageView by lazy { itemView.findViewById<ImageView>(R.id.ivLike) }
        val ivDislike: ImageView by lazy { itemView.findViewById<ImageView>(R.id.ivDislike) }


        fun bindItems(item: SuperHero): View {
            tvName.text = item.name
            ivLike.show(!item.isLike)
            ivDislike.show(item.isLike)
            ivHero.load(item.image) {
                size(500, 500)
                scale(Scale.FIT)
                placeholder(R.drawable.placeholder)
                transformations(RoundedCornersTransformation(0f, 0f, 0f, 0f))
            }

            return itemView
        }
    }
}