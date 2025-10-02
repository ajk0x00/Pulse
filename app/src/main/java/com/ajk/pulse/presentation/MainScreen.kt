package com.ajk.pulse.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Picker
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.rememberPickerState
import kotlinx.coroutines.launch

@Composable
fun MainScreen(navController: NavController, viewModel: MainViewModel) {
    val isRunning = viewModel.isRunning.collectAsState().value

    val scope = rememberCoroutineScope()
    val hourItems = (0..23).map { it.toString() }
    val minuteItems = (0..59).map { it.toString() }

    val hourState = rememberPickerState(initialNumberOfOptions = hourItems.size)
    val minuteState = rememberPickerState(initialNumberOfOptions = minuteItems.size)

    LaunchedEffect(Unit) {
        minuteState.scrollToOption(15)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Picker(
                modifier = Modifier.height(100.dp).width(50.dp),
                state = hourState,
                contentDescription = "Select hours",
            ) {
                Text(hourItems[it], style = MaterialTheme.typography.display1)
            }
            Text(text = "h", style = MaterialTheme.typography.display1)
            Picker(
                modifier = Modifier.height(100.dp).width(50.dp),
                state = minuteState,
                contentDescription = "Select minutes",
            ) {
                Text(minuteItems[it], style = MaterialTheme.typography.display1)
            }
            Text(text = "m", style = MaterialTheme.typography.display1)
        }
        if (isRunning) {
            Button(onClick = { viewModel.stopReminder() }) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Stop",
                    tint = Color.Red
                )
            }
        } else {
            Button(onClick = {
                scope.launch {
                    viewModel.setHour(hourItems[hourState.selectedOption].toInt())
                    viewModel.setMinute(minuteItems[minuteState.selectedOption].toInt())
                    viewModel.startReminder()
                }
            }) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "Start"
                )
            }
        }
    }
}
