package com.ajk.pulse.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.Switch
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.ToggleChip

@Composable
fun MainScreen(navController: NavController, viewModel: MainViewModel) {
    val isRunning by viewModel.isRunning.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ToggleChip(
            checked = isRunning,
            onCheckedChange = { viewModel.toggleReminder() },
            label = { Text(if (isRunning) "Stop" else "Start") },
            toggleControl = {
                Switch(checked = isRunning)
            }
        )
        Chip(
            onClick = { navController.navigate("settings") },
            label = { Text("Interval: 15 min") }
        )
    }
}
