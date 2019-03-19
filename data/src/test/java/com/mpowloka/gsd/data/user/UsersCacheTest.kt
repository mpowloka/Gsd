package com.mpowloka.gsd.data.user

import com.mpowloka.gsd.domain.user.User

import org.junit.Before
import org.junit.Test

class UsersCacheTest {

    private lateinit var SUT: UsersCache

    @Before
    fun setup() {
        SUT = UsersCache()
    }

    @Test
    fun addUser_userEmitted() {
        SUT.addUser(USER)

        SUT.getUsers().test().assertValue {
            it.contains(USER)
        }
    }

    @Test
    fun addUsers_usersEmitted() {
        SUT.addUsers(USERS)

        SUT.getUsers().test().assertValue {
            it.containsAll(USERS)
        }
    }

    companion object {
        private val USER = User(
            42, "szumi", "org", ""
        )

        private val USERS = listOf(
            User(42, "szumi", "org", ""),
            User(42, "szumi", "org", ""),
            User(42, "szumi", "org", "")
        )
    }
}