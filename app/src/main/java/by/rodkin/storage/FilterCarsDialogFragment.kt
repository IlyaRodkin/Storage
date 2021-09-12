package by.rodkin.storage

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.LifecycleOwner
import by.rodkin.storage.databinding.DialogFilterCarsBinding

class FilterCarsDialogFragment: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    val dialogBinding = DialogFilterCarsBinding.inflate(layoutInflater)
    val dialog = AlertDialog.Builder(requireContext())
        .setTitle("sort by")
        .setView(dialogBinding.root)
        .setNegativeButton("Close", null)
        .create()

    dialog.setOnShowListener {
        dialogBinding.bSortByModel.setOnClickListener { sortBy(SORT_BY_MODEL) }
        dialogBinding.bSortByYear.setOnClickListener { sortBy(SORT_BY_YEAR) }
        dialogBinding.bSortByPrice.setOnClickListener { sortBy(SORT_BY_PRICE) }
    }

    return dialog
}

    private fun sortBy( sortMode :Int) {
        parentFragmentManager.setFragmentResult(
           REQUEST_KEY, bundleOf(
                KEY_FILTER_RESPONSE to sortMode)
        )
        dismiss()
    }


    companion object {

        fun setupListener(manager: FragmentManager, lifecycleOwner: LifecycleOwner, listener: (Int) -> Unit) {
            manager.setFragmentResultListener(REQUEST_KEY, lifecycleOwner, FragmentResultListener { _, result ->
                listener.invoke(result.getInt(KEY_FILTER_RESPONSE))
            })
        }

        const val SORT_BY_MODEL = 1
        const val SORT_BY_YEAR = 2
        const val SORT_BY_PRICE = 3


        val TAG = FilterCarsDialogFragment::class.java.simpleName
        val REQUEST_KEY = "$TAG:request_key"
        val KEY_FILTER_RESPONSE = "$TAG:filter"

    }
}