package com.tabeni.areashield.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.tabeni.areashield.model.IncidentReport

@Composable
fun ReportScreen(
    modifier: Modifier = Modifier,
    onSubmit: (IncidentReport) -> Unit,
    onCancel: () -> Unit
) {
    var area by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var date by rememberSaveable { mutableStateOf("") }
    var time by rememberSaveable { mutableStateOf("") }
    var errorMessage by rememberSaveable { mutableStateOf("") }

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Text(
            text = "Report an Incident",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.semantics { heading() }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = area,
            onValueChange = {
                area = it
                if (errorMessage.isNotEmpty()) errorMessage = ""
            },
            label = { Text("Affected Area") },
            placeholder = { Text("e.g. Chifubu") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = errorMessage.startsWith("Please enter the affected area")
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = description,
            onValueChange = {
                description = it
                if (errorMessage.isNotEmpty()) errorMessage = ""
            },
            label = { Text("Incident Description") },
            placeholder = { Text("Describe what happened...") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
            isError = errorMessage.startsWith("Please enter an incident description")
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = date,
            onValueChange = {
                date = it
                if (errorMessage.isNotEmpty()) errorMessage = ""
            },
            label = { Text("Incident Date") },
            placeholder = { Text("DD/MM/YYYY") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = errorMessage.contains("date")
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = time,
            onValueChange = {
                time = it
                if (errorMessage.isNotEmpty()) errorMessage = ""
            },
            label = { Text("Incident Time") },
            placeholder = { Text("HH:MM") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = errorMessage.contains("time")
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.semantics {
                    liveRegion = LiveRegionMode.Polite
                }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                val trimmedArea = area.trim()
                val trimmedDescription = description.trim()
                val trimmedDate = date.trim()
                val trimmedTime = time.trim()

                if (trimmedArea.isBlank()) {
                    errorMessage = "Please enter the affected area."
                } else if (trimmedDescription.isBlank()) {
                    errorMessage = "Please enter an incident description."
                } else if (trimmedDate.isBlank()) {
                    errorMessage = "Please enter the incident date."
                } else if (!isValidDate(trimmedDate)) {
                    errorMessage = "Please enter a valid date in DD/MM/YYYY format."
                } else if (trimmedTime.isBlank()) {
                    errorMessage = "Please enter the incident time."
                } else if (!isValidTime(trimmedTime)) {
                    errorMessage = "Please enter a valid time in HH:MM format (e.g. 14:30)."
                } else {
                    val newReport = IncidentReport(
                        area = trimmedArea,
                        description = trimmedDescription,
                        date = trimmedDate,
                        time = trimmedTime
                    )
                    onSubmit(newReport)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 48.dp)
        ) {
            Text("Submit Report")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            onClick = onCancel,
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 48.dp)
        ) {
            Text("Cancel")
        }
    }
}

private fun isValidDate(dateStr: String): Boolean {
    val parts = dateStr.split("/")
    if (parts.size != 3) return false
    val day = parts[0].toIntOrNull() ?: return false
    val month = parts[1].toIntOrNull() ?: return false
    val year = parts[2].toIntOrNull() ?: return false

    if (month !in 1..12) return false
    if (day !in 1..31) return false
    if (year !in 1900..2100) return false

    val daysInMonth = when (month) {
        2 -> if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) 29 else 28
        4, 6, 9, 11 -> 30
        else -> 31
    }
    return day <= daysInMonth
}

private fun isValidTime(timeStr: String): Boolean {
    val parts = timeStr.split(":")
    if (parts.size != 2) return false
    val hour = parts[0].toIntOrNull() ?: return false
    val minute = parts[1].toIntOrNull() ?: return false

    return hour in 0..23 && minute in 0..59
}
