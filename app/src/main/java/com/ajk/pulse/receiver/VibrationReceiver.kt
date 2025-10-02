package com.ajk.pulse.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log

class VibrationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        Log.d("Vibration", "onReceive: Received")
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        // Example pattern: Short-Short-Long
        val vibrationEffect = VibrationEffect.createWaveform(longArrayOf(0, 200, 200, 200, 200, 500), -1)
        vibrator.vibrate(vibrationEffect)
    }
}
