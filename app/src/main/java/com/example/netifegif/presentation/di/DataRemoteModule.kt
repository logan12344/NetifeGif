package com.example.netifegif.presentation.di

import com.example.netifegif.BuildConfig
import com.example.netifegif.data.api.GifsApi
import com.example.netifegif.data.GifsMapper
import com.example.netifegif.data.repository.GifsSource
import com.example.netifegif.domain.repository.GifsRepository
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataRemoteModule = module {
    single {
        createRetrofit()
    }

    single { get<Retrofit>().create(GifsApi::class.java) }

    single { GifsMapper() }

    single<GifsRepository> {
        GifsSource(
            gifsApi = get(),
            mapper = get()
        )
    }
}

fun createRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}


