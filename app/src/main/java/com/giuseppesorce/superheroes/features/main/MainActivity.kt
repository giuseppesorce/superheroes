package com.giuseppesorce.superheroes.features.main

import android.view.View
import androidx.activity.viewModels
import com.giuseppesorce.superheroes.databinding.ActivityMainBinding
import com.giuseppesorce.superheroes.models.navigationevents.MainEvents
import com.giuseppesorce.superheroes.models.navigationevents.MainState
import com.giuseppesorce.vodafone.architecture.viewmodels.BaseFlowViewModel
import com.giuseppesorce.vodafone.architecture.views.BaseFlowActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseFlowActivity<MainState, MainEvents>() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()

    override fun getDataBindingiView(): View {
        _binding = ActivityMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun provideBaseViewModel(): BaseFlowViewModel<MainState, MainEvents> = viewModel

    override fun handleState(state: MainState) {
    }

    override fun setupUI() {

    }

    override fun handleEvent(uiEvent: MainEvents?) {
    }

    override fun handleUiState(state: MainState?) {

    }
}


