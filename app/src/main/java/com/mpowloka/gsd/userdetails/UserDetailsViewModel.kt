package com.mpowloka.gsd.userdetails

import androidx.lifecycle.ViewModel
import com.mpowloka.gsd.domain.applicationstate.usecase.GetCurrentUserUseCase
import io.reactivex.Observable


class UserDetailsViewModel(
    getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    val currentUserDetails: Observable<UserDetails> by lazy {
        getCurrentUserUseCase.get().map { UserDetails(it) }
    }

}
