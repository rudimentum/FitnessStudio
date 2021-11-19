package com.rudimentum.fitnessstudio

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class LoginActivity : Activity() {
        lateinit var userEmail : EditText
        lateinit var userPass : EditText
        lateinit var signInButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)
    }
}