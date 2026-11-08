package com.tabeni.areashield.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
fun AboutScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Text(
            text = "About AreaShield",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.semantics { heading() }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "AreaShield is a community safety reporting prototype designed for residents of Ndola.",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Safety Guidelines",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.semantics { heading() }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text("• Do not confront suspected gang members.", style = MaterialTheme.typography.bodyMedium)

        Text("• Do not include names or identifying personal details.", style = MaterialTheme.typography.bodyMedium)

        Text("• Only report information you believe to be accurate.", style = MaterialTheme.typography.bodyMedium)

        Text("• Reports are unverified and should not be treated as confirmed incidents.", style = MaterialTheme.typography.bodyMedium)

        Text("• Contact appropriate emergency services when immediate assistance is needed.", style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onBack,
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 48.dp)
        ) {
            Text("Back to Home")
        }
    }
}
