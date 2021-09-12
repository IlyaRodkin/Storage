package by.rodkin.storage.fragments

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.LifecycleOwner
import by.rodkin.storage.dbRoom.Car
import by.rodkin.storage.databinding.DialogAddCarBinding

class AddCarDialogFragment : DialogFragment() {

    //private var car: Car? = requireArguments().get(ARG_CAR) as Car

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBinding = DialogAddCarBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .setPositiveButton("Ok", null)
            .setNegativeButton("Close", null)
            .create()

        var car : Car? = null
        if(requireArguments().get(ARG_CAR) != null){
            car = requireArguments().get(ARG_CAR) as Car
            dialogBinding.etCarModel.setText(car.model)
            dialogBinding.etCarYear.setText(car.year.toString())
            dialogBinding.etCarPrice.setText(car.price.toString())
        }

        dialog.setOnShowListener {
            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {

                val model = dialogBinding.etCarModel.text.toString()
                if (model.isBlank()) dialogBinding.etCarModel.error = "is empty"
                else car?.model = model

                val year = dialogBinding.etCarYear.text.toString()
                if (year.isBlank()) dialogBinding.etCarYear.error = "is empty"
                else if (year.toInt() < 1900 || year.toInt() > 2021){
                    dialogBinding.etCarYear.error = "incorrect year"
                    return@setOnClickListener
                }
                else car?.year = year.toInt()

                val price = dialogBinding.etCarPrice.text.toString()
                if (price.isBlank()) dialogBinding.etCarPrice.error = "is empty"
                else car?.price = price.toInt()

                if (price.isBlank() || year.isBlank() || model.isBlank()) return@setOnClickListener

                if(car == null) car = Car(model,year.toInt(),price.toInt())

                parentFragmentManager.setFragmentResult(REQUEST_KEY, bundleOf(KEY_CAR_RESPONSE to car))

                dismiss()
            }
        }
        return dialog
    }


    companion object {

        fun show(manager: FragmentManager, car: Car?) {
            val dialogFragment = AddCarDialogFragment()
            dialogFragment.arguments = bundleOf(ARG_CAR to car)
            dialogFragment.show(manager, TAG)
        }

        fun setupListener(manager: FragmentManager, lifecycleOwner: LifecycleOwner, listener: (Parcelable) -> Unit) {
            manager.setFragmentResultListener(REQUEST_KEY, lifecycleOwner, FragmentResultListener { _, result ->
                listener.invoke(result.getParcelable<Car>(KEY_CAR_RESPONSE)!!)
            })
        }
        const val ARG_CAR = "arg_car"
        val TAG = AddCarDialogFragment::class.java.simpleName
        val REQUEST_KEY = "$TAG:request_key"
        val KEY_CAR_RESPONSE = "$TAG:car"

    }
}