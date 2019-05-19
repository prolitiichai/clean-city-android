package ru.prolitiichai.cleancity.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import kotlinx.android.synthetic.main.activity_add_point.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.prolitiichai.cleancity.App
import ru.prolitiichai.cleancity.R
import ru.prolitiichai.cleancity.dto.PointDto
import ru.prolitiichai.cleancity.utils.Utils

class AddPointActivity : AppCompatActivity() {

    var latitude: Double? = null
    var longitude: Double? = null

    fun addNewPoint(view: View) {
        val pointNameText = pointName.text.toString()
        App.getApi().createPoint(PointDto(pointNameText, 3, latitude?: 0.0, longitude?: 0.0)).enqueue(object :
            Callback<Long> {
            override fun onResponse(call: Call<Long>, response: Response<Long>) {
                if (response.code() == 200) {
                    val builder = AlertDialog.Builder(this@AddPointActivity)
                    builder.setTitle("Готово")
                        .setMessage("Точка успешно добавлена")
                        .setCancelable(false)
                        .setNegativeButton("Ок") { _, _ ->
                            startActivity(Intent(this@AddPointActivity, MainActivity::class.java))
                        }
                    builder.create().show()
                } else {
                    Utils.showError("Вас постигла неудача.", this@AddPointActivity)
                }
            }

            override fun onFailure(call: Call<Long>, t: Throwable) {
                System.out.println()
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_add_point)
        latitude = intent.getDoubleExtra("latitude", 0.0)
        longitude = intent.getDoubleExtra("longitude", 0.0)
    }

    fun goToMap(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}