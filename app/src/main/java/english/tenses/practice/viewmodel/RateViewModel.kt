package english.tenses.practice.viewmodel

import androidx.lifecycle.ViewModel
import english.tenses.practice.model.Prefs
import javax.inject.Inject

class RateViewModel @Inject constructor(
    private val prefs: Prefs
) : ViewModel() {
    fun doDismiss(): Boolean {
        return prefs.appOpens < 10 || prefs.dismissRate
    }

    fun setDismiss() {
        prefs.dismissRate = true
    }
}