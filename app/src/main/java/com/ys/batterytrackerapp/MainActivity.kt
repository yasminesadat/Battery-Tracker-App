package com.ys.batterytrackerapp

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.ys.batterytrackerapp.ui.theme.BatteryTrackerAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BatteryTrackerAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BatteryStatus(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }

}

@Composable
fun BatteryStatus(modifier: Modifier = Modifier) {
    var isBatteryLow by rememberSaveable {
        mutableStateOf(false)
    }

    val batteryReceiver = BatteryReceiver{
        isBatteryLow = it
    }
    val filter = IntentFilter().apply {
        addAction(Intent.ACTION_BATTERY_LOW)
        addAction(Intent.ACTION_BATTERY_OKAY)
    }
    LocalContext.current.registerReceiver(batteryReceiver,filter)

    Image(
        painter = painterResource(
            id = if (isBatteryLow) R.drawable.battery_low
            else R.drawable.battery_okay
        ),
        contentDescription = null,
        modifier = modifier.fillMaxSize()
    )
}

@Preview(showBackground = true)
@Composable
fun BatteryStatusPreview() {
    BatteryStatus()
}