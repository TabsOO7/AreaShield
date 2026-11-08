
package com.tabeni.areashield.data

import android.content.Context
import com.tabeni.areashield.model.IncidentReport
import org.json.JSONArray
import org.json.JSONObject

object ReportStorage {

    private const val PREFS_NAME = "areashield_storage"
    private const val REPORTS_KEY = "incident_reports"

    fun saveReports(
        context: Context,
        reports: List<IncidentReport>
    ) {
        val jsonArray = JSONArray()

        reports.forEach { report ->

            val jsonObject = JSONObject().apply {
                put("area", report.area)
                put("description", report.description)
                put("date", report.date)
                put("time", report.time)
                put("status", report.status)
            }

            jsonArray.put(jsonObject)
        }

        val preferences = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )

        preferences.edit()
            .putString(REPORTS_KEY, jsonArray.toString())
            .apply()
    }

    fun loadReports(context: Context): List<IncidentReport> {

        val preferences = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )

        val savedData = preferences.getString(
            REPORTS_KEY,
            null
        ) ?: return emptyList()

        return try {

            val jsonArray = JSONArray(savedData)
            val reports = mutableListOf<IncidentReport>()

            for (i in 0 until jsonArray.length()) {

                val item = jsonArray.getJSONObject(i)

                val report = IncidentReport(
                    area = item.getString("area"),
                    description = item.getString("description"),
                    date = item.getString("date"),
                    time = item.getString("time"),
                    status = item.optString("status", "Unverified")
                )

                reports.add(report)
            }

            reports

        } catch (exception: Exception) {
            emptyList()
        }
    }
}
