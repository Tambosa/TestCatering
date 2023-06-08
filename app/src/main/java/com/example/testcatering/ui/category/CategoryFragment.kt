package com.example.testcatering.ui.category

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import com.example.testcatering.R
import com.example.testcatering.databinding.DialogDishDetailsBinding
import com.example.testcatering.databinding.FragmentCategoryBinding
import com.example.testcatering.ui.common.ItemOffsetDecoration
import com.google.android.material.chip.Chip
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CategoryViewModel by viewModel()

    private val dishAdapter =
        DishAdapter(onDishItemClick = { position -> onDishItemClick(position) })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString("category")?.let {
            binding.header.categoryName.text = it
        }

        initViewmodel()
        initRecycler()
        initTagChips()
        binding.header.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        viewModel.getDishes()
    }

    private fun initRecycler() {
        binding.recyclerDish.apply {
            adapter = dishAdapter
            layoutManager =
                GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
            addItemDecoration(ItemOffsetDecoration(requireContext(), R.dimen.margin_small))
        }
    }

    private fun initViewmodel() {
        viewModel.data.observe(viewLifecycleOwner) { dish ->
            dishAdapter.items = dish
            dishAdapter.notifyDataSetChanged()
        }
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            with(binding) {
                progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
                recyclerDish.visibility = if (isLoading) View.GONE else View.VISIBLE
            }
        }
    }

    private fun initTagChips() {
        binding.chipGroup.setOnCheckedStateChangeListener { _, checkedId ->
            viewModel.selectTag(
                binding.root.findViewById<Chip>(checkedId[0]).text.toString()
            )
        }
    }

    private fun onDishItemClick(position: Int) {
        viewModel.data.value?.get(position)?.let { dish ->
            val dialogBinding: DialogDishDetailsBinding =
                DialogDishDetailsBinding.inflate(layoutInflater)
            val dialog =
                AlertDialog.Builder(requireContext()).setView(dialogBinding.root).show().apply {
                    window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                }
            with(dialogBinding) {
                dishImage.load(dish.imageUrl)
                dishName.text = dish.name
                dishPrice.text = "${dish.price} ₽"
                dishWeight.text = " · ${dish.weight} г"
                dishPreview.text = dish.description
                dishPreview.movementMethod = ScrollingMovementMethod()
                btnDismiss.setOnClickListener {
                    dialog.dismiss()
                }
                btnAddToCart.setOnClickListener {
                    viewModel.addToCart(dish)
                    Toast.makeText(requireContext(), "${dish.name} в корзине", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}