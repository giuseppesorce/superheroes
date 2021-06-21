package com.giuseppesorce.superheroes.features.likednoliked

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.giuseppesorce.superheroes.R
import com.giuseppesorce.superheroes.adapters.CardStackAdapter
import com.giuseppesorce.superheroes.adapters.HerosAdapter
import com.giuseppesorce.superheroes.databinding.FragmentLikedislikeBinding
import com.giuseppesorce.superheroes.databinding.FragmentSplashBinding
import com.giuseppesorce.superheroes.models.LIKE_PARAMETER
import com.giuseppesorce.superheroes.models.SuperHero
import com.giuseppesorce.superheroes.models.navigationevents.LikeEvents
import com.giuseppesorce.superheroes.models.navigationevents.LikeState
import com.giuseppesorce.vodafone.architecture.base.BaseViewBindingDialogFragment
import com.giuseppesorce.vodafone.architecture.base.BaseViewBindingFragment
import com.giuseppesorce.vodafone.architecture.base.BaseViewModel
import com.giuseppesorce.vodafone.commons.encrypt.show
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LikeDislikeDialogFragment : BaseViewBindingDialogFragment<LikeState, LikeEvents>() {

    private var _binding: FragmentLikedislikeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LikeDislikeViewModel by viewModels()
    override fun provideBaseViewModel(): BaseViewModel<LikeState, LikeEvents>? = viewModel

    private var adapter: HerosAdapter? = HerosAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.dialog_progress)
    }

    override fun handleState(state: LikeState) {
    }

    override fun handleEvent(event: LikeEvents) {
        when(event){
            is LikeEvents.GoBack-> dismiss()
        }
    }

    override fun setupUI() {

        binding.rvHero.layoutManager = LinearLayoutManager(
            activity?.applicationContext,
            RecyclerView.VERTICAL,
            false
        )
        binding.rvHero.adapter = adapter
        adapter?.onActionLikeClickListener = { item: SuperHero, position: Int ->
            viewModel.onSelectLike(item)

        }

        adapter?.onActionDislikeClickListener = { item: SuperHero, position: Int ->
            viewModel.onSelectDislike(item)
        }
        binding.tvTitle.setOnClickListener {
            viewModel.onSelectBack()
        }
        binding.ivBack.setOnClickListener {
            viewModel.onSelectBack()
        }
    }

    override fun initFragment() {
        viewModel.getHeroes()
    }

    override fun observerData() {
        viewModel.superHeroesLD.observe(this, Observer { list ->
                   adapter?.itemsList = list ?: emptyList()
        })

        viewModel.showEmptyLD.observe(this, Observer { show ->
          binding.tvEmpty.show(show)
        })
    }

    override fun cleanFragment() {
        _binding = null
    }

    override fun setFragmentViewBinding(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = FragmentLikedislikeBinding.inflate(inflater, container, false)
        viewFrag = binding.root
    }


    companion object {
        val DIALOG_TAG: String? = "LikeDislikeDialogFragment"

        @JvmStatic
        fun newInstance(isLike: Boolean) = LikeDislikeDialogFragment().apply {
            arguments = Bundle().apply {
                putBoolean(LIKE_PARAMETER, isLike)
            }
        }
    }
}