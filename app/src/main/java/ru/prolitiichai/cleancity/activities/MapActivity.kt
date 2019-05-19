package ru.prolitiichai.cleancity.activities

import android.Manifest
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKit
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point as NoMyPoint
import com.yandex.mapkit.location.Location
import com.yandex.mapkit.location.LocationListener
import com.yandex.mapkit.location.LocationStatus
import com.yandex.mapkit.map.CameraPosition
import kotlinx.android.synthetic.main.activity_map.*
import android.content.pm.PackageManager
import android.R
import android.content.Context
import android.content.Intent
import android.support.v4.content.PermissionChecker.checkSelfPermission
import android.support.v7.app.AlertDialog
import com.yandex.runtime.image.ImageProvider
import com.yandex.runtime.ui_view.ViewProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.prolitiichai.cleancity.App
import ru.prolitiichai.cleancity.dto.Point
import ru.prolitiichai.cleancity.dto.PointSearchSquareDto
import ru.prolitiichai.cleancity.utils.Utils
import java.time.Instant
import java.util.*
import kotlin.collections.ArrayList


class MapActivity : Fragment(){

    var points = ArrayList<Point>()

    companion object {

        @JvmStatic
        fun newInstance() =
            MapActivity().apply {
                arguments = Bundle().apply {
                    // putString(ARG_PARAM1, param1)
                }
            }

    }

    fun addPoint(view: View) {
        val intent = Intent(context!!, AddPointActivity::class.java)
        intent.putExtra("latitude", mapview.map.cameraPosition.target.latitude)
        intent.putExtra("longitude", mapview.map.cameraPosition.target.longitude)
        startActivity(intent)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            // param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment'
        MapKitFactory.setApiKey("d090c123-3d41-47b2-84bd-08e83f6bd503")
        MapKitFactory.initialize(activity)
        val view = inflater.inflate(ru.prolitiichai.cleancity.R.layout.activity_map, container, false)
        return view
    }

    private fun hasPermission(perm: String, context: Context): Boolean {
        return PackageManager.PERMISSION_GRANTED == checkSelfPermission(context, perm)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mapview.map.move(
            CameraPosition(
                NoMyPoint(57.988740, 56.206570), 19.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 0f), null
        )
        floatingActionButton2.setOnClickListener { view ->
            addPoint(view)
        }
        var topLeft = mapview.map.visibleRegion.topLeft
        var bottomRight = mapview.map.visibleRegion.bottomRight
        App.getApi().findBySquare(PointSearchSquareDto(topLeft.latitude, topLeft.longitude, bottomRight.latitude, bottomRight.longitude)).enqueue(object :
            Callback<List<Point>> {
            override fun onResponse(call: Call<List<Point>>, response: Response<List<Point>>) {
                if (response.code() == 200) {
                    points.addAll(response.body()?: ArrayList())
                    for (point in points) {
                        mapview.map.mapObjects.addPlacemark(NoMyPoint(point.latitude, point.longitude))
                    }
                } else {

                }
            }

            override fun onFailure(call: Call<List<Point>>, t: Throwable) {
                System.out.println()
            }
        })
    }

    private fun doLocationThing() {
        var locationManager = MapKitFactory.getInstance().createLocationManager()
        locationManager.requestSingleUpdate(object : LocationListener {
            override fun onLocationStatusUpdated(p0: LocationStatus) {

            }

            override fun onLocationUpdated(p0: Location) {
                mapview.map.move(
                    CameraPosition(
                        p0.position, 11.0f, 0.0f, 0.0f),
                    Animation(Animation.Type.SMOOTH, 0f), null
                )
            }

        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            requestCode -> {

                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Utils.showError("Вы не разрешили приложению работать :(", context!!)
                } else {
                    doLocationThing()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        mapview.onStart()
        MapKitFactory.getInstance().onStart()
    }
}