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
import com.example.netifegif.domain.models.Giphy
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

        viewModel.getTrendingGifs()
        viewModel.trendingGifs.observe(viewLifecycleOwner) { list ->
            adapter.list.submitList(list)
        }

        viewModel.searchGifs.observe(viewLifecycleOwner) { list ->
            adapter.list.submitList(list)
        }

        adapter.setOnItemClickListener {
            val bundle = Bundle()
            val index: Int = adapter.list.currentList.indexOf(it) - 1
            val test: Array<Giphy> = adapter.list.currentList.toTypedArray()
            bundle.putSerializable("arrayGifs", test)
            bundle.putInt("index", index)
            findNavController().navigate(R.id.action_gridOfGifsFragment_to_gifFragment, bundle)
        }

        binding.changeView.setOnClickListener {
            shared.viewTypeList = !shared.viewTypeList
            setLayoutManager()
        }
    }

    private fun setLayoutManager() {
        adapter.setItemViewType(shared.viewTypeList)
        if (shared.viewTypeList) {
            recyclerView.layoutManager = LinearLayoutManager(context)
            binding.changeView.setImageResource(R.drawable.ic_dashboard_24)
        } else {
            recyclerView.layoutManager = GridLayoutManager(context, 2)
            binding.changeView.setImageResource(R.drawable.ic_view_stream_24)
        }
    }

    private fun setupRecyclerView() {
        recyclerView = binding.recycle
        adapter = GifsAdapter()
        recyclerView.adapter = adapter
        setLayoutManager()
    }
}