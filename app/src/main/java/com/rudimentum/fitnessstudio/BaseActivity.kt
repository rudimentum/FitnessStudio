package com.rudimentum.fitnessstudio

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.dialog_progress.*

open class BaseActivity : AppCompatActivity() {
    // don't need onCreate() method

    private lateinit var specProgressDialog : Dialog

    fun showErrorSnackBar(message: String, errorMessage: Boolean) {
        val snackBar =
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view

        if (errorMessage) {
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this@BaseActivity,
                    R.color.snack_bar_error
                )
            )
        } else {
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this@BaseActivity,
                    R.color.snack_bar_access
                )
            )
        }

        snackBar.show()
    }

    fun showProgressDialog(text: String) {
        specProgressDialog = Dialog(this)

        specProgressDialog.setContentView(R.layout.dialog_progress)

        specProgressDialog.progressBarText.text = text

        specProgressDialog.setCancelable(false)
        specProgressDialog.setCanceledOnTouchOutside(false)

        specProgressDialog.show()
    }

    fun hideProgressDialog() {
        specProgressDialog.dismiss()
    }
}
