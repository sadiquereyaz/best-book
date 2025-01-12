package com.nabssam.bestbook.presentation.ui.account.auth.claud

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nabssam.bestbook.presentation.ui.account.auth.claud.composable.CredentialsStep
import com.nabssam.bestbook.presentation.ui.account.auth.claud.composable.EducationInfoStep
import com.nabssam.bestbook.presentation.ui.account.auth.claud.composable.ExamInfoStep
import com.nabssam.bestbook.presentation.ui.account.auth.claud.composable.LoginStep
import com.nabssam.bestbook.presentation.ui.account.auth.claud.composable.MobileVerificationStep
import com.nabssam.bestbook.presentation.ui.account.auth.claud.util.RegistrationStep

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AuthenticationScreen(
    viewModel: AuthViewModel,
    onRegistrationComplete: () -> Unit,
    onLoginSuccess: () -> Unit,
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.isSignedIn) {
        if (state.isSignedIn) {
            onLoginSuccess()
        }
    }
    
    BackHandler(enabled = state.currentStep != RegistrationStep.LOGIN) {
        viewModel.onEvent(AuthEvent.NavigateBack)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (state.currentStep) {
            RegistrationStep.LOGIN -> LoginStep(
                state = state,
                onEvent = viewModel::onEvent
            )
            RegistrationStep.CREDENTIALS -> CredentialsStep(
                state = state,
                onEvent = viewModel::onEvent
            )
            RegistrationStep.EDUCATION_INFO -> EducationInfoStep(
                state = state,
                onEvent = viewModel::onEvent
            )
            RegistrationStep.EXAM_INFO -> ExamInfoStep(
                state = state,
                onEvent = viewModel::onEvent,
                validate = viewModel::validateCurrentStep
            )
            RegistrationStep.MOBILE_VERIFICATION -> MobileVerificationStep(
                state = state,
                onEvent = viewModel::onEvent
            )
        }

        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }

        state.error?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            )
        }
    }
}