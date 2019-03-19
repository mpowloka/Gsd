package com.mpowloka.gsd.data.user

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit

class UsersRepositoryImplTest {

    private lateinit var SUT: UsersRepositoryImpl

    private lateinit var usersApiMock: UsersApi

    @Before
    fun setup() {
        mockUsersApi()
        SUT = UsersRepositoryImpl(usersApiMock)
    }

    @Test
    fun getAllUsers_apiQueried() {
        val result = SUT.getAllUsers()

        Thread.sleep(100L)

        result.test().awaitDone(1, TimeUnit.SECONDS).assertValue {
            it == USERS_FROM_ENDPOINT_MAPPED
        }
    }

    @Test
    fun fetchUser_userAddedToCache() {

        val result = SUT.getAllUsers()

        SUT.fetchUser(USERNAME)

        Thread.sleep(100L)

        result.test().assertValue {
            it.contains(USER_FROM_ENDPOINT_MAPPED)
        }
    }

    private fun mockUsersApi() {
        usersApiMock = mock()
        whenever(usersApiMock.getAllUsers()).thenReturn(
            Observable.just(USERS_FROM_ENDPOINT)
        )
        whenever(usersApiMock.getUser(USERNAME)).thenReturn(
            Observable.just(USER_FROM_ENDPOINT)
        )
    }

    companion object {

        private const val USERNAME = "octocat"

        private val USER_FROM_ENDPOINT = UserModel(
            42,
            USERNAME,
            "http://cdn.journaldev.com/wp-content/uploads/2016/11/android-image-picker-project-structure.png",
            "LoL"
        )

        private val USER_FROM_ENDPOINT_MAPPED = USER_FROM_ENDPOINT.toUser()

        private val USERS_FROM_ENDPOINT = listOf(
            UserModel(
                0,
                "szumi",
                "http://cdn.journaldev.com/wp-content/uploads/2016/11/android-image-picker-project-structure.png",
                "LoL"
            ),
            UserModel(
                1,
                "sancia",
                "http://cdn.journaldev.com/wp-content/uploads/2016/11/android-image-picker-project-structure.png",
                "LoL"
            ),
            UserModel(
                2,
                "seycher",
                "http://cdn.journaldev.com/wp-content/uploads/2016/11/android-image-picker-project-structure.png",
                "LoL"
            ),
            UserModel(
                3,
                "tomokene",
                "http://cdn.journaldev.com/wp-content/uploads/2016/11/android-image-picker-project-structure.png",
                "LoL"
            )
        )

        private val USERS_FROM_ENDPOINT_MAPPED = USERS_FROM_ENDPOINT.map {
            it.toUser()
        }

    }
}