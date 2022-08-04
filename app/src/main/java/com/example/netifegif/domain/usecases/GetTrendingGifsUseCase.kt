package com.example.netifegif.domain.usecases

import com.example.netifegif.domain.models.Giphy
import com.example.netifegif.domain.repository.GifsRepository

class GetTrendingGifsUseCase(private val repository: GifsRepository) {
    suspend fun fetchTrendingGifs(): List<Giphy> =
        repository.fetchTrendingGifs()
}