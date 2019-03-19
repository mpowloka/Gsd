package com.mpowloka.gsd.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mpowloka.gsd.MainViewModel
import com.mpowloka.gsd.userdetails.UserDetailsViewModel
import com.mpowloka.gsd.userlist.UserListViewModel

class ViewModelFactory(
    private val application: GsdApplication
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when (modelClass) {

        MainViewModel::class.java -> MainViewModel() as T

        UserDetailsViewModel::class.java -> UserDetailsViewModel(
            application.getCurrentUserUseCase
        ) as T

        UserListViewModel::class.java -> UserListViewModel(
            application.getAllUsersUseCase,
            application.setCurrentUserUseCase,
            application.getCurrentUserUseCase,
            application.getInitialUserSetUseCase,
            application.fetchUserUseCase,
            application
        ) as T

        else -> throw IllegalArgumentException("Unsupported ViewModel type")
    }

}