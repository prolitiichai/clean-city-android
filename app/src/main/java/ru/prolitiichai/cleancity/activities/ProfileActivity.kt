package ru.prolitiichai.cleancity.activities

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.prolitiichai.cleancity.App
import ru.prolitiichai.cleancity.dto.User
import ru.prolitiichai.cleancity.R

class ProfileActivity : Fragment() {

    companion object {

        @JvmStatic
        fun newInstance() =
            ProfileActivity().apply {
                arguments = Bundle().apply {
                    // putString(ARG_PARAM1, param1)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            // param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_profile, container, false)

        App.getApi().getUserData().enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.code() == 200) {
                    response.body()?.let {
                        nameText.text = it.nickName
                        loginText.text = it.nickName
                        findedText.text = it.trashFound.toString()
                        karmaText.text = it.karma.toString()
                        cleanedText.text = it.trashCleaned.toString()
                        if (it.avatarAddress.isNotBlank()) {
                            Picasso.get().load("http://46.146.211.12:25567${it.avatarAddress}").into(imageView)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<User>, t: Throwable) {
                System.out.println()
            }
        })

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }


}