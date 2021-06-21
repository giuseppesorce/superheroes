package com.giuseppesorce.vodafone.architecture.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.giuseppesorce.vodafone.architecture.CommonState
import com.giuseppesorce.vodafone.architecture.LoadingState
import com.giuseppesorce.vodafone.architecture.SimpleAlertData
import com.google.android.material.dialog.MaterialAlertDialogBuilder


/**
 * @author Giuseppe Sorce
 */
abstract class BaseViewBindingDialogFragment<State : Any, Event : Any>(
) : DialogFragment() {


    var viewFrag: View? = null
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

        val window: Window? = dialog?.window
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        window?.setDimAmount(0f)
        return viewFrag
    }

    abstract fun setFragmentViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        intitializeFragment()
    }



    private fun intitializeFragment() {
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

    private fun showDataAlert(alert: SimpleAlertData?) {
        alert?.let {
            var title = when (alert.titleRes) {
                -1 -> alert.title
                else -> getStr(alert.titleRes)
            }
            var message = when (alert.messageRes) {
                -1 -> alert.message
                else -> {
                    getStr(alert.messageRes)
                }
            }
            showAlert(title, message)
        }
    }

    private fun getStr(res:Int):String{
        var resString =""
        try {
            resString= getString(res)
        }catch (ex:Exception){
        }
        return  resString
    }

    override fun onDestroy() {
        viewFrag = null
        cleanFragment()
        super.onDestroy()
    }

    override fun onDestroyView() {
        viewFrag = null
        cleanFragment()
        super.onDestroyView()
    }

    open fun cleanFragment() {

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

    fun showAlert(title: String, messase: String) {
        activity?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle(title)
                .setMessage(messase)
                .setPositiveButton("Ok", null)
                .show();
        }
    }

    open fun initFragment() {
    }

    open fun observerData() {
    }

    open fun showLoadingState() {
    }

    open fun hideLoadingState() {
    }

}