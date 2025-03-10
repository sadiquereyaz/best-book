package com.nabssam.bestbook.presentation.ui.account.auth.composable

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nabssam.bestbook.R
import com.nabssam.bestbook.presentation.ui.account.auth.AuthEvent
import com.nabssam.bestbook.presentation.ui.account.auth.AuthState
import com.nabssam.bestbook.utils.showToastL

@Composable
fun MobileVerificationStep(
    state: AuthState,
    onEvent: (AuthEvent) -> Unit,
    validate: () -> Boolean,
    context: Context
) {
    var remainingTime by remember { mutableIntStateOf(R.string.otp_resend_time) } // 4 min
    LaunchedEffect(state.isOtpSent) {
        if (state.isOtpSent) {
            remainingTime = R.string.otp_resend_time // Reset the timer when OTP is sent
            while (remainingTime > 0) {
                kotlinx.coroutines.delay(1000)
                remainingTime--
            }
        }
    }
    LaunchedEffect(state.isOtpVerified){
        Log.d("TAG", "MobileVerificationStep: ${state.isOtpVerified}")
        if (state.isOtpVerified){
            Toast.makeText(context, "Account Created Successfully", Toast.LENGTH_SHORT).show()
//            showToastL(context, "Account Created Successfully")
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp, horizontal = 38.dp),
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
                onValueChange = {
                    if (state.mobileNumber.length < 11) onEvent(
                        AuthEvent.UpdateMobile(
                            it
                        )
                    )
                },
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
            OtpInputField(
                otp = state.otp,
                onValueChange = { onEvent(AuthEvent.UpdateOtp(it)) }
            )

            //timer
            val minutes = remainingTime / 60
            val seconds = remainingTime % 60
            Text(
                text = if (remainingTime > 0) "Resend in $minutes:${
                    seconds.toString().padStart(2, '0')
                }" else "Enter OTP sent to ${state.mobileNumber}",
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .padding(top = 6.dp)
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
                onClick = {
                    remainingTime = R.string.otp_resend_time
                    onEvent(AuthEvent.RegisterAndSendOtp)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = remainingTime == 0
            ) {
                Text("Resend")
            }
        }
    }
}


@Composable
fun OtpInputField(
    modifier: Modifier = Modifier,
    otp: String,
    onValueChange: (String) -> Unit
) {
//    var otp  by remember { mutableStateOf("") }
    BasicTextField(
        modifier = modifier,
        value = otp,
        onValueChange = { newValue ->
            if (newValue.length <= 4)
                onValueChange(newValue)
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        decorationBox = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                repeat(4) { index ->
                    val char = when {
                        index >= otp.length -> ""  // Empty if index exceeds otp length
                        else -> otp[index].toString()  // Extract the character from OTP value
                    }

                    Text(
                        modifier = Modifier
                            .width(48.dp)
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.outline,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp),
                        text = char,
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center
                    )

                    if (index < 5) {
                        //Spacer(modifier = Modifier.width(8.dp)) // Add spacing between boxes
                    }
                }
            }
        }
    )
}
