package com.example.testcatering.ui.cart

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testcatering.databinding.FragmentCartBinding
import com.example.testcatering.ui.common.getAddress
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
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

    private lateinit var fusedLocationClient: FusedLocationProviderClient

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

    private fun initLocation(context: Context) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            Geocoder(context, Locale.getDefault()).getAddress(
                location.latitude,
                location.longitude
            ) { address ->
                requireActivity().runOnUiThread {
                    binding.header.headerCityName.text = address?.subAdminArea
                }
            }
        }
    }

    private fun initHeader() {
        binding.header.headerDate.text = SimpleDateFormat(
            "dd-MMMM-yyyy",
            Locale.getDefault()
        ).format(Calendar.getInstance().time)
        if (binding.header.headerCityName.text.isNullOrBlank()) {
            initLocation(requireContext())
        }
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