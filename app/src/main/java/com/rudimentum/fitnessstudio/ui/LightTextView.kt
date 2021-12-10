package com.rudimentum.fitnessstudio.ui

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView


class LightTextView(context: Context, attributeSet: AttributeSet)
    : AppCompatTextView(context, attributeSet) {

    init {
        applyFont()
    }

    private fun applyFont() {
        val typeface : Typeface =
            Typeface.createFromAsset(context.assets, "Oswald-Light.ttf")
        setTypeface(typeface)
    }
}
