package com.ys.batterytrackerapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BatteryReceiver(val setIsBatteryLow: (Boolean) -> Unit): BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        when(intent!!.action){
            Intent.ACTION_BATTERY_LOW-> setIsBatteryLow(true)
            Intent.ACTION_BATTERY_OKAY->setIsBatteryLow(false)
        }

    }
}