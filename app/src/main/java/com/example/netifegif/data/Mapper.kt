package com.example.netifegif.data

interface Mapper<in I, out O> {
    operator fun invoke(input: I): O
}