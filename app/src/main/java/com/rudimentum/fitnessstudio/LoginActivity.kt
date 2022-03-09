package com.rudimentum.fitnessstudio

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.google.firebase.auth.FirebaseAuth
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

                    hideProgressDialog()

                    if (task.isSuccessful) {
                        showErrorSnackBar(
                            resources.getString(R.string.sign_in_successful),
                            false)

                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        // rid of stack
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        intent.putExtra("user_id", FirebaseAuth.getInstance().currentUser!!.uid)
                        intent.putExtra("email_id", email)
                        startActivity(intent)
                        finish()
                    } else {
                        showErrorSnackBar(
                            task.exception!!.message.toString(),
                            true)
                    }
                }
        }
    }
}
