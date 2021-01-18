package com.idn.smart.tiyas.infocovid19.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.idn.smart.tiyas.infocovid19.R
import com.idn.smart.tiyas.infocovid19.api.RetrofitClient
import com.idn.smart.tiyas.infocovid19.model.IndonesiaResponse
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showIndonesia()

        btnProvince.setOnClickListener {
            Intent(this@MainActivity,ProvinceActivity::class.java).also {
                startActivity(it)
            }
        }

        cdv3.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://covid19.go.id/peta-sebaran-covid19"))
            startActivity(i)
        }
    }

    private fun showIndonesia() {
        RetrofitClient.instance.getIndonesia().enqueue(object  : Callback<ArrayList<IndonesiaResponse>>{
            override fun onResponse(call: Call<ArrayList<IndonesiaResponse>>, response: Response<ArrayList<IndonesiaResponse>>) {
                // memuat variaable untuk data
                val Indonesia = response.body()?.get(0)
                val positive = Indonesia?.positif
                val  hospitalized = Indonesia?.dirawat
                val  recover = Indonesia?.sembuh
                val death = Indonesia?.meninggal

                // inisialisasi dg id textvie yg berada di xml
                tvPositive.text = positive
                tvDirawat.text = hospitalized
                tvSembuh.text = recover
                tvMeninggal.text = death
            }

            override fun onFailure(call: Call<ArrayList<IndonesiaResponse>>, t: Throwable) {
                Toast.makeText(this@MainActivity,"${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }
}