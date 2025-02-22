package com.nabssam.bestbook.presentation.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import com.nabssam.bestbook.domain.model.Unit1
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
            val userOld = Unit1()
            if (userOld != null) {
                _profileUiState.update { profileState ->
                    profileState.copy(
                        name = userOld.email?.take(userOld?.email!!.indexOf("@")) ?: "Display Name", email = userOld.email ?: ""
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