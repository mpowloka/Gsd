package com.mpowloka.gsd.domain.user.usecase

import com.mpowloka.gsd.domain.user.User
import com.mpowloka.gsd.domain.user.UsersRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetCurrentUserUseCaseTest {

    private lateinit var SUT: GetCurrentUserUseCase
    private lateinit var usersRepositoryMock: UsersRepository

    @Before
    fun setup() {
        mockUsersRepository()
        SUT = GetCurrentUserUseCase(usersRepositoryMock)
    }

    @Test
    fun get_repositoryQueried() {
        val result = SUT.get()
        assertEquals(result, GET_CURRENT_USERS_RESULT)
    }

    private fun mockUsersRepository() {
        usersRepositoryMock = mock()
        whenever(usersRepositoryMock.getCurrentUser()).thenReturn(GET_CURRENT_USERS_RESULT)
    }

    companion object {
        private val GET_CURRENT_USERS_RESULT = Observable.just(
            User(0, "", "", "")
        )
    }


}