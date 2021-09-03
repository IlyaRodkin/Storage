package by.rodkin.storage

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import by.rodkin.storage.databinding.DialogAddCarBinding

class AddCarDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBinding = DialogAddCarBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext())
            .setCancelable(true)
            .setView(dialogBinding.root)
            .setPositiveButton("Ok", null)
            .setNegativeButton("Close", null)
            .create()

        dialog.setOnShowListener {
            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {

                val model = dialogBinding.etCarModel.text.toString()
                if (model.isBlank()) dialogBinding.etCarModel.error = "is empty"

                val year = dialogBinding.etCarYear.text.toString()
                if (year.isBlank()) dialogBinding.etCarYear.error = "is empty"
                else if (year.toInt() < 1900 || year.toInt() > 2021) dialogBinding.etCarYear.error = "incorrect year"

                val price = dialogBinding.etCarPrice.text.toString()
                if (price.isBlank()) dialogBinding.etCarPrice.error = "is empty"

                if (price.isBlank() || year.isBlank() || model.isBlank()) return@setOnClickListener

                dismiss()
            }
        }
        return dialog
    }

    companion object {
        @JvmStatic
        val TAG = AddCarDialogFragment::class.java.simpleName

        @JvmStatic
        val KEY_MODEL = "$TAG:model"

        @JvmStatic
        val KEY_YEAR = "$TAG:year"

        @JvmStatic
        val KEY_PRICE = "$TAG:price"

        @JvmStatic
        val KEY_RESPONSE = "RESPONSE"

    }
}