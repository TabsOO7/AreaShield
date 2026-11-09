package com.tabeni.areashield

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.tabeni.areashield.data.ReportStorage
import com.tabeni.areashield.model.IncidentReport
import com.tabeni.areashield.ui.screens.AboutScreen
import com.tabeni.areashield.ui.screens.HomeScreen
import com.tabeni.areashield.ui.screens.ReportScreen
import com.tabeni.areashield.ui.theme.AreaShieldTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setContent {
            AreaShieldTheme {
                AreaShieldApp()
            }
        }
    }
}

@Composable
fun AreaShieldApp() {

    val context = LocalContext.current

    var currentScreen by rememberSaveable {
        mutableStateOf("home")
    }

    val reports = remember {
        mutableStateListOf<IncidentReport>().apply {
            addAll(ReportStorage.loadReports(context))
        }
    }

    // Handle the Android back button.
    BackHandler(enabled = currentScreen != "home") {
        currentScreen = "home"
    }

    Scaffold { paddingValues ->

        when (currentScreen) {

            "home" -> {
                HomeScreen(
                    reports = reports,
                    modifier = Modifier.padding(paddingValues),
                    onReportClick = {
                        currentScreen = "report"
                    },
                    onAboutClick = {
                        currentScreen = "about"
                    }
                )
            }

            "report" -> {
                ReportScreen(
                    modifier = Modifier.padding(paddingValues),
                    onSubmit = { report ->

                        reports.add(0, report)

                        ReportStorage.saveReports(
                            context,
                            reports
                        )

                        currentScreen = "home"
                    },
                    onCancel = {
                        currentScreen = "home"
                    }
                )
            }

            "about" -> {
                AboutScreen(
                    modifier = Modifier.padding(paddingValues),
                    onBack = {
                        currentScreen = "home"
                    }
                )
            }
        }
    }
}
