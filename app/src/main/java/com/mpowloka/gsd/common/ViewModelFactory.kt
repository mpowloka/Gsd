package com.mpowloka.gsd.common

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mpowloka.gsd.data.applicationstate.ApplicationStateRepositoryImpl
import com.mpowloka.gsd.data.user.UsersApi
import com.mpowloka.gsd.data.user.UsersRepositoryImpl
import com.mpowloka.gsd.domain.applicationstate.ApplicationStateRepository
import com.mpowloka.gsd.domain.applicationstate.usecase.GetCurrentUserUseCase
import com.mpowloka.gsd.domain.applicationstate.usecase.GetInitialUserSetUseCase
import com.mpowloka.gsd.domain.applicationstate.usecase.SetCurrentUserUseCase
import com.mpowloka.gsd.domain.user.UsersRepository
import com.mpowloka.gsd.domain.user.usecase.GetAllUsersUseCase
import com.mpowloka.gsd.userdetails.UserDetailsViewModel
import com.mpowloka.gsd.userlist.UserListViewModel

class ViewModelFactory(
    private val usersRepository: UsersRepository,
    private val applicationStateRepository: ApplicationStateRepository,
    private val application: Application
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when (modelClass) {
        UserDetailsViewModel::class.java -> UserDetailsViewModel(
            GetCurrentUserUseCase(applicationStateRepository)
        ) as T

        UserListViewModel::class.java -> UserListViewModel(
            GetAllUsersUseCase(usersRepository),
            SetCurrentUserUseCase(applicationStateRepository),
            GetCurrentUserUseCase(applicationStateRepository),
            GetInitialUserSetUseCase(applicationStateRepository),
            application
        ) as T

        else -> throw IllegalArgumentException("Unsupported ViewModel type")
    }

    companion object {

        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application): ViewModelFactory {
            if (INSTANCE == null) {
                INSTANCE = ViewModelFactory(
                    UsersRepositoryImpl(
                        UsersApi.newInstance()
                    ),
                    ApplicationStateRepositoryImpl(),
                    application
                )
            }
            return INSTANCE!!
        }

    }

}