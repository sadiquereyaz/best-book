package com.nabssam.bestbook

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.phonepe.intent.sdk.api.PhonePeKt
import com.phonepe.intent.sdk.api.models.PhonePeEnvironment

private const val TAG = "PhonePePayment"
 const val PAYMENT_REQUEST_CODE = 1001
// Example usage in your Activity
class PhonePeActivity : ComponentActivity() {

    // Test credentials for sandbox environment
    companion object {
        const val MERCHANT_ID = "PGTESTPAYUAT86" // PhonePe test merchant ID
//        const val MERCHANT_ID = "ATMOSTUAT" // PhonePe test merchant ID
        const val TEST_FLOW_ID = "58a63b64-574d-417a-9214-066bee1e4caa"
        const val MERCHANT_KEY = "96434309-7796-489d-8924-ab56988a6076"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize SDK early
        initializeSDK()

        setContent {
           // PaymentScreen()
        }
    }

    private fun initializeSDK() {
        try {
            val result = PhonePeKt.init(
                context = applicationContext,
                merchantId = MERCHANT_ID,
                flowId = TEST_FLOW_ID,
                phonePeEnvironment = PhonePeEnvironment.SANDBOX, // Use SANDBOX for testing
                enableLogging = true // Enable logs for debugging
            )

            Log.d(TAG, "SDK Initialization result: $result")

        } catch (e: Exception) {
            Log.e(TAG, "SDK Initialization failed", e)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PAYMENT_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    Log.d(TAG, "Test payment successful")
                }
                Activity.RESULT_CANCELED -> {
                    val error = data?.getStringExtra("error")
                    Log.d(TAG, "Test payment failed: $error")
                }
            }
        }
    }
}