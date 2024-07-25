package com.ys.batterytrackerapp

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
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
import androidx.compose.runtime.remember
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
    val context = LocalContext.current
    var isBatteryLow by remember {
        mutableStateOf(getIsBatteryLow(context))
    }

    val batteryReceiver = BatteryReceiver {
        isBatteryLow = it
    }
    val filter = IntentFilter().apply {
        addAction(Intent.ACTION_BATTERY_LOW)
        addAction(Intent.ACTION_BATTERY_OKAY)
    }
    context.registerReceiver(batteryReceiver, filter)

    Image(
        painter = painterResource(
            id = if (isBatteryLow) R.drawable.battery_low
            else R.drawable.battery_okay
        ),
        contentDescription = null,
        modifier = modifier.fillMaxSize()
    )
}

private fun getIsBatteryLow(context: Context): Boolean {
    val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let {
        context.registerReceiver(null, it)
    }
    val level = batteryStatus!!.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
    return level <= 20
}

@Preview(showBackground = true)
@Composable
fun BatteryStatusPreview() {
    BatteryStatus()
}