package com.example.passidapp.di
//
//import com.example.passidapp.network.RetrofitInstance
//import com.example.passidapp.repository.UserRepository
//import com.example.passidapp.viewmodels.UserViewModel
//import org.koin.androidx.viewmodel.dsl.viewModel
//import org.koin.dsl.module
//
//val appModule = module {
//    single { RetrofitInstance.api } // Определяем, как создавать ApiService
//    single { UserRepository(get()) } // Определяем, как создавать UserRepository
//    viewModel { UserViewModel(get()) } // Определяем, как создавать UserViewModel
//}
