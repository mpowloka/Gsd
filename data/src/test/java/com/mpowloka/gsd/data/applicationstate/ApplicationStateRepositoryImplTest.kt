package com.mpowloka.gsd.data.applicationstate

import com.mpowloka.gsd.domain.applicationstate.ApplicationStateRepository
import com.mpowloka.gsd.domain.user.User
import org.junit.Before
import org.junit.Test

class ApplicationStateRepositoryImplTest {

    private lateinit var SUT: ApplicationStateRepositoryImpl

    @Before
    fun setup() {
        SUT = ApplicationStateRepositoryImpl()
    }

    @Test
    fun getCurrentUser_noCurrentUser_noValueEmitted() {
        val result = SUT.getCurrentUser()

        result.test().assertValue(ApplicationStateRepository.DEFAULT_USER)
    }

    @Test
    fun getCurrentUser_currentUserAvailable_currentUserReturned() {
        SUT.setCurrentUser(USER)

        val result = SUT.getCurrentUser()

        result.test().assertValue(USER)
    }

    @Test
    fun getInitialUserSet_currentUserNotSet_falseReturned() {
        val result = SUT.getInitialUserSet()

        result.test().assertValue(false)
    }

    @Test
    fun getInitialUserSet_currentUserSet_trueReturned() {
        SUT.setCurrentUser(USER)

        val result = SUT.getInitialUserSet()

        result.test().assertValue(true)
    }

    companion object {

        private val USER = User(42, "asdf", "movies", "")

    }

}