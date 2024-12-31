package com.nabssam.bestbook.auth

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FirebaseAuth {

    private val auth = Firebase.auth
    private val TAG = "FirebaseAuth"

    fun isUserAlreadyLoggedIn(): Boolean {
        return auth.currentUser != null

    }


    fun signUp(
        email: String,
        password: String,
        context: Context,
        onComplete: (Task<AuthResult>) -> Unit,
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                onComplete(task) // Pass the task to the callback
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    // Display error message to the user using Toast or Snackbar
                    Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun signIn(email: String, password: String, context: Context ,onComplete: (Boolean) -> Unit,) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true)
                    Log.d(TAG, "Login:success")
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    // Display error message to the user using Toast or Snackbar
                    Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun signOut() {
        auth.signOut()
    }

}