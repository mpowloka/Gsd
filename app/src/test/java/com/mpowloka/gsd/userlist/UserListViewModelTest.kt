package com.mpowloka.gsd.userlist

import android.app.Application
import com.mpowloka.gsd.R
import com.mpowloka.gsd.domain.applicationstate.usecase.GetCurrentUserUseCase
import com.mpowloka.gsd.domain.applicationstate.usecase.GetInitialUserSetUseCase
import com.mpowloka.gsd.domain.applicationstate.usecase.SetCurrentUserUseCase
import com.mpowloka.gsd.domain.user.User
import com.mpowloka.gsd.domain.user.usecase.FetchUserUseCase
import com.mpowloka.gsd.domain.user.usecase.GetAllUsersUseCase
import com.mpowloka.gsd.userlist.list.UserListAdapterData
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test

class UserListViewModelTest {

    private lateinit var SUT: UserListViewModel

    private lateinit var getAllUsersUseCaseMock: GetAllUsersUseCase
    private lateinit var getInitialUserSetUseCaseMock: GetInitialUserSetUseCase
    private lateinit var setCurrentUserUseCaseMock: SetCurrentUserUseCase
    private lateinit var getCurrentUserUseCaseMock: GetCurrentUserUseCase
    private lateinit var fetchUserUseCase: FetchUserUseCase
    private lateinit var applicationMock: Application

    @Before
    fun setup() {
        initializeMocks()
        SUT = UserListViewModel(
            getAllUsersUseCaseMock,
            setCurrentUserUseCaseMock,
            getCurrentUserUseCaseMock,
            getInitialUserSetUseCaseMock,
            fetchUserUseCase,
            applicationMock
        )
    }

    @Test
    fun fetchUser_usernamePassedToUseCase() {
        SUT.fetchUser(USERNAME)

        verify(fetchUserUseCase, times(1)).fetch(USERNAME)
    }

    @Test
    fun initializeFirstCurrentUserIfNeeded_noCurrentUserSet_firstUserFromApiSet() {

        setupCurrentUserSet(false)

        SUT.initializeFirstCurrentUserIfNeeded()

        verify(setCurrentUserUseCaseMock, timeout(1000)).set(ALL_USERS[0])
    }

    @Test
    fun initializeFirstCurrentUserIfNeeded_currentUserSet_noNewCurrentUserSet() {

        setupCurrentUserSet(true)

        SUT.initializeFirstCurrentUserIfNeeded()

        verifyZeroInteractions(setCurrentUserUseCaseMock)
    }

    @Test
    fun setCurrentUser_userPassedToUseCase() {
        SUT.setCurrentUser(USER)

        verify(setCurrentUserUseCaseMock, times(1)).set(USER)
    }

    @Test
    fun adapterData_noUsersAvailable_messageItemReturned() {
        setupNoUsersAvailable()

        SUT.adapterData.test().assertValue {
            it.items.size == 1 && it.items[0] is UserListAdapterData.Item.MessageItem
        }
    }

    @Test
    fun adapterData_currentUserReturned() {
        SUT.adapterData.test().assertValue {
            it.selectedItem == UserListAdapterData.Item.UserItem(USER)
        }
    }

    @Test
    fun adapterData_noPhrase_allUsersReturned() {
        SUT.adapterData.test().assertValue {
            it.items == ALL_USERS_SHORT_DATA
        }
    }

    @Test
    fun adapterData_phrasePresent_filteredUsersReturned() {
        SUT.nextSearchPhrase(PHRASE)

        SUT.adapterData.test().assertValue {
            it.items == PHRASE_FILTERED_USERS_DATA
        }
    }

    private fun initializeMocks() {
        applicationMock = mock()
        fetchUserUseCase = mock()
        getInitialUserSetUseCaseMock = mock()
        getAllUsersUseCaseMock = mock()
        setCurrentUserUseCaseMock = mock()
        getCurrentUserUseCaseMock = mock()

        whenever(applicationMock.getString(R.string.tap_search_message)).thenReturn(
            SEARCH_MESSAGE_TEXT
        )

        whenever(getAllUsersUseCaseMock.get()).thenReturn(
            Observable.just(ALL_USERS)
        )

        whenever(getCurrentUserUseCaseMock.get()).thenReturn(
            Observable.just(USER)
        )
    }

    private fun setupCurrentUserSet(set: Boolean) {
        whenever(getInitialUserSetUseCaseMock.get()).thenReturn(
            Observable.just(set)
        )
    }

    private fun setupNoUsersAvailable() {
        whenever(getAllUsersUseCaseMock.get()).thenReturn(
            Observable.just(emptyList())
        )
    }

    companion object {

        private const val USERNAME = "USERNAME"

        private const val SEARCH_MESSAGE_TEXT = "SEARCH_MESSAGE_TEXT"

        private const val PHRASE = "m"

        private val USER = User(
            0,
            "szumi",
            "GitHub",
            "http://cdn.journaldev.com/wp-content/uploads/2016/11/android-image-picker-project-structure.png"
        )

        private val ALL_USERS = listOf(
            User(
                0,
                "szumi",
                "GitHub",
                "http://cdn.journaldev.com/wp-content/uploads/2016/11/android-image-picker-project-structure.png"
            ),
            User(
                1,
                "sancia",
                "GitHub",
                "http://cdn.journaldev.com/wp-content/uploads/2016/11/android-image-picker-project-structure.png"
            ),
            User(
                2,
                "seycher",
                "GitHub",
                "http://cdn.journaldev.com/wp-content/uploads/2016/11/android-image-picker-project-structure.png"
            ),
            User(
                3,
                "tomokene",
                "GitHub",
                "http://cdn.journaldev.com/wp-content/uploads/2016/11/android-image-picker-project-structure.png"
            )
        )

        private val ALL_USERS_SHORT_DATA = ALL_USERS.map { UserListAdapterData.Item.UserItem(it) }

        private val PHRASE_FILTERED_USERS_DATA = listOf(
            User(
                0,
                "szumi",
                "GitHub",
                "http://cdn.journaldev.com/wp-content/uploads/2016/11/android-image-picker-project-structure.png"
            ),
            User(
                3,
                "tomokene",
                "GitHub",
                "http://cdn.journaldev.com/wp-content/uploads/2016/11/android-image-picker-project-structure.png"
            )
        ).map { UserListAdapterData.Item.UserItem(it) }

    }
}