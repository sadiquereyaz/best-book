package com.nabssam.bestbook.presentation.ui.account.auth

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nabssam.bestbook.presentation.ui.account.auth.composable.CredentialsStep
import com.nabssam.bestbook.presentation.ui.account.auth.composable.EducationInfoStep
import com.nabssam.bestbook.presentation.ui.account.auth.composable.ErrorText
import com.nabssam.bestbook.presentation.ui.account.auth.composable.ExamInfoStep
import com.nabssam.bestbook.presentation.ui.account.auth.composable.LoginStep
import com.nabssam.bestbook.presentation.ui.account.auth.composable.MobileVerificationStep
import com.nabssam.bestbook.presentation.ui.account.auth.util.AuthSteps

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AuthenticationScreen(
    viewModel: VMAuth,
    onLoginSuccess: () -> Unit,
) {
    val state by viewModel.state.collectAsState()
    val errorState by viewModel.errState.collectAsState()

    LaunchedEffect(state.isSignedIn) {
        if (state.isSignedIn) {
            onLoginSuccess()
        }
    }
   /* LaunchedEffect(state.isOtpVerified) {
        if (state.isOtpVerified) {
            viewModel.onEvent(AuthEvent.NavigateNext)
        }
    }*/

    BackHandler(enabled = state.currentStep != AuthSteps.LOGIN) {
        viewModel.onEvent(AuthEvent.NavigateBack)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (state.currentStep) {
            AuthSteps.LOGIN -> LoginStep(
                state = state,
                onEvent = viewModel::onEvent,
                validate = viewModel::validateCurrentStep
            )

            AuthSteps.CREDENTIALS -> CredentialsStep(
                state = state,
                onEvent = viewModel::onEvent,
                validate = viewModel::validateCurrentStep
            )

            AuthSteps.EDUCATION_INFO -> EducationInfoStep(
                state = state,
                onEvent = viewModel::onEvent,
                validate = viewModel::validateCurrentStep
            )

            AuthSteps.EXAM_INFO -> ExamInfoStep(
                state = state,
                onEvent = viewModel::onEvent,
                validate = viewModel::validateCurrentStep
            )

            AuthSteps.MOBILE_VERIFICATION -> MobileVerificationStep(
                state = state,
                onEvent = viewModel::onEvent,
                validate = viewModel::validateCurrentStep
            )
        }

        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
        ErrorText(
            error=errorState,
            modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(16.dp)
        )
    }
}