package com.rudimentum.fitnessstudio

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.rudimentum.fitnessstudio.firestore.FirestoreClass
import com.rudimentum.fitnessstudio.models.User
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        forgotPassword.setOnClickListener(this)
        registerOfferLink.setOnClickListener(this)
        btnLogin.setOnClickListener(this)
    }

    fun userLoggedSuccess(user: User) {
        hideProgressDialog()

        // Print details in log
        Log.i("First name: ", user.firstName)
        Log.i("Last name: ", user.lastName)
        Log.i("Email: ", user.email)

        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        finish()
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.forgotPassword -> {
                    startActivity(Intent(this@LoginActivity, ForgotPasswordActivity::class.java))

                }
                R.id.btnLogin -> {
                    loginUser()
                }
                R.id.registerOfferLink -> {
                    startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
                }
            }
        }
    }


    private fun validateLoginDetails(): Boolean {
        return when {
            TextUtils.isEmpty(editTextLoginEmail.text.toString().trim { it <= ' '} ) -> {
                showErrorSnackBar(resources.getString(R.string.error_msg_email), true)
                false
            }
            TextUtils.isEmpty(editTextLoginPassword.text.toString().trim { it <= ' '} ) -> {
                showErrorSnackBar(resources.getString(R.string.error_msg_password), true)
                false
            }
            else -> {
                true
            }
        }
    }

    private fun loginUser() {
        if (validateLoginDetails()) {

            showProgressDialog(resources.getString(R.string.please_wait))

            val email = editTextLoginEmail.text.toString().trim { it <= ' '}
            val password = editTextLoginPassword.text.toString().trim { it <= ' '}

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {
                        FirestoreClass().getUserDetails(this@LoginActivity)
                        /*
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)

                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        intent.putExtra("user_id", FirebaseAuth.getInstance().currentUser!!.uid)
                        intent.putExtra("email_id", email)
                        startActivity(intent)
                        finish()
                         */
                    } else {
                        hideProgressDialog()
                        showErrorSnackBar(
                            task.exception!!.message.toString(),
                            true)
                    }
                }
        }
    }
}
