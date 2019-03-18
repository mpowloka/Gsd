package com.mpowloka.gsd.domain.applicationstate.usecase

import com.mpowloka.gsd.domain.applicationstate.ApplicationStateRepository
import com.mpowloka.gsd.domain.user.User
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test

class SetCurrentUserUseCaseTest {

    private lateinit var SUT: SetCurrentUserUseCase
    private lateinit var applicationStateRepositoryMock: ApplicationStateRepository

    @Before
    fun setup() {
        applicationStateRepositoryMock = mock()
        SUT = SetCurrentUserUseCase(applicationStateRepositoryMock)
    }

    @Test
    fun set_userPassedToRepository() {
        SUT.set(USER)

        verify(applicationStateRepositoryMock, times(1)).setCurrentUser(USER)
    }

    companion object {

        private val USER = User(0, "", "", "")

    }
}