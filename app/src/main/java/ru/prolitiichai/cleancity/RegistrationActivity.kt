package ru.prolitiichai.cleancity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import kotlinx.android.synthetic.main.activity_registration.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.prolitiichai.cleancity.dto.RegistrationDto
import ru.prolitiichai.cleancity.utils.Utils


class RegistrationActivity : AppCompatActivity() {

    fun registrationComplete() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Готово")
            .setMessage("Пользователь успешно зарегестрирован")
            .setCancelable(false)
            .setNegativeButton("Ок") { _, _ ->
                startActivity(Intent(this, MainActivity::class.java))
            }
        builder.create().show()
    }

    fun registration(view: View) {
        val login = loginText.text
        val password = passwordText.text
        val passwordRepeat = passwordRepeatText.text
        if (login.isBlank()) {
            Utils.showError("Необходимо заполнить логин", this)
            return
        }
        if (password.isBlank()) {
            Utils.showError("Необходимо заполнить пароль", this)
            return
        }
        if (password.length < 6) {
            Utils.showError("Слишком слабый пароль (необходимо минимум 6 символов)", this)
            return
        }
        if (password.toString() != passwordRepeat.toString()) {
            Utils.showError("Пароли не совпадают", this)
            return
        }
        App.getApi().registration(RegistrationDto(login.toString(), password.toString()))
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.code() == 400) {
                        Utils.showError("Неверенные данные", this@RegistrationActivity)
                    } else if (response.code() == 200) {
                        registrationComplete()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    System.out.println()
                }
            })
    }

    fun back(view: View) {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_registration)
    }

}