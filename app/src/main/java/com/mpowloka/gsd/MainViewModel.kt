package com.mpowloka.gsd

import androidx.lifecycle.ViewModel
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class MainViewModel : ViewModel() {

    val currentPhrase = BehaviorSubject.createDefault("")
    val searchClicks = PublishSubject.create<String>()

    fun setCurrentPhrase(phrase: String) {
        currentPhrase.onNext(phrase)
    }

    fun searchPressed(currentPhrase: String) {
        searchClicks.onNext(currentPhrase)
    }
}