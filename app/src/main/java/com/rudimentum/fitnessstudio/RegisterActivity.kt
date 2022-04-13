package com.rudimentum.fitnessstudio

import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseUser
import com.rudimentum.fitnessstudio.firestore.FirestoreClass
import com.rudimentum.fitnessstudio.models.User
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.dialog_progress.*

class RegisterActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setUpActionBar()

        signInOfferLink.setOnClickListener {
            onBackPressed()
        }

        // Assign a click event to the register button and call the validate function
        btnRegister.setOnClickListener {
            registerUser()
        }
    }

    /**
     * A function for actionBar Setup.
     */
    private fun setUpActionBar() {
        setSupportActionBar(toolbarRegister)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
        }

        toolbarRegister.setNavigationOnClickListener { onBackPressed() }
    }

    /**
     * A function to validate the entries of a new user.
     */
    private fun validateRegisterDetails(): Boolean {
        return when {
            TextUtils.isEmpty(etFirstName.text.toString().trim { it <= ' '} ) -> {
                showErrorSnackBar(resources.getString(R.string.error_msg_first_name), true)
                false
            }
            TextUtils.isEmpty(etLastName.text.toString().trim { it <= ' '} ) -> {
                showErrorSnackBar(resources.getString(R.string.error_msg_last_name), true)
                false
            }
            TextUtils.isEmpty(etEmail.text.toString().trim { it <= ' '} ) -> {
                showErrorSnackBar(resources.getString(R.string.error_msg_email), true)
                false
            }
            TextUtils.isEmpty(etPassword.text.toString().trim { it <= ' '} ) -> {
                showErrorSnackBar(resources.getString(R.string.error_msg_password), true)
                false
            }
            TextUtils.isEmpty(etConfirmPassword.text.toString().trim { it <= ' '} ) -> {
                showErrorSnackBar(resources.getString(R.string.error_msg_confirm_password), true)
                false
            }
            etPassword.text.toString().trim { it <= ' '} !=
                    etConfirmPassword.text.toString().trim { it <= ' '} -> {
                showErrorSnackBar(resources.getString(R.string.error_msg_mismatch_passwords), true)
                false
            }
            !cbTermsCondition.isChecked -> {
                showErrorSnackBar(resources.getString(R.string.error_msg_agree_terms_condition), true)
                false
            }
            else -> {
                true
            }
        }
    }

    private fun registerUser() {
        if (validateRegisterDetails()) {
            showProgressDialog(resources.getString(R.string.please_wait))

            val firstName = etFirstName.text.toString().trim { it <= ' ' }
            val lastName = etLastName.text.toString().trim { it <= ' ' }
            val name = "$firstName $lastName"
            val email = etEmail.text.toString().trim { it <= ' ' }
            val password = etPassword.text.toString().trim { it <= ' ' }

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->

                    if (task.isSuccessful) {
                        val firebaseUser: FirebaseUser = task.result!!.user!!

                        val user = User(
                            firebaseUser.uid,
                            firstName,
                            lastName,
                            email
                        )

                        FirestoreClass().registerUser(this@RegisterActivity, user)

                        val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        intent.putExtra("user_id", firebaseUser.uid)
                        intent.putExtra("email_id", email)
                        startActivity(intent)
                        finish()
                    } else {
                        hideProgressDialog()
                        showErrorSnackBar(task.exception!!.message.toString(), true)
                    }
                }
        }
    }

    fun userRegistrationSuccess() {
        hideProgressDialog()

        Toast.makeText(
            this@RegisterActivity,
            resources.getString(R.string.register_successful),
            Toast.LENGTH_SHORT
        ).show()

        finish()
    }
}
