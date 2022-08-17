package com.ken.morse

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ken.morse.databinding.ActivityMainBinding
import com.ken.morse.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private var mainViewModel: MainViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layoutBinding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        mainViewModel = MainViewModel(this, layoutBinding)
        layoutBinding.viewModel = mainViewModel
    }

    override fun onResume() {
        super.onResume()

        // Invalidate since some permissions may have changed outside of the app.
        mainViewModel!!.notifyChange()
    }
}