package com.recio.esciencetestapp.activities.splash

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.recio.esciencetestapp.R
import com.recio.esciencetestapp.activities.baseactivity.BaseActivity
import com.recio.esciencetestapp.activities.mainmenu.MainMenuActivity
import com.recio.esciencetestapp.databinding.ActivitySplashBinding
import com.squareup.picasso.Picasso

class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    override fun getActivityBinding(): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showNetworkConnectionStatusSnackBar()

        if (!checkDarkMode(this)) {
            Picasso.get().load(R.drawable.ic_app_logo_black).into(binding.icLogo)
        } else {
            Picasso.get().load(R.drawable.ic_app_logo_white).into(binding.icLogo)
        }

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainMenuActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }, 3000)
    }

    private fun checkDarkMode(context: Context): Boolean {
        val darkModeFlag = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return darkModeFlag == Configuration.UI_MODE_NIGHT_YES
    }
}