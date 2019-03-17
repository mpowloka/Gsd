package com.mpowloka.gsd.domain.user.usecase

import com.mpowloka.gsd.domain.user.User
import com.mpowloka.gsd.domain.user.UsersRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify

import org.junit.Before
import org.junit.Test

class SetCurrentUserTest {

    private lateinit var SUT: SetCurrentUser
    private lateinit var userRepositoryMock: UsersRepository

    @Before
    fun setup() {
        userRepositoryMock = mock()
        SUT = SetCurrentUser(userRepositoryMock)
    }

    @Test
    fun set_userPassedToRepository() {
        SUT.set(USER)

        verify(userRepositoryMock, times(1)).setCurrentUser(USER)
    }

    companion object {

        private val USER = User(0, "", "", "")

    }
}