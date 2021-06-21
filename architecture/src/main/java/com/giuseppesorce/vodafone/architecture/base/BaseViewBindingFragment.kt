package com.giuseppesorce.vodafone.architecture.base

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.giuseppesorce.vodafone.architecture.CommonState
import com.giuseppesorce.vodafone.architecture.LoadingState
import com.giuseppesorce.vodafone.architecture.SimpleAlertData
import com.google.android.material.dialog.MaterialAlertDialogBuilder


/**
 * @author Giuseppe Sorce
 */

abstract class BaseViewBindingFragment<State : Any, Event : Any>() : Fragment() {

    var alerMaterial: AlertDialog?=null

    open var viewFrag: View? = null

    private var fragmentInitialized = false
    abstract fun provideBaseViewModel(): BaseViewModel<State, Event>?
    abstract fun setupUI()
    abstract fun handleState(state: State)
    abstract fun handleEvent(event: Event)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (viewFrag == null) {
            setFragmentViewBinding(inflater, container)
        }
        return viewFrag
    }

    abstract fun setFragmentViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        intitializeFragment()
    }

     override fun onSaveInstanceState(oldInstanceState: Bundle) {
        super.onSaveInstanceState(oldInstanceState)
        oldInstanceState.clear()
    }

    fun intitializeFragment() {
        setupUI()
        provideBaseViewModel()?.stateHolder()
            ?.observe(viewLifecycleOwner, Observer { state -> handleState(state) })
        provideBaseViewModel()?.commonStateHolder()
            ?.observe(viewLifecycleOwner, Observer { state -> handleCommonState(state) })
        provideBaseViewModel()?.eventHolder()
            ?.observe(viewLifecycleOwner, Observer { event -> handleEvent(event) })
        provideBaseViewModel()?.alertLiveData()
            ?.observe(viewLifecycleOwner, Observer { alert -> showDataAlert(alert) })
       if (!fragmentInitialized) {
            fragmentInitialized = true
            observerData()
            initFragment()
        }
    }

    override fun onDestroy() {
        viewFrag = null
        super.onDestroy()
    }

    override fun onDestroyView() {
        viewFrag = null
        cleanFragment()
        super.onDestroyView()
    }

    open fun cleanFragment() {
    }

    private fun showDataAlert(alert: SimpleAlertData?) {
        alert?.let {
            var title = when (alert.titleRes) {
                -1 -> alert.title
                else -> getString(alert.titleRes)
            }
            var message = when (alert.messageRes) {
                -1 -> alert.message
                else -> {
                    getString(alert.messageRes)
                }
            }
            showAlert(title, message, alert.codeMessage)
        }
    }

    private fun handleCommonState(commonState: CommonState) {
        handleLoading(commonState.loadingState)
    }

    open fun showIdleState() {
        hideLoadingState()
    }

    open fun handleLoading(loadingState: LoadingState) {
        when (loadingState) {
            is LoadingState.Loading -> showLoadingState()
            is LoadingState.Idle -> showIdleState()
        }
    }

    open fun showAlert(title: String, messase: String, codeMessage: Int) {
        activity?.let {
           alerMaterial=  MaterialAlertDialogBuilder(it)
                .setTitle(title)
                .setMessage(messase)
                .setPositiveButton("Ok", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        onClickPositive(codeMessage)
                    }
                })
                .show()
        }
    }
    open fun onClickPositive(codeMessage: Int) {
    }

    open fun initFragment() {
    }

    open fun observerData() {
    }

    open fun showLoadingState() {}

    open fun hideLoadingState() {
    }
}