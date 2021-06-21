package com.giuseppesorce.superheroes.features.splash

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController

import com.giuseppesorce.superheroes.R
import com.giuseppesorce.superheroes.databinding.FragmentSplashBinding
import com.giuseppesorce.superheroes.models.navigationevents.MainEvents
import com.giuseppesorce.superheroes.models.navigationevents.MainState
import com.giuseppesorce.vodafone.architecture.base.BaseViewBindingFragment
import com.giuseppesorce.vodafone.architecture.base.BaseViewModel

import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashFragment : BaseViewBindingFragment<MainState, MainEvents>() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!


    private val viewModel: SplashViewModel by viewModels()
    override fun provideBaseViewModel(): BaseViewModel<MainState, MainEvents>? = viewModel

    override fun handleState(state: MainState) {
    }

    override fun handleEvent(event: MainEvents) {
        when (event) {
            is MainEvents.GotoHome -> {
                findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
            }
        }
    }

    override fun setupUI() {
    }

    override fun initFragment() {
        viewModel.checkUser()
    }

    override fun cleanFragment() {
        _binding = null
    }

    override fun setFragmentViewBinding(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        viewFrag = binding.root
    }
}