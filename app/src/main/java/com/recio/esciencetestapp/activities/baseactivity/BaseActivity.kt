package com.recio.esciencetestapp.activities.baseactivity

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.recio.esciencetestapp.R
import com.recio.esciencetestapp.classes.CheckNetworkConnection

abstract class BaseActivity <AVBinding : ViewBinding> : AppCompatActivity() {

    protected lateinit var binding: AVBinding
    protected var networkConnectionStatus = false
    private var isNoNetworkSnackBarRecentlyDisplayed = false

    protected abstract fun getActivityBinding(): AVBinding

    protected fun initializeActivityViewBinding() {
        binding = getActivityBinding()
        setContentView(binding.root)
    }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeActivityViewBinding()
        supportActionBar?.hide()
    }

    protected fun showNetworkConnectionStatusSnackBar() {
        val networkConnection = CheckNetworkConnection(this)
        networkConnection.observe(this) { isConnected ->
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
        snackBar.setAction(action) {}
        snackBar.show()
    }

    protected fun displayDialogBoxMessage(
        dialogTitle: String,
        dialogMessage: String,
        positiveButtonLabel: String
    ) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(dialogTitle)
        builder.setMessage(dialogMessage)
        builder.setPositiveButton(positiveButtonLabel) { dialog, _ ->
            dialog.dismiss()
        }
        builder.setCancelable(false)
        builder.show()
    }

    protected fun displayToastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

//    protected fun showFullImage(image: String) {
//        val intent = Intent(this, ViewImageActivity::class.java)
//        intent.putExtra("imageUrl", image)
//        startActivity(intent)
//    }

}