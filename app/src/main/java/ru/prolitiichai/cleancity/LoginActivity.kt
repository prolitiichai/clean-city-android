package ru.prolitiichai.cleancity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window

class LoginActivity: AppCompatActivity() {

    fun login(view: View) {
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_login)
    }

}