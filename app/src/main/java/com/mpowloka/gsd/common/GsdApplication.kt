package com.mpowloka.gsd.common

import android.app.Application
import com.mpowloka.gsd.data.applicationstate.ApplicationStateRepositoryImpl
import com.mpowloka.gsd.data.user.UsersApi
import com.mpowloka.gsd.data.user.UsersCache
import com.mpowloka.gsd.data.user.UsersFetcherImpl
import com.mpowloka.gsd.data.user.UsersRepositoryImpl
import com.mpowloka.gsd.domain.applicationstate.ApplicationStateRepository
import com.mpowloka.gsd.domain.applicationstate.usecase.GetCurrentUserUseCase
import com.mpowloka.gsd.domain.applicationstate.usecase.GetInitialUserSetUseCase
import com.mpowloka.gsd.domain.applicationstate.usecase.SetCurrentUserUseCase
import com.mpowloka.gsd.domain.user.UsersFetcher
import com.mpowloka.gsd.domain.user.UsersRepository
import com.mpowloka.gsd.domain.user.usecase.FetchUserUseCase
import com.mpowloka.gsd.domain.user.usecase.FetchUsersUseCase
import com.mpowloka.gsd.domain.user.usecase.GetAllUsersUseCase

class GsdApplication : Application() {

    lateinit var applicationStateRepository: ApplicationStateRepository

    lateinit var usersFetcher: UsersFetcher

    lateinit var usersRepository: UsersRepository

    lateinit var getCurrentUserUseCase: GetCurrentUserUseCase

    lateinit var setCurrentUserUseCase: SetCurrentUserUseCase

    lateinit var getAllUsersUseCase: GetAllUsersUseCase

    lateinit var getInitialUserSetUseCase: GetInitialUserSetUseCase

    lateinit var fetchUserUseCase: FetchUserUseCase

    lateinit var fetchUsersUseCase: FetchUsersUseCase

    override fun onCreate() {
        super.onCreate()

        buildGlobalAccessServices()

        fetchUsersUseCase.fetch()
    }

    private fun buildGlobalAccessServices() {
        applicationStateRepository = ApplicationStateRepositoryImpl()

        usersRepository = UsersRepositoryImpl(
            UsersCache()
        )

        usersFetcher = UsersFetcherImpl(
            usersRepository,
            UsersApi.newInstance()
        )

        getCurrentUserUseCase = GetCurrentUserUseCase(applicationStateRepository)

        setCurrentUserUseCase = SetCurrentUserUseCase(applicationStateRepository)

        getAllUsersUseCase = GetAllUsersUseCase(usersRepository)

        getInitialUserSetUseCase = GetInitialUserSetUseCase(applicationStateRepository)

        fetchUserUseCase = FetchUserUseCase(usersFetcher)

        fetchUsersUseCase = FetchUsersUseCase(usersFetcher)
    }

}