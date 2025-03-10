package com.nabssam.bestbook.presentation.ui.account.auth.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.nabssam.bestbook.presentation.ui.account.auth.AuthEvent
import com.nabssam.bestbook.presentation.ui.account.auth.AuthState

@Composable
fun MobileVerificationStep(
    state: AuthState,
    onEvent: (AuthEvent) -> Unit,
    validate: () -> Boolean
) {
    var remainingTime by remember { mutableIntStateOf(240) } // 4 minutes in seconds

    LaunchedEffect(state.isOtpSent) {
        if (state.isOtpSent) {
            remainingTime = 240 // Reset the timer when OTP is sent
            while (remainingTime > 0) {
                kotlinx.coroutines.delay(1000)
                remainingTime--
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Mobile Verification",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(vertical = 32.dp)
        )
        if (!state.isOtpSent) {
            OutlinedTextField(
                value = state.mobileNumber,
                onValueChange = { if(state.mobileNumber.length < 11) onEvent(AuthEvent.UpdateMobile(it)) },
                label = { Text("Mobile Number") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { onEvent(AuthEvent.RegisterAndSendOtp) },
                enabled = state.mobileNumber.length == 10,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Send OTP")
            }
        } else {
            OutlinedTextField(
                value = state.otp,
                onValueChange = { if(state.otp.length < 5) onEvent(AuthEvent.UpdateOtp(it)) },
                label = { Text("Enter OTP") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                isError = state.otp.length > 6,
                supportingText = {
                    if (remainingTime > 0) {
                        val minutes = remainingTime / 60
                        val seconds = remainingTime % 60
                        Text(
                            "Resend in $minutes:${seconds.toString().padStart(2, '0')}"
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { onEvent(AuthEvent.VerifyOtp) },
                enabled = validate(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Verify OTP")
            }

            Spacer(modifier = Modifier.height(4.dp))

                OutlinedButton(
                    onClick = { onEvent(AuthEvent.RegisterAndSendOtp) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = remainingTime == 0
                ) {
                    Text("Resend")
                }
        }
    }
}
