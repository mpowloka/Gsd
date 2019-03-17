package com.mpowloka.gsd.userlist

import com.mpowloka.gsd.domain.user.User
import com.mpowloka.gsd.domain.user.usecase.GetAllUsersUseCase
import com.mpowloka.gsd.domain.user.usecase.SetCurrentUserUseCase
import com.mpowloka.gsd.userlist.list.UserListRecyclerAdapter
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test

class UserListViewModelTest {

    private lateinit var SUT: UserListViewModel

    private lateinit var getAllUsersUseCaseMock: GetAllUsersUseCase

    private lateinit var setCurrentUserUseCaseMock: SetCurrentUserUseCase

    @Before
    fun setup() {
        mockUseCases()
        SUT = UserListViewModel(
            getAllUsersUseCaseMock,
            setCurrentUserUseCaseMock
        )
    }

    @Test
    fun itemsToDisplay_noPhrase_allUsersReturned() {
        SUT.itemsToDisplay.test().assertValue(ALL_USERS_SHORT_DATA)
    }

    @Test
    fun itemsToDisplay_phrasePresent_filteredUsersReturned() {
        SUT.nextSearchPhrase(PHRASE)

        SUT.itemsToDisplay.test().assertValue(PHRASE_FILTERED_USERS_DATA)
    }

    @Test
    fun setCurrentUser_userPassedToUseCase() {
        SUT.setCurrentUser(USER)

        verify(setCurrentUserUseCaseMock, times(1)).set(USER)
    }

    private fun mockUseCases() {
        getAllUsersUseCaseMock = mock()
        setCurrentUserUseCaseMock = mock()
        whenever(getAllUsersUseCaseMock.get()).thenReturn(
            Observable.just(ALL_USERS)
        )
    }

    companion object {

        private const val PHRASE = "m"

        private val USER = User(0, "szumi", "LoL", "http://cdn.journaldev.com/wp-content/uploads/2016/11/android-image-picker-project-structure.png")

        private val ALL_USERS = listOf(
            User(0, "szumi", "LoL", "http://cdn.journaldev.com/wp-content/uploads/2016/11/android-image-picker-project-structure.png"),
            User(1, "sancia", "LoL", "http://cdn.journaldev.com/wp-content/uploads/2016/11/android-image-picker-project-structure.png"),
            User(2, "seycher", "LoL", "http://cdn.journaldev.com/wp-content/uploads/2016/11/android-image-picker-project-structure.png"),
            User(3, "tomokene", "LoL", "http://cdn.journaldev.com/wp-content/uploads/2016/11/android-image-picker-project-structure.png")
        )

        private val ALL_USERS_SHORT_DATA = ALL_USERS.map { UserListRecyclerAdapter.Item.UserItem(it) }

        private val PHRASE_FILTERED_USERS_DATA = listOf(
            User(0, "szumi", "LoL", "http://cdn.journaldev.com/wp-content/uploads/2016/11/android-image-picker-project-structure.png"),
            User(3, "tomokene", "LoL", "http://cdn.journaldev.com/wp-content/uploads/2016/11/android-image-picker-project-structure.png")
        ).map { UserListRecyclerAdapter.Item.UserItem(it) }

    }
}