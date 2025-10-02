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

    private val _selectedHour = MutableStateFlow(0)
    val selectedHour: StateFlow<Int> = _selectedHour

    private val _selectedMinute = MutableStateFlow(15)
    val selectedMinute: StateFlow<Int> = _selectedMinute

    fun setHour(hour: Int) {
        _selectedHour.value = hour
    }

    fun setMinute(minute: Int) {
        _selectedMinute.value = minute
    }

    fun startReminder() {
        _isRunning.value = true
        val interval = (_selectedHour.value * 60 + _selectedMinute.value) * 60 * 1000L
        val intent = Intent(application, VibrationService::class.java).apply {
            action = Actions.START.toString()
            putExtra("interval", interval)
        }
        application.startService(intent)
    }

    fun stopReminder() {
        _isRunning.value = false
        val intent = Intent(application, VibrationService::class.java).apply {
            action = Actions.STOP.toString()
        }
        application.startService(intent)
    }
}
