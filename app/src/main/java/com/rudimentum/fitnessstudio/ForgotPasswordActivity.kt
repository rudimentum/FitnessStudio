package com.rudimentum.fitnessstudio

import android.content.Intent
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        setUpActionBar()

        btnResetPassword.setOnClickListener {
            val email = etForgotPasswordEmail.text.toString().trim { it <= ' '}
            if (email.isEmpty()) {
                showErrorSnackBar(
                    resources.getString(R.string.error_msg_email),
                    true)
            } else {
                showProgressDialog(resources.getString(R.string.please_wait))

                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener{task ->

                        hideProgressDialog()

                        if (task.isSuccessful) {
                            startActivity(Intent(this@ForgotPasswordActivity, LoginActivity::class.java))
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
}