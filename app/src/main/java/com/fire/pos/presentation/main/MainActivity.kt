package com.fire.pos.presentation.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.fire.pos.R
import com.fire.pos.base.activity.BaseActivity
import com.fire.pos.databinding.ActivityMainBinding
import com.fire.pos.di.appComponent
import com.fire.pos.presentation.main.di.DaggerMainComponent
import com.fire.pos.presentation.main.viewmodel.MainViewModel
import com.fire.pos.presentation.main.viewmodel.MainViewModelContract
import javax.inject.Inject

class MainActivity : BaseActivity<MainViewModel, MainViewModelContract, ActivityMainBinding>(),
    MainView {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getViewModelClass(): Class<MainViewModel> {
        return MainViewModel::class.java
    }

    override fun getViewModelFactory(): ViewModelProvider.Factory {
        return viewModelProviderFactory
    }

    override fun subscribeLiveData() {

    }

    override fun setupDependencyInjection() {
        DaggerMainComponent.builder()
            .appComponent(appComponent())
            .build()
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

}