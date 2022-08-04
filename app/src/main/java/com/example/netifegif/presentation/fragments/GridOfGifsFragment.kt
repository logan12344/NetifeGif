package com.example.netifegif.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.netifegif.R
import com.example.netifegif.databinding.FragmentGridOfGifsBinding
import com.example.netifegif.preference.Prefs
import com.example.netifegif.presentation.GifsViewModel
import com.example.netifegif.presentation.adapters.GifsAdapter
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class GridOfGifsFragment : Fragment() {
    private lateinit var binding: FragmentGridOfGifsBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GifsAdapter
    private val viewModel: GifsViewModel by sharedViewModel()
    private lateinit var shared : Prefs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        shared = Prefs(requireContext())
        binding = FragmentGridOfGifsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        var job: Job? = null
        binding.search.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                editable?.let {
                    if (editable.toString().isNotEmpty()) {
                        viewModel.getSearchGifs(editable.toString())
                    }
                }
            }
        }

        viewModel.searchGifs.observe(viewLifecycleOwner) { list ->
            adapter.differ.submitList(list)
        }

        adapter.setOnItemClickListener {
            val bundle = Bundle()
            bundle.putSerializable("giphy", it)
            findNavController().navigate(R.id.action_gridOfGifsFragment_to_gifFragment, bundle)
        }

        viewModel.getTrendingGifs()
        viewModel.trendingGifs.observe(viewLifecycleOwner) { list ->
            adapter.differ.submitList(list)
        }

        binding.changeView.setOnClickListener {
            setLayoutManager()
        }
    }

    private fun setLayoutManager() {
        if (shared.viewTypeList) {
            shared.viewTypeList = false
            binding.changeView.setImageResource(R.drawable.ic_view_stream_24)
            recyclerView.layoutManager = GridLayoutManager(context, 2)
        } else {
            shared.viewTypeList = true
            binding.changeView.setImageResource(R.drawable.ic_dashboard_24)
            recyclerView.layoutManager = LinearLayoutManager(context)
        }
        adapter.notifyDataSetChanged()
    }

    private fun setupRecyclerView() {
        recyclerView = binding.recycle
        adapter = GifsAdapter()
        if (shared.viewTypeList) {
            binding.changeView.setImageResource(R.drawable.ic_dashboard_24)
            recyclerView.layoutManager = LinearLayoutManager(context)
        } else {
            binding.changeView.setImageResource(R.drawable.ic_view_stream_24)
            recyclerView.layoutManager = GridLayoutManager(context, 2)
        }
        recyclerView.adapter = adapter
    }
}