package com.mpowloka.gsd.common

import android.app.Application
import androidx.lifecycle.ViewModel
import com.mpowloka.gsd.domain.applicationstate.ApplicationStateRepository
import com.mpowloka.gsd.domain.user.UsersRepository
import com.mpowloka.gsd.userdetails.UserDetailsViewModel
import com.mpowloka.gsd.userlist.UserListViewModel
import com.nhaarman.mockitokotlin2.mock
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ViewModelFactoryTest {

    private lateinit var SUT: ViewModelFactory

    private lateinit var usersRepositoryMock: UsersRepository
    private lateinit var applicationStateRepositoryMock: ApplicationStateRepository
    private lateinit var applicationMock: Application

    @Before
    fun setup() {
        initializeMocks()
        SUT = ViewModelFactory(
            usersRepositoryMock,
            applicationStateRepositoryMock,
            applicationMock
        )
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

    private fun initializeMocks() {
        applicationMock = mock()
        applicationStateRepositoryMock = mock()
        usersRepositoryMock = mock()
    }
}