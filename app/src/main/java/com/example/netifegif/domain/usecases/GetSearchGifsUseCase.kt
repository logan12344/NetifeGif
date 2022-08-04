package com.example.netifegif.domain.usecases

import com.example.netifegif.domain.models.Giphy
import com.example.netifegif.domain.repository.GifsRepository

class GetSearchGifsUseCase(private val repository: GifsRepository) {
    suspend fun fetchSearchGifs( searchQuery: String): List<Giphy> =
        repository.fetchSearchGifs(searchQuery)
}