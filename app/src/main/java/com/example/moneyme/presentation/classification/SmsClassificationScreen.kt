package com.example.moneyme.presentation.classification

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moneyme.presentation.navigation.Screen
import kotlin.system.exitProcess

@Composable
fun SmsClassificationScreen(
    navController: NavController,
    amount: Double,
    body: String,
    modifier: Modifier = Modifier
) {
    val activity = LocalActivity.current as? Activity

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text("Transaction Details", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = modifier.height(16.dp))

            Text("Amount: $amount EGP", fontWeight = FontWeight.Bold)
            Spacer(modifier = modifier.height(8.dp))
            Text("SMS Body:")
            Text(body)
        }

        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    navController.navigate(Screen.MoneyIn.withArgs(amount, body))
                },
                modifier = modifier.weight(1f)
            ) {
                Text("Money In")
            }

            Spacer(modifier = modifier.width(8.dp))

            Button(
                onClick = {
                    navController.navigate(Screen.MoneyOut.withArgs(amount, body))
                },
                modifier = modifier.weight(1f)
            ) {
                Text("Money Out")
            }

            Spacer(modifier = modifier.width(8.dp))

            Button(
                onClick = {
                    activity?.finishAffinity() // Close the app
                    exitProcess(0) // Exit the process to remove from recents
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Neither")
            }
        }
    }
}
