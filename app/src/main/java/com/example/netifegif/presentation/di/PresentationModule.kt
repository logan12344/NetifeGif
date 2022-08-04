package com.example.netifegif.presentation.di

import com.example.netifegif.presentation.GifsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel {
        GifsViewModel(trendingGifsUseCase = get(), searchGifsUseCase = get())
    }
}