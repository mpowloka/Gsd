package com.mpowloka.gsd.userdetails

import com.mpowloka.gsd.domain.user.User
import com.mpowloka.gsd.domain.applicationstate.usecase.GetCurrentUserUseCase
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test

class UserDetailsViewModelTest {

    private lateinit var SUT: UserDetailsViewModel

    private lateinit var getCurrentUserUseCaseMock: GetCurrentUserUseCase

    @Before
    fun setup() {
        mockGetCurrentUserUseCase()
        SUT = UserDetailsViewModel(
            getCurrentUserUseCaseMock
        )
    }

    @Test
    fun currentUserDetails_detailsOfCurrentUserReturned() {
        val result = SUT.currentUserDetails

        result.test().assertValue(
            UserDetails(
                USER.login,
                USER.organization,
                USER.avatarUrl
            )
        )
    }

    private fun mockGetCurrentUserUseCase() {
        getCurrentUserUseCaseMock = mock()
        whenever(getCurrentUserUseCaseMock.get()).thenReturn(
            Observable.just(USER)
        )
    }

    companion object {

        private val USER = User(42, "asdf", "movies", "")

    }
}