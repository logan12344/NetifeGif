package com.example.netifegif.domain.repository

import com.example.netifegif.domain.models.Giphy

interface GifsRepository {
    suspend fun fetchTrendingGifs(): List<Giphy>
    suspend fun fetchSearchGifs(searchQuery: String): List<Giphy>
}