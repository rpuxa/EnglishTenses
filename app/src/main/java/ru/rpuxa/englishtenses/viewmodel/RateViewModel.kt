package ru.rpuxa.englishtenses.viewmodel

import androidx.lifecycle.ViewModel
import ru.rpuxa.englishtenses.SingleLiveEvent
import ru.rpuxa.englishtenses.model.Prefs
import javax.inject.Inject

class RateViewModel @Inject constructor(
    private val prefs: Prefs
) : ViewModel() {
    fun doDismiss(): Boolean {
        return prefs.dismissRate
    }

    fun setDismiss() {
        prefs.dismissRate = true
    }
}