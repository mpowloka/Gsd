package com.mpowloka.gsd.data.user

import com.mpowloka.gsd.domain.user.User
import com.mpowloka.gsd.domain.user.UsersRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.timeout
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test

class UsersFetcherImplTest {

    private lateinit var SUT: UsersFetcherImpl
    private lateinit var usersApiMock: UsersApi
    private lateinit var usersRepositoryMock: UsersRepository

    @Before
    fun setup() {
        initializeMocks()
        SUT = UsersFetcherImpl(
            usersRepositoryMock,
            usersApiMock
        )
    }

    @Test
    fun fetchUsers_usersAddedToCache() {
        SUT.fetchUsers()

        verify(usersRepositoryMock, timeout(1000)).addUsers(USERS_FROM_ENDPOINT)
    }

    @Test
    fun fetchUser_userAddedToCache() {
        SUT.fetchUser(USERNAME)

        verify(usersRepositoryMock, timeout(1000)).addUser(USER_FROM_ENDPOINT)
    }

    private fun initializeMocks() {
        usersApiMock = mock()
        usersRepositoryMock = mock()

        whenever(usersApiMock.getAllUsers()).thenReturn(
            Observable.just(MODELS_FROM_ENDPOINT)
        )

        whenever(usersApiMock.getUser(USERNAME)).thenReturn(
            Observable.just(MODEL_FROM_ENDPOINT)
        )
    }

    companion object {

        private const val USERNAME = "USERNAME"

        private val MODEL_FROM_ENDPOINT = UserModel(
            42, USERNAME, "", ""
        )

        private val USER_FROM_ENDPOINT = User(
            42, USERNAME, "", ""
        )

        private val MODELS_FROM_ENDPOINT = listOf(
            UserModel(0, "0", "0", "0"),
            UserModel(1, "1", "1", "1")
        )

        private val USERS_FROM_ENDPOINT = listOf(
            User(0, "0", "0", "0"),
            User(1, "1", "1", "1")
        )
    }

}