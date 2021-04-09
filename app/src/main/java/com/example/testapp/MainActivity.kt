package com.example.testapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.testapp.databinding.ActivityMainBinding
import com.example.testapp.ui.adaptors.CovidListAdaptor
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    private val adaptor: CovidListAdaptor = CovidListAdaptor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        binding.adaptor = adaptor
        binding.viewModel = viewModel

        loadData()
    }

    private fun loadData() {
        lifecycleScope.launchWhenCreated {
            launch {
                viewModel.covidState.collect { covidState ->
                    when (covidState) {
                        is CovidState.Success -> adaptor.load(covidState.info)
                        is CovidState.Error -> Log.d("ASD", covidState.exception.toString())
                    }
                }
            }
            launch {
                viewModel.searchText.collect { newSearchText ->
                    adaptor.filter { item ->
                        item.name.toLowerCase(Locale.getDefault()).contains(
                            newSearchText.toLowerCase(
                                Locale.getDefault()
                            )
                        )
                    }
                }
            }
        }
        viewModel.load()
    }
}
