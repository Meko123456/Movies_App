package com.merabk.moviesapp.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.merabk.moviesapp.databinding.FragmentMainBinding
import com.merabk.moviesapp.domain.model.AllMoviesDomainModel
import com.merabk.moviesapp.util.collectFlow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow


@AndroidEntryPoint
class MainFragment : Fragment(), MoviesAdapter.MovieItemClickListener {

    private val viewModel by viewModels<MainPageViewModel>()
    private var moviesAdapter: MoviesAdapter = MoviesAdapter(this)

//    private var transactionsAdapter: TvAdapter? = null

    //    private val debouncingQueryTextListener by lazy {
//        DebouncingQueryTextListener(
//            lifecycle = this@MainFragment.lifecycle,
//            onDebouncingQueryTextChange = viewModel::searchTv
//        )
//    }
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        collectData()
    }

    private fun setListeners() = with(binding) {
//        searchView.setOnQueryTextListener(debouncingQueryTextListener)
    }

    private fun collectData() = with(viewModel) {
        collectAllMoviesData(allMoviesData)
//        collectSearchMoviesData(searchMoviesData)
    }

//    private fun collectSearchMoviesData(searchMoviesData: StateFlow<DataState<List<MainContentDomainModel>>>) {
//        collectFlow(searchMoviesData) { dataState ->
//            when (dataState) {
//                is DataState.Loading -> {
//                }
//
//                is DataState.Success -> {
//                    val data = dataState.data
//                    moviesAdapter.submitList(data)
//                    binding.filmsRv.adapter = moviesAdapter
//
//                }
//
//                is DataState.Error -> {
//                    val errorMessage = dataState.message
//                    Log.d("errorMessage", "onCreate: $errorMessage")
//
//                }
//            }
//        }
//    }

    private fun collectAllMoviesData(allMoviesData: StateFlow<DataState<List<AllMoviesDomainModel>>>) {
        collectFlow(allMoviesData) { dataState ->
            when (dataState) {
                is DataState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is DataState.Success -> {
                    val data = dataState.data
                    moviesAdapter.submitList(data)
                    binding.recyclerViewTv.adapter = moviesAdapter
                    binding.progressBar.visibility = View.GONE
                }

                is DataState.Error -> {
                    val errorMessage = dataState.message
                    binding.progressBar.visibility = View.GONE
                    Log.d("errorMessage", "collectAllMoviesData: $errorMessage")
                }
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMovieItemClicked(movieId: Int) {
        val action = MainFragmentDirections.actionMainFragmentToDetailsFragment(movieId)
        findNavController().navigate(action)
    }
}