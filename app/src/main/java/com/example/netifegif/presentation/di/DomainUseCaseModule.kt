package com.example.netifegif.presentation.di

import com.example.netifegif.domain.usecases.GetSearchGifsUseCase
import com.example.netifegif.domain.usecases.GetTrendingGifsUseCase
import org.koin.dsl.module

val domainUseCaseModule = module {

    single {
        GetTrendingGifsUseCase(
            repository = get()
        )
    }

    single {
        GetSearchGifsUseCase(
            repository = get()
        )
    }
}