package com.mpowloka.gsd.domain.applicationstate.usecase

import com.mpowloka.gsd.domain.applicationstate.ApplicationStateRepository
import com.mpowloka.gsd.domain.user.User
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetCurrentUserUseCaseTest {

    private lateinit var SUT: GetCurrentUserUseCase
    private lateinit var applicationStateRepositoryMock: ApplicationStateRepository

    @Before
    fun setup() {
        mockUsersRepository()
        SUT = GetCurrentUserUseCase(applicationStateRepositoryMock)
    }

    @Test
    fun get_repositoryQueried() {
        val result = SUT.get()

        result.test().assertValue(CURRENT_USER)
    }

    private fun mockUsersRepository() {
        applicationStateRepositoryMock = mock()
        whenever(applicationStateRepositoryMock.getCurrentUser()).thenReturn(Observable.just(CURRENT_USER))
    }

    companion object {
        private val CURRENT_USER = User(0, "", "", "")
    }


}