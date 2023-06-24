package com.miempresa.googlemapv4

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.content.ContextCompat
import android.Manifest
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.miempresa.googlemapv4.databinding.ActivityMapsBinding
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener,
    AdapterView.OnItemSelectedListener {

    private var destino = ""
    private var marcadorDestino: Marker? = null
    private var coordenada = LatLng(0.0, 0.0)
    private var informacion = ""

    private val Plaza = LatLng(-16.3988032, -71.5394805)

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private val tipos_mapas = intArrayOf(
        GoogleMap.MAP_TYPE_NONE,
        GoogleMap.MAP_TYPE_NORMAL,
        GoogleMap.MAP_TYPE_SATELLITE,
        GoogleMap.MAP_TYPE_HYBRID,
        GoogleMap.MAP_TYPE_TERRAIN
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        spnTipoMapa.onItemSelectedListener = this

        val recibidos = intent.extras
        if (recibidos != null) {
            destino = intent.getStringExtra("destino") ?: ""
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.setAllGesturesEnabled(true)
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
        } else {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 123)
        }

        when (destino) {
            "plaza de armas" -> {
                coordenada = LatLng(-16.3988031, -71.5374435)
                informacion =
                    "La plaza Mayor o plaza de Armas de Arequipa, es uno de los principales espacios públicos de Arequipa y el lugar de fundación de la ciudad"
            }
            "characato" -> {
                coordenada = LatLng(-16.4703241, -71.5221986)
                informacion = "El distrito de Characato es uno de los 29 distritos que conforman la provincia de Arequipa en el departamento de Arequipa"
            }
            "colca" -> {
                coordenada = LatLng(-16.4698441, -71.645808)
                informacion = "El Cañón del Colca es el lugar perfecto para ver a una de las aves más representativas de la biodiversidad en nuestro país, el cóndor andino."
            }
            "yura" -> {
                coordenada = LatLng(-16.2525412, -71.734399)
                informacion = "El distrito de Yura es uno de los veintinueve que conforman la provincia de Arequipa, ubicada en el departamento de Arequipa, en el Sur del Perú."
            }
            "mirador sachaca" -> {
                coordenada = LatLng(-16.4262762, -71.5674416)
                informacion = "El mirador tiene cinco pisos. Desde su terraza se puede apreciar a los volcanes Misti, Chachani y Pichu Pichu"
            }
            else -> {
                Toast.makeText(this, "No se encontró destino turístico", Toast.LENGTH_LONG).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        marcadorDestino = mMap.addMarker(
            MarkerOptions()
                .position(coordenada)
                .title("Conoce: $destino")
        )

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordenada, 15f))
        mMap.setOnMarkerClickListener(this)
    }

    override fun onMarkerClick(p0: Marker): Boolean {
        if (p0 == marcadorDestino) {
            val intent = Intent(this, destinos::class.java)
            intent.putExtra("destino", destino)
            intent.putExtra("latitud", p0.position.latitude.toString())
            intent.putExtra("longitud", p0.position.longitude.toString())
            intent.putExtra("info", informacion)
            startActivity(intent)
            return true
        }
        return false
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        mMap.setMapType(tipos_mapas[p2])
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        // No implementado
    }
}
