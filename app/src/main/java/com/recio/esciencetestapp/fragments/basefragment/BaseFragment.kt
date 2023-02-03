package com.recio.esciencetestapp.fragments.basefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.recio.esciencetestapp.R
import com.recio.esciencetestapp.classes.CheckNetworkConnection

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment <FVBinding: ViewBinding>(private val inflate: Inflate<FVBinding>) : Fragment() {

    private var _binding: FVBinding? = null
    protected val binding get() = _binding!!

    protected var networkConnectionStatus = false
    private var isNoNetworkSnackBarRecentlyDisplayed = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    protected fun showNetworkConnectionStatusSnackBar() {
        val networkConnection = context?.let { CheckNetworkConnection(it) }
        networkConnection?.observe(this) { isConnected ->
            networkConnectionStatus = isConnected
            if (isConnected) {
                if (isNoNetworkSnackBarRecentlyDisplayed) {
                    displaySnackBar(
                        getString(R.string.network_detected_message),
                        Snackbar.LENGTH_LONG,
                        ""
                    )
                    isNoNetworkSnackBarRecentlyDisplayed = false
                }
            } else {
                displaySnackBar(
                    getString(R.string.no_network_connection_message),
                    Snackbar.LENGTH_INDEFINITE,
                    getString(R.string.dismiss)
                )
                isNoNetworkSnackBarRecentlyDisplayed = true
            }
        }
    }

    protected fun displaySnackBar(message: String, duration: Int, action: String) {
        val snackBar: Snackbar = Snackbar.make(binding.root, "", Snackbar.LENGTH_INDEFINITE)
        snackBar.setText(message)
        snackBar.duration = duration
        snackBar.setAction(action){}
        snackBar.show()
    }

    protected fun displayDialogBoxMessage(dialogTitle: String, dialogMessage: String, positiveButtonLabel: String) {
        val builder = context?.let { AlertDialog.Builder(it) }
        builder?.setTitle(dialogTitle)
        builder?.setMessage(dialogMessage)
        builder?.setPositiveButton(positiveButtonLabel) { dialog, _ ->
            dialog.dismiss()
        }
        builder?.show()
    }

    protected fun displayToastMessage(message: String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}