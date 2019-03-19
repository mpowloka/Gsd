package com.mpowloka.gsd.data.user

import com.mpowloka.gsd.domain.user.User
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UsersRepositoryImplTest {

    private lateinit var SUT: UsersRepositoryImpl

    private lateinit var usersCacheMock: UsersCache

    @Before
    fun setup() {
        mockUsersCache()
        SUT = UsersRepositoryImpl(usersCacheMock)
    }

    @Test
    fun getAllUsers_cache() {
        val result = SUT.getAllUsers()

        assertEquals(USERS_CACHE_OBSERVABLE, result)
    }

    @Test
    fun addUser_userPassedToCache() {
        SUT.addUser(USER)

        verify(usersCacheMock, times(1)).addUser(USER)
    }

    @Test
    fun addUsers_usersPassedToCache() {
        SUT.addUsers(USERS)

        verify(usersCacheMock, times(1)).addUsers(USERS)
    }

    private fun mockUsersCache() {
        usersCacheMock = mock()

        whenever(usersCacheMock.getUsers()).thenReturn(
            USERS_CACHE_OBSERVABLE
        )
    }

    companion object {


        private val USER = User(22, "tzazki", "GitHub", "")

        private val USERS = listOf(
            User(1, "tzazki", "GitHub", ""),
            User(2, "tzazki", "GitHub", "")
        )

        private val USERS_CACHE_OBSERVABLE = Observable.just(
            listOf(
                User(42, "octocat", "GitHub", ""),
                User(43, "octocat2", "GitHub", "")
            )
        )

    }

}