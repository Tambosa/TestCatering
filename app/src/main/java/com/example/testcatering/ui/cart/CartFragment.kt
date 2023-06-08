package com.example.testcatering.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testcatering.databinding.FragmentCartBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CartViewModel by viewModel()
    private val mainAdapter =
        CartAdapter(
            onMinus = { position -> onMinus(position) },
            onPlus = { position -> onPlus(position) },
        )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewmodel()
        initHeader()
        initRecycler()
        viewModel.getCart()
    }

    private fun initHeader() {
        binding.header.headerDate.text = SimpleDateFormat(
            "dd-MMMM-yyyy",
            Locale.getDefault()
        ).format(Calendar.getInstance().time)
    }

    private fun initRecycler() {
        binding.recyclerCart.apply {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        }
    }

    private fun initViewmodel() {
        viewModel.cart.observe(viewLifecycleOwner) { items ->
            mainAdapter.items = items
            mainAdapter.notifyDataSetChanged()
            if (items.isEmpty()) {
                binding.btnPay.text = "Пусто :("
            } else {
                var sum = 0
                items.forEach {
                    sum += it.first.price * it.second
                }
                binding.btnPay.text = "Оплатить $sum ₽"
            }
        }
    }

    private fun onPlus(position: Int) {
        viewModel.cart.value?.get(position)?.first?.let { viewModel.addItem(it) }
    }

    private fun onMinus(position: Int) {
        viewModel.cart.value?.get(position)?.first?.let { viewModel.removeItem(it) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}