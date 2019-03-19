package com.mpowloka.gsd.common

import androidx.lifecycle.ViewModel
import com.mpowloka.gsd.MainViewModel
import com.mpowloka.gsd.userdetails.UserDetailsViewModel
import com.mpowloka.gsd.userlist.UserListViewModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ViewModelFactoryTest {

    private lateinit var SUT: ViewModelFactory

    private lateinit var applicationMock: GsdApplication

    @Before
    fun setup() {
        mockApplication()
        SUT = ViewModelFactory(
            applicationMock
        )
    }

    @Test
    fun create_mainViewModelClassProvided_mainViewModelReturned() {
        val result = SUT.create(MainViewModel::class.java)

        assertEquals(result::class.java, MainViewModel::class.java)
    }

    @Test
    fun create_userDetailsViewModelClassProvided_userDetailsViewModelReturned() {
        val result = SUT.create(UserDetailsViewModel::class.java)

        assertEquals(result::class.java, UserDetailsViewModel::class.java)
    }

    @Test
    fun create_userListViewModelClassProvided_userListViewModelReturned() {
        val result = SUT.create(UserListViewModel::class.java)

        assertEquals(result::class.java, UserListViewModel::class.java)
    }

    @Test(expected = IllegalArgumentException::class)
    fun create_unknownViewModelClassProvided_illegalArgumentExceptionThrown() {
        SUT.create(object : ViewModel() {}::class.java)
    }

    private fun mockApplication() {
        applicationMock = mock()
        whenever(applicationMock.applicationStateRepository).thenReturn(mock())
        whenever(applicationMock.usersFetcher).thenReturn(mock())
        whenever(applicationMock.usersRepository).thenReturn(mock())
        whenever(applicationMock.getCurrentUserUseCase).thenReturn(mock())
        whenever(applicationMock.setCurrentUserUseCase).thenReturn(mock())
        whenever(applicationMock.getAllUsersUseCase).thenReturn(mock())
        whenever(applicationMock.getInitialUserSetUseCase).thenReturn(mock())
        whenever(applicationMock.fetchUserUseCase).thenReturn(mock())
        whenever(applicationMock.fetchUsersUseCase).thenReturn(mock())

    }
}