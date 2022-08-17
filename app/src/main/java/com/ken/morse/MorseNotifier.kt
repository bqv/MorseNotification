package com.ken.morse

import android.content.Context
import android.os.Vibrator
import com.ken.morse.encoder.EncodeResult
import com.ken.morse.encoder.MorseEncoder

/**
 * Handles playing notifications as morse code.
 */
class MorseNotifier(context: Context) {
    private val mVibrator: Vibrator
    private val converter = MorseEncoder()
    fun notifyText(text: String?): EncodeResult {
        val unitSignalDurationMs: Long = 100
        val encoded = converter.encode(text!!)
        mVibrator.vibrate(encoded.toDuration(unitSignalDurationMs), -1)
        return encoded
    }

    init {
        mVibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }
}