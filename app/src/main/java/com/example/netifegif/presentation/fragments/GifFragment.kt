package com.example.netifegif.presentation.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.netifegif.R
import com.example.netifegif.databinding.FragmentGifBinding


class GifFragment : Fragment() {

    private lateinit var binding: FragmentGifBinding
    private val args: GifFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentGifBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val gif = args.giphy
        when (resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_NO -> binding.btnBack.setImageResource(R.drawable.ic_baseline_arrow_back_24)
            Configuration.UI_MODE_NIGHT_YES -> binding.btnBack.setImageResource(R.drawable.ic_baseline_arrow_back_24_white)
        }
        Glide.with(this)
            .asGif()
            .load(gif.url_large)
            .placeholder(R.drawable.black_cursor)
            .into(binding.imageViewGif)

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_gifFragment_to_gridOfGifsFragment)
        }
    }
}