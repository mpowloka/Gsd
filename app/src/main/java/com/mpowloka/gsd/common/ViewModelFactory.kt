package com.mpowloka.gsd.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mpowloka.gsd.data.user.UsersApi
import com.mpowloka.gsd.data.user.UsersRepositoryImpl
import com.mpowloka.gsd.domain.user.User
import com.mpowloka.gsd.domain.user.UsersRepository
import com.mpowloka.gsd.domain.user.usecase.GetAllUsersUseCase
import com.mpowloka.gsd.domain.user.usecase.GetCurrentUserUseCase
import com.mpowloka.gsd.domain.user.usecase.SetCurrentUserUseCase
import com.mpowloka.gsd.userdetails.UserDetailsViewModel
import com.mpowloka.gsd.userlist.UserListViewModel
import io.reactivex.Observable

class ViewModelFactory(
    private val usersRepository: UsersRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when (modelClass) {
        UserDetailsViewModel::class.java -> UserDetailsViewModel(
            GetCurrentUserUseCase(usersRepository)
        ) as T

        UserListViewModel::class.java -> UserListViewModel(
            GetAllUsersUseCase(usersRepository),
            SetCurrentUserUseCase(usersRepository)
        ) as T

        else -> throw IllegalArgumentException("Unsupported ViewModel type")
    }

    companion object {

        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(): ViewModelFactory {
            if (INSTANCE == null) {
                INSTANCE = ViewModelFactory(
                    UsersRepositoryImpl(
                        UsersApi.newInstance()
                    )
                )
            }
            return INSTANCE!!
        }

    }

}