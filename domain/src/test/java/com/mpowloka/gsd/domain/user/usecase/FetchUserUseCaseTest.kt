package com.mpowloka.gsd.domain.user.usecase

import com.mpowloka.gsd.domain.user.UsersRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test

class FetchUserUseCaseTest {

    private lateinit var SUT: FetchUserUseCase

    private lateinit var usersRepositoryMock: UsersRepository

    @Before
    fun setup() {
        usersRepositoryMock = mock()
        SUT = FetchUserUseCase(
            usersRepositoryMock
        )
    }

    @Test
    fun fetch_usernamePassedToRepository() {
        SUT.fetch(USERNAME)

        verify(usersRepositoryMock, times(1)).fetchUser(USERNAME)
    }

    companion object {
        private const val USERNAME = "USERNAME"
    }

}