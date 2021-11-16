package com.rudimentum.fitnessstudio

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class LoginActivity : Activity() {
        lateinit var userEditEmail : EditText
        lateinit var userEditPass : EditText
        lateinit var signInButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userEditEmail = findViewById(R.id.editTextTextPersonName)
        userEditPass = findViewById(R.id.editTextTextPassword)
        signInButton = findViewById(R.id.signIn)

    }
}