package com.amir.circletosearch

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView

class SettingsActivity : android.app.Activity() {

    private val prefsName = "circle_to_search"
    private val targetPackageKey = "target_package"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val apps = packageManager.getInstalledApplications(0)
            .filter {
                it.packageName != packageName &&
                (it.flags and ApplicationInfo.FLAG_SYSTEM) == 0
            }
            .sortedBy {
                packageManager.getApplicationLabel(it).toString().lowercase()
            }

        val labels = apps.map {
            packageManager.getApplicationLabel(it).toString()
        }

        val packages = apps.map {
            it.packageName
        }

        val current = getSharedPreferences(prefsName, MODE_PRIVATE)
            .getString(targetPackageKey, null)

        var selected = packages.indexOf(current)
        if (selected < 0) selected = 0

        val title = TextView(this).apply {
            text = "Circle to Search"
            textSize = 24f
            gravity = Gravity.CENTER
            setPadding(0, 40, 0, 24)
        }

        val spinner = Spinner(this).apply {
            adapter = ArrayAdapter(
                this@SettingsActivity,
                android.R.layout.simple_spinner_dropdown_item,
                labels
            )

            if (labels.isNotEmpty()) {
                setSelection(selected)
            }
        }

        val save = Button(this).apply {
            text = "Save target app"

            setOnClickListener {
                if (packages.isNotEmpty()) {
                    getSharedPreferences(prefsName, MODE_PRIVATE)
                        .edit()
                        .putString(
                            targetPackageKey,
                            packages[spinner.selectedItemPosition]
                        )
                        .apply()
                }
            }
        }

        val assistantSettings = Button(this).apply {
            text = "Open Digital Assistant settings"

            setOnClickListener {
                startActivity(
                    Intent(Settings.ACTION_VOICE_INPUT_SETTINGS)
                )
            }
        }

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 24, 32, 32)

            addView(title)
            addView(spinner)
            addView(save)
            addView(assistantSettings)
        }

        setContentView(layout)
    }
}
