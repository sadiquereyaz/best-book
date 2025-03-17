package com.nabssam.bestbook.presentation.ui.address

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import com.nabssam.bestbook.presentation.ui.components.GradientButton
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.nabssam.bestbook.R
import com.nabssam.bestbook.presentation.ui.address.components.AddressRadio
import com.nabssam.bestbook.presentation.ui.address.components.FormScreen
import com.nabssam.bestbook.presentation.ui.components.ErrorView
import com.nabssam.bestbook.presentation.ui.components.VerticalSpacer

@Composable
fun AddressScreen(
    modifier: Modifier = Modifier,
    navigateToSummary: (Int) -> Unit,
    uiState: UiStateAddress,
    onEvent: (EventAddressScreen) -> Unit,
) {
    Box(modifier = modifier.fillMaxSize()) {
        when (uiState) {
            is UiStateAddress.Error -> {
                ErrorView(
                    message = uiState.message,
                    onRetry = { onEvent(EventAddressScreen.LoadAddress) }
                )
            }

            is UiStateAddress.Loading -> CircularProgressIndicator(
                modifier = Modifier.align(
                    Alignment.Center
                )
            )

            is UiStateAddress.Success -> {
                var fieldVisible by rememberSaveable { mutableStateOf(false) }
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    // add address btn
                    TextButton(
                        modifier = Modifier
                            .animateContentSize(),
                        onClick = {
                            fieldVisible = !fieldVisible
                            onEvent(EventAddressScreen.ToggleAddressFields(fieldVisible))
                        }) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(
                                    id = if (fieldVisible) R.drawable.history else R.drawable.add_home
                                ),
                                "address icon"
                            )
                            Text(text = if (fieldVisible) "Show Saved Address" else "Add New Address")
                        }
                    }

                    when (uiState) {

                        is UiStateAddress.Success.Addresses -> {
                            val (selectedOption, onOptionSelected) = remember {
                                mutableStateOf<String?>(
                                    null
                                )
                            }
                            uiState.addresses.forEach { address ->
                                AddressRadio(
                                    Modifier.fillMaxWidth(),
                                    address,
                                    selectedOption,
                                    onOptionSelected
                                ) {
                                    onEvent(EventAddressScreen.DeleteAddress(address._id))
                                }
                            }
                            VerticalSpacer(18)
                            // checkout btn
                            GradientButton(
                                enabled = selectedOption != null,
                                onClick = {
                                    navigateToSummary(40)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(18.dp)
                            ) {
                                Text("Checkout")
                            }
                        }

                        is UiStateAddress.Success.Form -> {
                            AnimatedVisibility(fieldVisible) {
                                FormScreen(
                                    modifier = Modifier.fillMaxWidth(),
                                    formState = uiState,
                                    onFieldUpdated = { field, value ->
                                        onEvent(EventAddressScreen.UpdateFormField(field, value))
                                    },
                                    onSubmit = {
                                        onEvent(EventAddressScreen.SubmitForm)
                                    }
                                )
                            }
                        }
                    }

                }
            }
        }
    }
}