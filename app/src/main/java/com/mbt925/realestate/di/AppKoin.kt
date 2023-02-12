package com.mbt925.realestate.di

import com.mbt925.realestate.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel {
        MainViewModel(
            model = get(),
        )
    }

}
