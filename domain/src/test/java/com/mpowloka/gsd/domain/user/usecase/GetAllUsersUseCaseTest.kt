package com.mpowloka.gsd.domain.user.usecase

import com.mpowloka.gsd.domain.user.User
import com.mpowloka.gsd.domain.user.UsersRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetAllUsersUseCaseTest {

    private lateinit var SUT: GetAllUsersUseCase
    private lateinit var usersRepositoryMock: UsersRepository

    @Before
    fun setup() {
        mockUsersRepository()
        SUT = GetAllUsersUseCase(usersRepositoryMock)
    }

    @Test
    fun get_repositoryQueried() {
        val result = SUT.get()
        assertEquals(result, GET_ALL_USERS_RESULT)
    }

    private fun mockUsersRepository() {
        usersRepositoryMock = mock()
        whenever(usersRepositoryMock.getAllUsers()).thenReturn(GET_ALL_USERS_RESULT)
    }

    companion object {
        private val GET_ALL_USERS_RESULT = Observable.just(
            listOf(
                User(0, "", "", "")
            )
        )
    }
}