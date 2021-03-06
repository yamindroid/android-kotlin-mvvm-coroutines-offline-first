package com.ymo.ui.component.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ymo.data.Resource
import com.ymo.data.Status
import com.ymo.data.model.api.MovieItem
import com.ymo.data.model.api.MovieResponse
import com.ymo.databinding.FragmentPopularBinding
import com.ymo.ui.MainActivity
import com.ymo.utils.showSnackbar
import com.ymo.utils.showToast
import com.ymo.utils.toGone
import com.ymo.utils.toVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularFragment : Fragment(), TopRatedMoviesAdapter.OnClickedListener {

    private var _binding: FragmentPopularBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PopularViewModel by viewModels()

    private val favoriteMoviesAdapter: TopRatedMoviesAdapter by lazy {
        TopRatedMoviesAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPopularBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUIs()
        setupObservers()
    }

    private fun setupUIs() {
        viewModel.loadMovies()
        binding.rvTopRatedMovies.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = favoriteMoviesAdapter
        }

        binding.btnNoData.setOnClickListener {
            binding.loaderView.toVisible()
            binding.btnNoData.toGone()
            viewModel.loadMovies()
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.btnNoData.toGone()
            viewModel.loadMovies()
        }
    }

    private fun setupObservers() {
        viewModel.movieLiveData.observe(viewLifecycleOwner, ::moviesHandler)
        viewModel.addFavoriteStatusLiveData.observe(viewLifecycleOwner, ::addFavStatusHandler)
    }

    private fun addFavStatusHandler(resource: Resource<Unit>) {
        when (resource.status) {
            Status.LOADING -> showLoadingView()
            Status.SUCCESS -> resource.data?.let {
                showDataView(true)
                binding.root.showToast("Added to Favorites", Toast.LENGTH_SHORT)
            }
            Status.ERROR -> {
                showDataView(false)
                resource.errorMessage?.let { binding.root.showSnackbar(it, Snackbar.LENGTH_SHORT) }
            }
        }
    }

    private fun moviesHandler(resource: Resource<List<MovieItem?>?>) {
        when (resource.status) {
            Status.LOADING -> showLoadingView()
            Status.SUCCESS -> resource.data?.let {
                showDataView(resource.data.isNotEmpty())
                favoriteMoviesAdapter.submitList(it)
            }
            Status.ERROR -> {
                showDataView(false)
                resource.errorMessage?.let { binding.root.showSnackbar(it, Snackbar.LENGTH_SHORT) }
            }
        }

    }

    override fun onPosterClicked(movieItem: MovieItem) {
        findNavController().navigate(
            PopularFragmentDirections.actionPopularFragmentToMovieDetailFragment(
                movieItem
            )
        )
        (activity as MainActivity).navView(isVisible = false)
    }

    override fun onFavoriteClicked(movieItem: MovieItem) {
        viewModel.addFavoriteMovie(movieItem)
    }

    private fun showDataView(show: Boolean) {
        binding.btnNoData.visibility = if (show) View.GONE else View.VISIBLE
        binding.loaderView.toGone()
        binding.swipeRefreshLayout.isRefreshing = false
    }

    private fun showLoadingView() {
        binding.loaderView.toVisible()
        binding.btnNoData.toGone()
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).navView(isVisible = true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}