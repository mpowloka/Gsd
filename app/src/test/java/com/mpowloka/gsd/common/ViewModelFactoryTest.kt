package com.mpowloka.gsd.common

import androidx.lifecycle.ViewModel
import com.mpowloka.gsd.domain.user.UsersRepository
import com.mpowloka.gsd.userdetails.UserDetailsViewModel
import com.nhaarman.mockitokotlin2.mock
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ViewModelFactoryTest {

    private lateinit var SUT: ViewModelFactory

    private lateinit var usersRepositoryMock: UsersRepository

    @Before
    fun setup() {
        mockUsersRepository()
        SUT = ViewModelFactory(usersRepositoryMock)
    }

    @Test
    fun create_detailsViewModelClassProvided_detailsViewModelReturned() {
        val result = SUT.create(UserDetailsViewModel::class.java)

        assertEquals(result::class.java, UserDetailsViewModel::class.java)
    }

    @Test(expected = IllegalArgumentException::class)
    fun create_unknownViewModelClassProvided_illegalArgumentExceptionThrown() {
        SUT.create(object : ViewModel() {}::class.java)
    }

    private fun mockUsersRepository() {
        usersRepositoryMock = mock()
    }
}