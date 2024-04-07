package com.merabk.moviesapp.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.merabk.moviesapp.databinding.ItemTvBinding
import com.merabk.moviesapp.domain.model.AllMoviesDomainModel
import com.merabk.moviesapp.util.loadImageWithGlide

class MoviesAdapter(
    private val movieItemClickListener: MovieItemClickListener
) : ListAdapter<AllMoviesDomainModel, MoviesAdapter.TvViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvViewHolder = TvViewHolder(
        ItemTvBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ), movieItemClickListener
    )

    override fun onBindViewHolder(holder: TvViewHolder, position: Int) =
        holder.bind(getItem(position))

    class TvViewHolder(
        private val binding: ItemTvBinding,
        private val movieItemClickListener: MovieItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(rvModel: AllMoviesDomainModel) = with(binding) {
            val url = rvModel.poster_path
            ivImage.loadImageWithGlide(url)
            tvTitle.text = rvModel.name
            tvVoteAverage.text = rvModel.vote_average.toString()
            itemView.setOnClickListener {
                movieItemClickListener.onMovieItemClicked(rvModel.id)
            }
        }
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<AllMoviesDomainModel>() {

            override fun areContentsTheSame(
                oldItem: AllMoviesDomainModel,
                newItem: AllMoviesDomainModel
            ): Boolean = oldItem == newItem

            override fun areItemsTheSame(
                oldItem: AllMoviesDomainModel,
                newItem: AllMoviesDomainModel
            ): Boolean = oldItem.id == newItem.id
        }
    }

    interface MovieItemClickListener {
        fun onMovieItemClicked(movieId: Int)
    }
}