package com.example.testcatering.ui.home

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.model.Category
import com.example.testcatering.R
import com.example.testcatering.databinding.FragmentHomeBinding
import com.example.testcatering.ui.common.getAddress
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModel()
    private val homeAdapter =
        MainAdapter(onCategoryItemClick = { position -> onCategoryItemClick(position) })

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                initLocation(requireContext())
            }

            else -> {
            }
        }
    }

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
        initViewmodel()
        initRecycler()
        initHeader()
        viewModel.getCategories()
    }

    private fun initLocation(context: Context) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionRequest.launch(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION))
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
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
        binding.recyclerCategory.apply {
            adapter = homeAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        }
    }

    private fun initViewmodel() {
        viewModel.data.observe(viewLifecycleOwner) { newList ->
            renderRecyclerData(newList)
        }
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            with(binding) {
                if (isLoading) {
                    shimmerContainer.startShimmer()
                    shimmerContainer.visibility = View.VISIBLE
                } else {
                    shimmerContainer.stopShimmer()
                    shimmerContainer.visibility = View.GONE
                }
            }
        }
    }

    private fun renderRecyclerData(newList: List<Category>) {
        val oldList = homeAdapter.items ?: listOf()
        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize() = oldList.size

            override fun getNewListSize() = newList.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                oldList[oldItemPosition]::class == newList[newItemPosition]::class &&
                        oldList[oldItemPosition] == newList[newItemPosition]

            override fun areContentsTheSame(
                oldItemPosition: Int,
                newItemPosition: Int
            ) = oldList[oldItemPosition] == newList[newItemPosition]
        })
        homeAdapter.items = newList
        diff.dispatchUpdatesTo(homeAdapter)
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