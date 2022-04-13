package com.rudimentum.fitnessstudio.firestore

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.rudimentum.fitnessstudio.R
import com.rudimentum.fitnessstudio.RegisterActivity
import com.rudimentum.fitnessstudio.models.User

class FirestoreClass {

    private val mFireStoreClass = FirebaseFirestore.getInstance()

    fun registerUser(activity: RegisterActivity, userInfo: User) {
        mFireStoreClass.collection("users")
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


}