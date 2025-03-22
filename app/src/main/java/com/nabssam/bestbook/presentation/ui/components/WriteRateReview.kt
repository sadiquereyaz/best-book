package com.nabssam.bestbook.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nabssam.bestbook.R

@Composable
fun WriteRateReview(
    modifier: Modifier = Modifier,
    submitReview: () -> Unit = {},
    updateRateReview: (Int, String) -> Unit = {_,_->}
) {
    var starSelectedCount: Int by remember { mutableIntStateOf(0) }
    var reviewText: String by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Card(
        modifier = modifier.padding(vertical = 8.dp, horizontal = 16.dp),

        ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(
                modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 4.dp),
                text = "Rate this book",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

//            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
            ) {
                repeat(5) { it ->
                    IconButton(onClick = { starSelectedCount = it + 1 }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(
                                if ((starSelectedCount ?: 0) > it) R.drawable.star_filled
                                else R.drawable.star
                            ),
                            contentDescription = "Rating Star",
                            modifier = Modifier.size(42.dp),
                            tint = if (it < (starSelectedCount
                                    ?: 0)
                            ) colorResource(R.color.star_yellow) else MaterialTheme.colorScheme.onSurface.copy(
                                alpha = 0.4f
                            )
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    modifier = Modifier
                        .onFocusChanged { focusState ->
                            isFocused = focusState.isFocused
                        }
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    value = reviewText,
                    onValueChange = { reviewText = it },
                    minLines = 1,
                    label = { Text(text = "Write a review", modifier = Modifier) },
                    colors = OutlinedTextFieldDefaults.colors(
                        //focusedBorderColor = colorResource(R.color.star_yellow),
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                    ),
                    shape = RoundedCornerShape(8.dp),
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    )
                )

                OutlinedButton(
                    modifier = Modifier
                        //.align(Alignment.CenterHorizontally)
                        .padding(16.dp), onClick = {
                        focusManager.clearFocus()
                        updateRateReview(starSelectedCount, reviewText)
                        submitReview()
                        starSelectedCount = 0
                        reviewText = ""
                    }, shape = CircleShape,
                    enabled = reviewText.isNotEmpty() && starSelectedCount >0
                ) {
                    Text(
                        text = "Send", modifier = Modifier.padding(horizontal = 24.dp)
                    )
                }
            }

    }
}