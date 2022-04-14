package com.rudimentum.fitnessstudio.firestore

import android.app.Activity
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.rudimentum.fitnessstudio.LoginActivity
import com.rudimentum.fitnessstudio.R
import com.rudimentum.fitnessstudio.RegisterActivity
import com.rudimentum.fitnessstudio.models.User
import com.rudimentum.fitnessstudio.utils.Constants
import kotlin.math.acos

class FirestoreClass {

    private val mFireStoreClass = FirebaseFirestore.getInstance()

    fun registerUser(activity: RegisterActivity, userInfo: User) {
        mFireStoreClass.collection(Constants.USERS)
            // Document ID for users fields. Here the document it is the User ID
            .document(userInfo.personId)
            // Here the userInfo are Field and the SetOption is set to merge. It is for if we wants to merge later on instead of replacing the fields
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                // Here call a function of base activity for transferring the result to it
                activity.userRegistrationSuccess()
            }
            .addOnFailureListener { e ->
                activity.hideProgressDialog()
                Log.e(
                    activity.javaClass.simpleName,
                    // why resourses don't work
                    "Error",
                    e
                )
            }
    }

    fun getCurrentUserID(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserID = ""
        if (currentUser != null)
            currentUserID = currentUser.uid

        return currentUserID
    }

    fun getUserDetails(activity: Activity) {
        mFireStoreClass.collection(Constants.USERS)
            .document(getCurrentUserID())
            .get()
            .addOnSuccessListener { document ->
                Log.i(activity.javaClass.simpleName, document.toString())

                // We have received the document snapshot
                val user = document.toObject(User::class.java)!!

                // Pass the result to the LoginActivity
                when (activity) {
                    is LoginActivity -> {
                        activity.userLoggedSuccess(user)
                    }
                }
            }
            .addOnFailureListener { e ->
                // Hide the progress dialog if there is any error and print the error in log
                when (activity) {
                    is LoginActivity -> {
                        activity.hideProgressDialog()
                    }
                }
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while getting user details",
                    e)
                }

            }
    }

}