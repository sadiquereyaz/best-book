package com.nabssam.bestbook.presentation.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import com.nabssam.bestbook.domain.model.User
import com.nabssam.bestbook.presentation.ui.address.FirebaseRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class ProfileUiState(val name: String = "", val email: String = "")

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {

    private val _profileUiState = MutableStateFlow<ProfileUiState>(ProfileUiState())
    val profileUiState: StateFlow<ProfileUiState>
        get() = _profileUiState

    init {
        fetchProfile()
    }

    private fun fetchProfile() {
        try {
            //val user = firebaseRepo.user()
            val user = User()
            if (user != null) {
                _profileUiState.update { profileState ->
                    profileState.copy(
                        name = user.email?.take(user?.email!!.indexOf("@")) ?: "Display Name", email = user.email ?: ""
                    )
                }
            }
        }catch (e:Exception){
            Log.d("MYDEBUG", e.message.toString())
        }

    }

    fun signOut() {
       // firebaseRepo.signOut()
    }
}