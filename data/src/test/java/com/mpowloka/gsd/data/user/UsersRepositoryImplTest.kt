package com.mpowloka.gsd.data.user

import com.mpowloka.gsd.domain.user.User
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test

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

        result.test().assertValue {
            it == USERS_FROM_ENDPOINT_MAPPED
        }
    }



    private fun mockUsersApi() {
        usersApiMock = mock()
        whenever(usersApiMock.getAllUsers()).thenReturn(Observable.just(USERS_FROM_ENDPOINT))
    }

    companion object {

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