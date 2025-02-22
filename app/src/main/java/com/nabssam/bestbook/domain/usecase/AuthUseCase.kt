package com.nabssam.bestbook.domain.usecase


import com.nabssam.bestbook.domain.model.Unit1
import com.nabssam.bestbook.domain.repository.AuthRepositoryOld
import com.nabssam.bestbook.utils.ValidationException
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepositoryOld
) {
    suspend operator fun invoke(email: String, password: String): Unit1 {
        validateLoginInput(email, password)
        return repository.login(email, password)
    }

    private fun validateLoginInput(email: String, password: String) {
        if (email.isBlank()) {
            throw ValidationException("Email cannot be empty")
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            throw ValidationException("Invalid email format")
        }
        if (password.length < 6) {
            throw ValidationException("Password must be at least 6 characters")
        }
    }
}

class SignUpUseCase @Inject constructor(
    private val repository: AuthRepositoryOld
) {
    suspend operator fun invoke(email: String, password: String, name: String): Unit1 {
        validateSignUpInput(email, password, name)
        return repository.signUp(email, password, name)
    }

    private fun validateSignUpInput(email: String, password: String, name: String) {
        if (name.isBlank()) {
            throw ValidationException("Name cannot be empty")
        }
        if (email.isBlank()) {
            throw ValidationException("Email cannot be empty")
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            throw ValidationException("Invalid email format")
        }
        if (password.length < 6) {
            throw ValidationException("Password must be at least 6 characters")
        }
    }
}

class LogoutUseCase @Inject constructor(
    private val repository: AuthRepositoryOld
) {
    suspend operator fun invoke() {
        repository.logout()
    }
}

class GetCurrentUserUseCase @Inject constructor(
    private val repository: AuthRepositoryOld
) {
    operator fun invoke(): Flow<Unit1?> {
        return repository.getCurrentUser()
    }
}