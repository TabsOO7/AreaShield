package com.tabeni.areashield.model

data class IncidentReport(
    val area: String,
    val description: String,
    val date: String,
    val time: String,
    val status: String = "Unverified"
)
