package com.imageclasses.imageclasses.controllers

import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.imageclasses.imageclasses.modals.User

class RealtimeDb {
    private lateinit var dbRef: DatabaseReference

    fun writeData(
        email: String,
        phoneNumber: String,
        classname: String,
        entranceExam: String,
        targetYear: String,
        onSuccess:(Boolean)->Unit
    ) {
        dbRef = Firebase.database.reference

        val user = User(
            email = email,
            phoneNumber = phoneNumber,
            class_ = classname,
            exam = entranceExam,
            targetYear = targetYear.toInt()
        )

        dbRef.child("users").push().setValue(user).addOnCompleteListener { task->
            if(task.isSuccessful){
                onSuccess(true)
                Log.d("writeData", "writeData: data added successfullt")
            }
            else{
                onSuccess(false)
                Log.e("writeData", "writeData: some error occured")
            }
        }
    }

    fun readData(): User {
        dbRef = Firebase.database.reference
        val user = User()

        dbRef.child("users").get().addOnSuccessListener {
            Log.d("userDetails", "readData: ${it.getValue()}")
            user.email = it.child("email").value.toString()
            user.phoneNumber = it.child("phoneNumber").value.toString()
            user.exam = it.child("exam").value.toString()
            user.class_ = it.child("class_").value.toString()
            user.targetYear = it.child("targetYear").value.toString().toInt()
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }

        return  user
    }
}