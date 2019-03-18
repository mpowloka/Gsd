package com.mpowloka.gsd.domain.applicationstate.usecase

import com.mpowloka.gsd.domain.applicationstate.ApplicationStateRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test

class GetInitialUserSetUseCaseTest {

    private lateinit var SUT: GetInitialUserSetUseCase
    private lateinit var applicationStateRepositoryMock: ApplicationStateRepository

    @Before
    fun setup() {
        mockUsersRepository()
        SUT = GetInitialUserSetUseCase(applicationStateRepositoryMock)
    }

    @Test
    fun get_repositoryQueried() {
        val result = SUT.get()

        verify(applicationStateRepositoryMock, times(1)).getInitialUserSet()

        result.test().assertValue(true)
    }

    private fun mockUsersRepository() {
        applicationStateRepositoryMock = mock()
        whenever(applicationStateRepositoryMock.getInitialUserSet()).thenReturn(Observable.just(true))
    }

}