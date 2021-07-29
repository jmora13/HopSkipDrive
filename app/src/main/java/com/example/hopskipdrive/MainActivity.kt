package com.example.hopskipdrive

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hopskipdrive.adapters.RideListAdapter
import com.example.hopskipdrive.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.HttpException
import java.io.IOException

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val rideViewModel: RideViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //INITIALIZE RIDE RECYCLERVIEW
        val rideAdapter = RideListAdapter()
        binding.rideRecyclerView.adapter = rideAdapter
        binding.rideRecyclerView.layoutManager = LinearLayoutManager(this)

        //GET ALL EVENTS AND ADD TO RECYCLERVIEW
        rideViewModel.allRides.observe(this){ rides ->
            rides.let{ rideAdapter.submitList(it) }
            binding.spinner.visibility = View.GONE
    }

        lifecycleScope.launchWhenCreated {
                try {
                    //GET LIST OF RIDES
                rideViewModel.getRides()
            } catch (e: IOException) {
                Log.d("IOEXCEPTION", e.message.toString())
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.d("HTTPEXCEPTION", e.stackTrace.toString())
                return@launchWhenCreated
            }
        }
    }
}