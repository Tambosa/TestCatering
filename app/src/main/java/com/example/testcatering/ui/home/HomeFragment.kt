package com.example.testcatering.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testcatering.R
import com.example.testcatering.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModel()
    private val mainAdapter =
        MainAdapter(onCategoryItemClick = { position -> onCategoryItemClick(position) })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.data.observe(viewLifecycleOwner) { categories ->
            mainAdapter.items = categories
            mainAdapter.notifyDataSetChanged()
        }

        binding.recyclerCategory.apply {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        }
        viewModel.getCategories()
    }

    private fun onCategoryItemClick(position: Int) {
        val bundle = Bundle()
        bundle.putString("category", viewModel.data.value?.get(position)?.name)
        findNavController().navigate(
            R.id.navigation_category,
            bundle
        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}