package com.mpowloka.gsd.domain.applicationstate.usecase

import com.mpowloka.gsd.domain.applicationstate.ApplicationStateRepository

class GetInitialUserSetUseCase(
    private val applicationStateRepository: ApplicationStateRepository
) {

    fun get() = applicationStateRepository.getInitialUserSet()

}