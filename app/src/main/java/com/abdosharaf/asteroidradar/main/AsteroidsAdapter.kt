package com.abdosharaf.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abdosharaf.asteroidradar.Asteroid
import com.abdosharaf.asteroidradar.databinding.ItemAsteroidBinding

class AsteroidsAdapter : ListAdapter<Asteroid, AsteroidViewHolder>(AsteroidsDiffUtil) {

    var onItemClicked: ((Asteroid) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        return AsteroidViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onItemClicked)
    }
}

class AsteroidViewHolder private constructor(private val binding: ItemAsteroidBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(asteroid: Asteroid, clickListener: ((Asteroid) -> Unit)?) {
        binding.asteroid = asteroid
        binding.root.setOnClickListener {
            clickListener?.invoke(asteroid)
        }
    }

    companion object {
        fun from(parent: ViewGroup): AsteroidViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            return AsteroidViewHolder(
                ItemAsteroidBinding.inflate(layoutInflater, parent, false)
            )
        }
    }
}

object AsteroidsDiffUtil : DiffUtil.ItemCallback<Asteroid>() {
    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return newItem.id == oldItem.id
    }

    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return newItem == oldItem
    }
}