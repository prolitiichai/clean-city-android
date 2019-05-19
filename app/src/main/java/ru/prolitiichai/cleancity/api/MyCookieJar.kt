package ru.prolitiichai.cleancity.api

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

class MyCookieJar : CookieJar {

    private var cookies: MutableList<Cookie> = ArrayList()

    override fun saveFromResponse(url: HttpUrl, cookies: MutableList<Cookie>) {
        this.cookies = cookies
    }

    override fun loadForRequest(url: HttpUrl): MutableList<Cookie> {
        return cookies
    }
}