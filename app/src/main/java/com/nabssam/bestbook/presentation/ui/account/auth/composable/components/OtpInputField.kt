package com.nabssam.bestbook.presentation.ui.account.auth.composable.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

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
                }
            }
        }
    )
}