package com.ajk.pulse.presentation

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import com.ajk.pulse.service.Actions
import com.ajk.pulse.service.VibrationService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel(private val application: Application) : AndroidViewModel(application) {

    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning

    fun toggleReminder() {
        _isRunning.value = !_isRunning.value
        if (_isRunning.value) {
            startReminder()
        } else {
            stopReminder()
        }
    }

    private fun startReminder() {
        val intent = Intent(application, VibrationService::class.java).apply {
            action = Actions.START.toString()
        }
        application.startService(intent)
    }

    private fun stopReminder() {
        val intent = Intent(application, VibrationService::class.java).apply {
            action = Actions.STOP.toString()
        }
        application.startService(intent)
    }
}
