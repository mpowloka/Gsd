package com.mpowloka.gsd.domain.user.usecase

import com.mpowloka.gsd.domain.user.UsersFetcher
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test

class FetchUsersUseCaseTest {

    private lateinit var SUT: FetchUsersUseCase
    private lateinit var usersFetcherMock: UsersFetcher

    @Before
    fun setup() {
        usersFetcherMock = mock()
        SUT = FetchUsersUseCase(
            usersFetcherMock
        )
    }

    @Test
    fun fetch_apiClientTriggered() {
        SUT.fetch()

        verify(usersFetcherMock, times(1)).fetchUsers()
    }
}