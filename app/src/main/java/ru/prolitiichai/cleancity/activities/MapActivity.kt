package ru.prolitiichai.cleancity.activities

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKit
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.location.Location
import com.yandex.mapkit.location.LocationListener
import com.yandex.mapkit.location.LocationStatus
import com.yandex.mapkit.map.CameraPosition
import kotlinx.android.synthetic.main.activity_map.*
import ru.prolitiichai.cleancity.R


class MapActivity : Fragment(){

    companion object {

        @JvmStatic
        fun newInstance() =
            MapActivity().apply {
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
        // Inflate the layout for this fragment'
        MapKitFactory.setApiKey("d090c123-3d41-47b2-84bd-08e83f6bd503")
        MapKitFactory.initialize(activity)
        val view = inflater.inflate(R.layout.activity_map, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mapview.map.move(
            CameraPosition(
                Point(55.751574, 37.573856), 11.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 0f), null
        )
        // Initialize UI
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

    override fun onStart() {
        super.onStart()
        mapview.onStart()
        MapKitFactory.getInstance().onStart()
    }
}