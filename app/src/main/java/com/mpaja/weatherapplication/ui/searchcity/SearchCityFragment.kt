package com.mpaja.weatherapplication.ui.searchcity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mpaja.weatherapplication.R
import com.mpaja.weatherapplication.data.repository.model.CityModel
import com.mpaja.weatherapplication.databinding.FragmentSearchCityBinding
import com.mpaja.weatherapplication.di.Injectable
import com.mpaja.weatherapplication.di.ViewModelInjectionFactory
import com.mpaja.weatherapplication.ui.searchcity.adapter.CityListRVAdapter
import com.mpaja.weatherapplication.ui.searchcity.adapter.HistoryListRVAdapter
import com.mpaja.weatherapplication.utils.EventObserver
import com.mpaja.weatherapplication.utils.viewModelProvider
import kotlinx.android.synthetic.main.fragment_search_city.*
import javax.inject.Inject


class SearchCityFragment : Fragment(), Injectable, CityListRVAdapter.OnItemClickedListener,
    HistoryListRVAdapter.OnHistoryItemClickListener {

    @Inject
    lateinit var viewModelInjectionFactory: ViewModelInjectionFactory<SearchCityViewModel>

    lateinit var viewModel: SearchCityViewModel
    lateinit var binding: FragmentSearchCityBinding

    lateinit var cityListRVAdapter: CityListRVAdapter
    lateinit var historyListRVAdapter: HistoryListRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_city, container, false)
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = viewModelProvider(viewModelInjectionFactory)
        binding.viewModel = viewModel

        prepareRecyclerViews()
        registerObservables()

        search_city.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    if (query.matches("^([a-zA-Z\u0080-\u024F]+(?:. |-| |'))*[a-zA-Z\u0080-\u024F]*$".toRegex())) {
                        viewModel.saveSearchHistory(it)
                        viewModel.getCitiesList(it)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            R.string.city_name_error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                return true
            }
        })

        search_city.findViewById<View>(R.id.search_close_btn)
            .setOnClickListener {
                search_city.setQuery("", false)
            }

        viewModel.getHistory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (recycler_cities.visibility == View.VISIBLE) {
                        recycler_cities.visibility = View.INVISIBLE
                        recycler_history.visibility = View.VISIBLE
                        search_city.setQuery("", false)
                    } else requireActivity().finish()
                }
            })
    }

    private fun prepareRecyclerViews() {
        val dividerItemDecoration = DividerItemDecoration(
            context,
            LinearLayoutManager.VERTICAL
        )
        getDrawable(
            requireContext(),
            R.drawable.divider_layer
        )?.let { dividerItemDecoration.setDrawable(it) }

        cityListRVAdapter = CityListRVAdapter()
        recycler_cities.adapter = cityListRVAdapter
        recycler_cities.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recycler_cities.addItemDecoration(dividerItemDecoration)

        cityListRVAdapter.onItemClickedListener = this

        historyListRVAdapter = HistoryListRVAdapter()
        recycler_history.adapter = historyListRVAdapter
        recycler_history.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        recycler_history.addItemDecoration(
            dividerItemDecoration
        )
        historyListRVAdapter.onHistoryItemClickListener = this
    }

    private fun registerObservables() {
        viewModel.uiState.observe(viewLifecycleOwner, Observer { uiState ->
            when (uiState) {
                CityFragmentUiState.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    recycler_cities.visibility = View.GONE
                    recycler_history.visibility = View.GONE
                    text_no_results.visibility = View.GONE
                    text_no_history.visibility = View.GONE
                }
                is CityFragmentUiState.DataSearched -> {
                    progressBar.visibility = View.GONE

                    recycler_history.visibility = View.GONE
                    if (uiState.cityModelList.isEmpty()) {
                        recycler_cities.visibility = View.GONE
                        text_no_results.visibility = View.VISIBLE
                    } else {
                        text_no_results.visibility = View.GONE
                        recycler_cities.visibility = View.VISIBLE
                        cityListRVAdapter.addCities(uiState.cityModelList)
                    }
                }
                is CityFragmentUiState.DataLoaded -> {
                    progressBar.visibility = View.GONE
                    recycler_cities.visibility = View.GONE
                    if (uiState.cityModelList.isEmpty()) {
                        recycler_history.visibility = View.GONE
                        text_no_history.visibility = View.VISIBLE
                    } else {
                        text_no_history.visibility = View.GONE
                        recycler_history.visibility = View.VISIBLE
                        historyListRVAdapter.addCities(uiState.cityModelList)
                    }

                }
            }
        })
        viewModel.errorLiveData.observe(viewLifecycleOwner, EventObserver {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onClick(cityId: Int) {
        findNavController().navigate(
            SearchCityFragmentDirections.actionSearchCityFragmentToCityWeatherFragment(cityId)
        )
    }

    override fun onClick(cityName: String) {
        search_city.setQuery(cityName, true)
    }
}

sealed class CityFragmentUiState {
    object Loading : CityFragmentUiState()
    data class DataSearched(val cityModelList: List<CityModel>) : CityFragmentUiState()
    data class DataLoaded(val cityModelList: List<CityModel>) : CityFragmentUiState()
}