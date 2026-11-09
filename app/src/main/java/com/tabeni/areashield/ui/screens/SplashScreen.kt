package com.tabeni.areashield.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tabeni.areashield.R
import com.tabeni.areashield.ui.components.AreaShieldBackground
import com.tabeni.areashield.ui.theme.AreaShieldTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    onTimeout: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(1800)
        onTimeout()
    }

    AreaShieldBackground(
        modifier = modifier.clickable { onTimeout() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_areashield_logo),
                    contentDescription = "AreaShield Logo",
                    modifier = Modifier.size(130.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "AreaShield",
                    style = MaterialTheme.typography.displayMedium.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    ),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.semantics { heading() }
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Report • View • Stay Safer",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(36.dp))

                Text(
                    text = "Building a safer Ndola together.",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Medium
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(32.dp),
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 3.dp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Loading...",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Light Theme")
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Theme")
@Composable
fun SplashScreenPreview() {
    AreaShieldTheme {
        SplashScreen(onTimeout = {})
    }
}
