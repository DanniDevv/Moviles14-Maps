package com.miempresa.googlemapv4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_destinos.*

class destinos : AppCompatActivity() {
    var destino = ""
    var latitud = ""
    var longitud = ""
    var info = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destinos)

        val recibidos = intent.extras
        if (recibidos != null) {
            destino = recibidos.getString("destino")!!
            latitud = recibidos.getString("latitud")!!
            longitud = recibidos.getString("longitud")!!
            info = recibidos.getString("info")!!
        }
        lblDestino.setText(destino)
        lblCoordenadas.setText("$latitud , $longitud")
        lblInfo.setText(info)
        val imageView: ImageView = findViewById(R.id.imageView)

        // Carga la imagen desde la URL utilizando Picasso
        val imageUrl: String = when (destino) {
            "plaza de armas" -> "https://images.mnstatic.com/b4/f7/b4f707d8b868e9df994e4040a44e77ed.jpg"
            "characato" -> "https://conresatours.com.pe/wp-content/uploads/2016/08/Characato-Plaza-Arequipa_Big.jpg"
            "colca" -> "https://elcomercio.pe/resizer/EFO44D8XzZKkhBpgzVJTIBr5LXY=/980x0/smart/filters:format(jpeg):quality(75)/cloudfront-us-east-1.images.arcpublishing.com/elcomercio/XISRGHXLJBGW3A5QGWECCD5XHY.jpg"
            "yura" -> "https://media-cdn.tripadvisor.com/media/photo-m/1280/19/89/6e/ba/hotel-de-turistas-yura.jpg"
            "mirador sachaca" -> "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/10/1e/07/33/mirador-de-sachaca.jpg?w=1200&h=1200&s=1"
            else -> ""
        }

        if (imageUrl.isNotEmpty()) {
            Picasso.get().load(imageUrl).into(imageView)
        }
        btnVolver.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
