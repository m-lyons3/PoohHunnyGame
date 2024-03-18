package com.zybooks.poohhunnygame
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class StartingGameDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?)
            : Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(R.string.gameStartTitle)
        builder.setMessage(R.string.gameInstructions)
        builder.setPositiveButton(R.string.ok, null)
        return builder.create()
    }
}