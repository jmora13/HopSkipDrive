package com.example.hopskipdrive

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hopskipdrive.databinding.ActivityRideDetailsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.hopskipdrive.adapters.RideDetailsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RideDetailsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityRideDetailsBinding
    private lateinit var locations: HashMap<String, List<Double>>
    private lateinit var mMap: GoogleMap
    private val rideViewModel: RideViewModel by viewModels()
    private lateinit var startsAt: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRideDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //MAPVIEW INIT
        val mapFragment =
            supportFragmentManager.findFragmentByTag("mapFragment") as SupportMapFragment
        mapFragment?.getMapAsync(this)

        // FUNCTION TO GET PUTEXTRA VALUES AND BIND TO VIEWS
        initViews()

        //INITIALIZE RECYCLERVIEW ADAPTER FOR PICKUP AND DROPPOFF LIST
        val rideDetailsAdapter = RideDetailsAdapter()
        binding.pickupDropoffRecyclerview.adapter = rideDetailsAdapter
        binding.pickupDropoffRecyclerview.layoutManager = LinearLayoutManager(this)
        rideDetailsAdapter.submitList(locations.keys.toList())

    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0

        for((k,v) in locations) {
            val newMarker = LatLng(v[0], v[1])
            mMap.addMarker(
                MarkerOptions()
                    .position(newMarker)
                    .title(k)
            )
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newMarker,9f))
        }
    }

    //DELETE RIDE FROM DATABASE
    fun deleteRide(view: View) {
        lifecycleScope.launch{
            rideViewModel.deleteRide(startsAt)
        }
        finish()
    }

    //GETEXTRA VALUES AND VIEWBINDING
    fun initViews(){
        val intent = intent //INTENT TO GET PUTEXTRA DATA
        locations = intent.getSerializableExtra("locationsMap") as HashMap<String, List<Double>>
        val inSeries = intent.getBooleanExtra("inSeries", false)
        startsAt = intent.getStringExtra("startsAt")!!
        binding.rideDate.text = intent.getStringExtra("parsedDate")
        binding.startRideTime.text = "${intent.getStringExtra("startTime")}"
        binding.endRideTime.text = " — ${intent.getStringExtra("endTime")}"
        binding.totalEstimate.text = intent.getStringExtra("estimate")
        if(inSeries){
            binding.isSeries.visibility = View.VISIBLE
        }
        binding.tripId.text = "Trip ID: ${intent.getStringExtra("id")}"
        binding.distance.text = "${intent.getStringExtra("distance")} mi"
        binding.duration.text = "${intent.getStringExtra("duration")} min"

    }

}