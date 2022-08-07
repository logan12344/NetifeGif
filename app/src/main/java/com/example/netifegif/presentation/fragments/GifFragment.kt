package com.example.netifegif.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.netifegif.R
import com.example.netifegif.databinding.FragmentGifBinding
import com.example.netifegif.domain.models.Giphy
import com.example.netifegif.presentation.adapters.SlideAdapter


class GifFragment : Fragment() {

    private lateinit var binding: FragmentGifBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentGifBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val gifsArray = requireArguments().getSerializable("arrayGifs") as Array<Giphy>
        val index = requireArguments().getSerializable("index") as Int
        binding.backButton.setImageResource(R.drawable.ic_baseline_arrow_back_24_white)
        binding.pager.adapter = SlideAdapter(gifsArray, index + 1)

        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_gifFragment_to_gridOfGifsFragment)
        }
    }
}