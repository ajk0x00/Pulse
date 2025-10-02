package com.ajk.pulse.service

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.os.SystemClock
import androidx.core.app.NotificationCompat
import com.ajk.pulse.R
import com.ajk.pulse.receiver.VibrationReceiver

enum class Actions {
    START, STOP
}

class VibrationService : Service() {

    private lateinit var alarmManager: AlarmManager
    private var alarmPendingIntent: PendingIntent? = null

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            Actions.START.toString() -> start()
            Actions.STOP.toString() -> stop()
        }
        return START_STICKY
    }

    private fun start() {
        createNotificationChannel()
        val notification = NotificationCompat.Builder(this, "pulse_channel")
            .setContentTitle("Pulse Active")
            .setContentText("Vibrations are active.")
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Replace with a real icon
            .build()
        startForeground(1, notification)

        val alarmIntent = Intent(this, VibrationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, PendingIntent.FLAG_IMMUTABLE)
        this.alarmPendingIntent = pendingIntent

        val interval = 60 * 1000L // 2 minutes
        alarmManager.setRepeating(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime() + interval,
            interval,
            pendingIntent
        )
    }

    private fun stop() {
        alarmPendingIntent?.let { alarmManager.cancel(it) }
        stopForeground(true)
        stopSelf()
    }

    private fun createNotificationChannel() {
        val name = "Pulse"
        val descriptionText = "Pulse reminders"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("pulse_channel", name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
