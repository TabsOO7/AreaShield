package com.tabeni.areashield.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tabeni.areashield.model.IncidentReport
import com.tabeni.areashield.ui.components.AreaShieldBackground
import com.tabeni.areashield.ui.theme.AreaShieldTheme

@Composable
fun HomeScreen(
    reports: List<IncidentReport>,
    modifier: Modifier = Modifier,
    onReportClick: () -> Unit,
    onAboutClick: () -> Unit
) {
    AreaShieldBackground(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "AreaShield",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.semantics { heading() }
            )

            Text(
                text = "Community Safety Reporting",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onReportClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 48.dp)
            ) {
                Text("Report Incident")
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = onAboutClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 48.dp)
            ) {
                Text("About and Safety")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Recent Incident Reports",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.semantics { heading() }
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (reports.isEmpty()) {
                Text(
                    text = "No incident reports have been submitted yet.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(reports) { report ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .semantics(mergeDescendants = true) {}
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = report.area,
                                    style = MaterialTheme.typography.titleMedium
                                )

                                Spacer(modifier = Modifier.height(6.dp))

                                Text(
                                    text = report.description,
                                    style = MaterialTheme.typography.bodyMedium
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = "Date: ${report.date}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Text(
                                    text = "Time: ${report.time}",
                                    style = MaterialTheme.typography.bodySmall
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = "Status: ${report.status}",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Light Theme")
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Theme")
@Composable
fun HomeScreenPreview() {
    AreaShieldTheme {
        HomeScreen(
            reports = listOf(
                IncidentReport(
                    area = "Chifubu",
                    description = "Suspicious activity reported near market.",
                    date = "25/10/2026",
                    time = "14:30",
                    status = "Unverified"
                )
            ),
            onReportClick = {},
            onAboutClick = {}
        )
    }
}
