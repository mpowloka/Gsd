package com.mpowloka.gsd.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mpowloka.gsd.domain.user.User
import com.mpowloka.gsd.domain.user.UsersRepository
import com.mpowloka.gsd.domain.user.usecase.GetCurrentUserUseCase
import com.mpowloka.gsd.userdetails.UserDetailsViewModel
import io.reactivex.Observable

class ViewModelFactory(
    private val usersRepository: UsersRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when (modelClass) {
        UserDetailsViewModel::class.java -> UserDetailsViewModel(
            GetCurrentUserUseCase(usersRepository)
        ) as T

        else -> throw IllegalArgumentException("Unsupported ViewModel type")
    }

    companion object {

        private var FAKE_INSTANCE: ViewModelFactory? = null

        fun getInstanceWithMockedRepository(): ViewModelFactory {
            if(FAKE_INSTANCE == null) {
                FAKE_INSTANCE = ViewModelFactory(object : UsersRepository {

                    lateinit var user: User

                    override fun getAllUsers() = Observable.just(listOf(
                        User(0, "szumi", "LoL", "http://cdn.journaldev.com/wp-content/uploads/2016/11/android-image-picker-project-structure.png")
                    ))

                    override fun getCurrentUser() = Observable.just(user)

                    override fun setCurrentUser(user: User) {
                        this.user = user
                    }

                })
            }

            return FAKE_INSTANCE!!
        }

    }

}