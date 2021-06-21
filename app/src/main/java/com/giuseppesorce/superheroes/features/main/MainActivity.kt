package com.giuseppesorce.superheroes.features.main

import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.giuseppesorce.superheroes.databinding.ActivityMainBinding
import com.giuseppesorce.superheroes.models.navigationevents.MainEvents
import com.giuseppesorce.superheroes.models.navigationevents.MainState
import com.giuseppesorce.vodafone.architecture.base.BaseActivityViewBinding
import com.giuseppesorce.vodafone.architecture.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.security.MessageDigest
import java.util.*

@AndroidEntryPoint
class MainActivity : BaseActivityViewBinding<MainState, MainEvents>() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()

    override fun getDataBindingiView(): View {
        _binding = ActivityMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun provideBaseViewModel(): BaseViewModel<MainState, MainEvents>? = viewModel

    override fun handleState(state: MainState) {
    }

    override fun handleEvent(event: MainEvents) {
    }

    override fun setupUI() {

        var word = "ciao"
        Log.i("marvel", "word "+word.toMD5())
        Log.i("marvel", "word 6e6bc4e49dd477ebc98ef4046c067b5f")
    }



}

fun String.toMD5(): String {
    val bytes = MessageDigest.getInstance("MD5").digest(this.toByteArray())
    return bytes.toHex()
}

fun ByteArray.toHex(): String {
    return joinToString("") { "%02x".format(it) }
}
