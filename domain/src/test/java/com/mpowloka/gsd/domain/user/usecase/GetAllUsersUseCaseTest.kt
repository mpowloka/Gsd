package com.mpowloka.gsd.domain.user.usecase

import com.mpowloka.gsd.domain.user.User
import com.mpowloka.gsd.domain.user.UsersRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
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

        result.test().assertValue(ALL_USERS)
    }

    private fun mockUsersRepository() {
        usersRepositoryMock = mock()
        whenever(usersRepositoryMock.getAllUsers()).thenReturn(Observable.just(ALL_USERS))
    }

    companion object {
        private val ALL_USERS = listOf(
            User(0, "", "", "")
        )
    }
}