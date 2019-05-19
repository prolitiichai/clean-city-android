package ru.prolitiichai.cleancity.utils

import android.content.Context
import android.support.v7.app.AlertDialog

class Utils {

    companion object {
        fun showDialog(title: String, message: String, context: Context) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setNegativeButton("Ок") { dialog, _ ->
                    dialog.cancel()
                }
            builder.create().show()
        }

        fun showError(error: String, context: Context) {
            showDialog("Ошибка", error, context)
        }

    }

}