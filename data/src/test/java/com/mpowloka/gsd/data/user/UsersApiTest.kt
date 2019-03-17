package com.mpowloka.gsd.data.user

import org.junit.Before
import org.junit.Test

class UsersApiTest {

    private lateinit var SUT: UsersApi

    @Before
    fun setup() {
        SUT = UsersApi.newInstance()
    }

    @Test
    fun getAllUsers_usersReturned() {
        val result = SUT.getAllUsers()

        result.test().assertValue {
            it.isNotEmpty()
        }
    }

    @Test
    fun getUser_userReturned() {
        val result = SUT.getUser(USERNAME)

        result.test().assertValue {
            it.login == USERNAME
        }
    }

    companion object {

        private const val USERNAME = "mpowloka"

    }
}