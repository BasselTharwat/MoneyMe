package com.example.moneyme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.moneyme.ui.theme.MoneyMeTheme
import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Telephony
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.moneyme.presentation.navigation.AppNavHost
import com.example.moneyme.presentation.navigation.Screen
import com.example.moneyme.utils.CHANNEL_ID


class MainActivity : ComponentActivity() {
    private val DEFAULT_SMS_REQUEST_CODE = 456
    private val REQUIRED_PERMISSIONS = mutableListOf(
        Manifest.permission.READ_SMS,
        Manifest.permission.RECEIVE_SMS
    ).apply {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            add(Manifest.permission.POST_NOTIFICATIONS)
        }
    }.toTypedArray()

    private val PERMISSION_REQUEST_CODE = 123

    private fun requestPermissionsIfNecessary() {
        val permissionsToRequest = REQUIRED_PERMISSIONS.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }

        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toTypedArray(),
                PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Transaction Alerts",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifies when a transaction is detected"
            }

            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    private fun requestDefaultSmsApp() {
        val myPackageName = packageName
        val defaultSmsPackage = android.provider.Telephony.Sms.getDefaultSmsPackage(this)

        if (defaultSmsPackage != myPackageName) {
            val intent = Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT).apply {
                putExtra(android.provider.Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, myPackageName)
            }
            startActivityForResult(intent, DEFAULT_SMS_REQUEST_CODE)
        }
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissionsIfNecessary()
        createNotificationChannel()
        requestDefaultSmsApp()

        // Handle notification click
        val amount = intent.getDoubleExtra("amount", -1.0)
        val smsBody = intent.getStringExtra("sms_body") ?: ""

        val startDestination = if (amount != -1.0 && smsBody.isNotBlank()) {
            Screen.Classify.withArgs(amount, smsBody)
        } else {
            Screen.Home.route
        }

        enableEdgeToEdge()
        setContent {

            MoneyMeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavHost(startDestination = startDestination, modifier = Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }



