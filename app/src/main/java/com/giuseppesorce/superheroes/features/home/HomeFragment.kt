package com.giuseppesorce.superheroes.features.home

import android.animation.Animator
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController

import androidx.recyclerview.widget.DefaultItemAnimator
import com.giuseppesorce.superheroes.R

import com.giuseppesorce.superheroes.adapters.CardStackAdapter
import com.giuseppesorce.superheroes.databinding.FragmentHomeBinding
import com.giuseppesorce.superheroes.features.likednoliked.LikeDislikeDialogFragment
import com.giuseppesorce.superheroes.models.navigationevents.HomeEvents
import com.giuseppesorce.superheroes.models.navigationevents.HomeState
import com.giuseppesorce.vodafone.architecture.base.BaseViewBindingFragment
import com.giuseppesorce.vodafone.architecture.base.BaseViewModel
import com.giuseppesorce.vodafone.commons.encrypt.show
import com.google.android.material.snackbar.Snackbar
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.SwipeableMethod
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseViewBindingFragment<HomeState, HomeEvents>(), CardStackListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var layoutManager: CardStackLayoutManager
    override fun provideBaseViewModel(): BaseViewModel<HomeState, HomeEvents>? = viewModel

    private var adapter: CardStackAdapter?= CardStackAdapter()

    override fun handleState(state: HomeState) {

        when(state){
            is HomeState.ShowLikeAnimation-> showLikeAnimation()
            is HomeState.ShowDisLikeAnimation-> showDisLikeAnimation()
            is HomeState.ShowError-> showSnakeMessage(getString(state.error), binding.clRoot, R.color.colorPrimary,R.color.white )
        }
    }

    private fun showLikeAnimation() {

        binding.animLike.show(true)
        binding.animLike.setAnimation("like2.json");

        binding.animLike.playAnimation()
        binding.animLike.addAnimatorListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                binding.animLike.show(false)
            }

            override fun onAnimationCancel(animation: Animator?) {
            }
            override fun onAnimationStart(animation: Animator?) {
            }
        })
    }

    private fun showDisLikeAnimation() {

        binding.animLike.show(true)
        binding.animLike.setAnimation("dislike1.json");

        binding.animLike.playAnimation()
        binding.animLike.addAnimatorListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                binding.animLike.show(false)
            }

            override fun onAnimationCancel(animation: Animator?) {
            }
            override fun onAnimationStart(animation: Animator?) {
            }
        })
    }



    override fun handleEvent(event: HomeEvents) {
        when(event){

            is HomeEvents.GotoLikedOrDislikeHeroes ->{
               val dialog= LikeDislikeDialogFragment.newInstance(event.isLike)
                val ftManager= activity?.supportFragmentManager?.beginTransaction()
                ftManager?.let {  dialog.show(it,LikeDislikeDialogFragment.DIALOG_TAG) }
            }
        }
    }

    override fun setupUI() {

        activity?.let {
            layoutManager = CardStackLayoutManager(it.applicationContext, this).apply {
                setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
                setOverlayInterpolator(LinearInterpolator())
            }
        }

        binding.cvCartStackView.layoutManager = layoutManager
        binding.cvCartStackView.adapter = adapter
        binding.cvCartStackView.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }
        binding.ivLike.setOnClickListener {
            viewModel.onSelectLikeButton()
        }
        binding.ivDislike.setOnClickListener {
            viewModel.onSelectDislikeButton()
        }
    }

    override fun initFragment() {
        viewModel.loadCharacters()
    }

    override fun cleanFragment() {
        _binding = null

    }

    override fun observerData() {
        viewModel.superHeroesLD.observe(this,  { list ->
            adapter?.itemsList= list ?: emptyList()
        })
    }

    override fun setFragmentViewBinding(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewFrag = binding.root
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {
        viewModel.onCardDragging(direction)
    }

    override fun onCardSwiped(direction: Direction?) {
        viewModel.onCardSwiped(direction)
    }

    open fun showSnakeMessage(message:String,  anchor:View, backgroundColor:Int, textColor:Int, actionLabel:String="OK", timeShow:Int=Snackbar.LENGTH_LONG ){
        activity?.let {
            var snake = Snackbar.make(
                anchor,
                message,
                timeShow
            )
            snake.setTextColor(ContextCompat.getColor(it.applicationContext,textColor))
            val layout = snake.getView()
            val textView =
                layout.findViewById<View>(R.id.snackbar_text) as TextView
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18f)
            snake.setActionTextColor(ContextCompat.getColor(it.applicationContext, textColor))
            snake.setBackgroundTint(
                ContextCompat.getColor(
                    it.applicationContext,
                    backgroundColor
                )
            )
            snake.setAction(actionLabel, View.OnClickListener {

            })
            snake.show()
        }
    }


    override fun onCardRewound() {
    }

    override fun showLoadingState() {
        binding.animLoader.show(true)
        binding.animLoader.playAnimation()
    }

    override fun hideLoadingState() {
        binding.animLoader.show(false)
    }

    override fun onCardCanceled() {
        Log.i("marvel", "onCardCanceled")
    }

    override fun onCardAppeared(view: View?, position: Int) {
        Log.i("marvel", "onCardAppeared: position: $position")
        viewModel.setPositionTopCard(position)
    }

    override fun onCardDisappeared(view: View?, position: Int) {
        Log.i("marvel", "onCardDisappeared: position: $position")
        viewModel.onCardDisappeared(position)
    }
}