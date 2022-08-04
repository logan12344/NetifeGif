package com.example.netifegif.data

import com.example.netifegif.data.models.GifModel
import com.example.netifegif.domain.models.Giphy

class GifsMapper : Mapper<GifModel, List<Giphy>> {
    override fun invoke(input: GifModel): List<Giphy> {
        val giphyList = mutableListOf<Giphy>()
        input.data.forEach {
            giphyList.add(Giphy(url_large = it.images.downsized_large.url))
        }
        return giphyList
    }
}