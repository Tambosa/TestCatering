package com.example.testcatering.di

import com.example.data.CartRepositoryFakeImpl
import com.example.data.CateringApi
import com.example.data.CateringRepositoryImpl
import com.example.data.RetrofitConstants
import com.example.domain.CartRepository
import com.example.domain.CateringRepository
import com.example.testcatering.ui.cart.CartViewModel
import com.example.testcatering.ui.category.CategoryViewModel
import com.example.testcatering.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object KoinModules {

    val repository = module {
        single<CateringApi> {
            Retrofit.Builder()
                .baseUrl(RetrofitConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CateringApi::class.java)
        }
        single<CateringRepository> { CateringRepositoryImpl(get<CateringApi>()) }
        single<CartRepository> { CartRepositoryFakeImpl() }
    }

    val viewModel = module {
        viewModel<HomeViewModel> { HomeViewModel(get<CateringRepository>()) }
        viewModel<CategoryViewModel> {
            CategoryViewModel(
                get<CateringRepository>(),
                get<CartRepository>()
            )
        }
        viewModel<CartViewModel> { CartViewModel(get<CartRepository>()) }
    }
}