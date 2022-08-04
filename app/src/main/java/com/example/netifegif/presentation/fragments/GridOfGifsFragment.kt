package com.example.netifegif.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.netifegif.R
import com.example.netifegif.databinding.FragmentGridOfGifsBinding
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
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

        viewModel.getTrendingGifs()
        viewModel.trendingGifs.observe(viewLifecycleOwner) { list ->
            adapter.list.submitList(list)
        }

        viewModel.searchGifs.observe(viewLifecycleOwner) { list ->
            adapter.list.submitList(list)
        }

        adapter.setOnItemClickListener {
            val bundle = Bundle()
            bundle.putSerializable("giphy", it)
            findNavController().navigate(R.id.action_gridOfGifsFragment_to_gifFragment, bundle)
        }

        binding.changeView.setOnClickListener {
            setLayoutManager()
        }
    }

    private fun setLayoutManager() {
        val isSwitched: Boolean = adapter.toggleItemViewType()
        recyclerView.layoutManager = if (isSwitched) LinearLayoutManager(context) else GridLayoutManager(context, 2)
        binding.changeView.setImageResource(
            if (isSwitched) R.drawable.ic_dashboard_24
            else R.drawable.ic_view_stream_24
        )
        adapter.notifyDataSetChanged()
    }

    private fun setupRecyclerView() {
        recyclerView = binding.recycle
        adapter = GifsAdapter()
        setLayoutManager()
        recyclerView.adapter = adapter
    }
}