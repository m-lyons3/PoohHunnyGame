package com.zybooks.poohhunnygame

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class WinningDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?)
            : Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(R.string.win)
        builder.setMessage(R.string.winMessage)
        builder.setPositiveButton(R.string.playAgain, null)
        return builder.create()
    }
}