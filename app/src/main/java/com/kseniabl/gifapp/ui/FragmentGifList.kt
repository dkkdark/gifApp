package com.kseniabl.gifapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.filter
import com.google.android.flexbox.*
import com.kseniabl.gifapp.databinding.FragmentGifListBinding
import com.kseniabl.gifapp.network.models.GifModel
import com.kseniabl.gifapp.ui.adapter.GifAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch

class FragmentGifList: Fragment() {

    private var _binding: FragmentGifListBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<MainViewModel>()
    private val adapter = GifAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentGifListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = FlexboxLayoutManager(context).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            alignItems = AlignItems.STRETCH
        }

        binding.apply {
            gifRecyclerView.layoutManager = layoutManager
            gifRecyclerView.adapter = adapter

            /**
             * Observe change search query
             * **/
            searchText.addTextChangedListener {
                viewModel.onSearchQueryChange(searchText.text.toString().lowercase())
                if (searchText.text.toString().isEmpty())
                    hiddenText.visibility = View.VISIBLE
                else
                    hiddenText.visibility = View.GONE
            }
        }

        /**
         * Listener implementation in viewModel
         * **/
        adapter.setOnClickListener(viewModel)

        lifecycleScope.launch {
            viewModel.gifList.collectLatest {
                adapter.submitData(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    // Observe click on item
                    viewModel.actionTrigger.collect {
                        when (it) {
                            is MainViewModel.UIActions.MoveToDetails -> moveToDetails(it.item)
                        }
                    }
                }
            }
        }

    }

    private fun moveToDetails(item: GifModel) {
        findNavController()
            .navigate(FragmentGifListDirections
                .actionFragmentGifListToFragmentGifDetails(item))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}