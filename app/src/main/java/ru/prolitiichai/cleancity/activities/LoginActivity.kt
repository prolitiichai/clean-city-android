package ru.prolitiichai.cleancity.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.prolitiichai.cleancity.App
import ru.prolitiichai.cleancity.R
import ru.prolitiichai.cleancity.utils.Utils

class LoginActivity: AppCompatActivity() {

    fun completeLogin() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    fun goRegistration(view: View) {
        startActivity(Intent(this, RegistrationActivity::class.java))
    }

    fun login(view: View) {
        val login = loginText.text.toString()
        val password = passwordText.text.toString()
        if (login.isBlank()) {
            Utils.showError("Необходимо ввести логин", this)
            return
        }
        if (password.isBlank()) {
            Utils.showError("Необходимо ввести пароль", this)
            return
        }
        App.getApi().login(login, password).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.code() == 400 || response.code() == 401) {
                    Utils.showError("Неверенные логин или пароль", this@LoginActivity)
                } else if (response.code() == 200) {
                    completeLogin()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                System.out.println()
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_login)
    }

}