package com.example.perfilfalso

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var tv_nombre : TextView
    private lateinit var tv_apellido : TextView
    private lateinit var tv_edad : TextView
    private lateinit var tv_nacionalidad : TextView
    private lateinit var tv_ciudad : TextView
    private lateinit var tv_email: TextView
    private lateinit var tv_telefono: TextView
    private lateinit var imageView: ImageView
    private lateinit var btnRefrescar : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        tv_nombre = findViewById(R.id.textViewNombre)
        tv_apellido = findViewById(R.id.textViewApellido)
        tv_edad = findViewById(R.id.textViewEdad)
        tv_nacionalidad = findViewById(R.id.textViewNacionalidad)
        tv_ciudad = findViewById(R.id.textViewCiudad)
        tv_email = findViewById(R.id.textViewEmail)
        tv_telefono = findViewById(R.id.textViewTelefono)
        imageView = findViewById(R.id.imageView)
        btnRefrescar = findViewById(R.id.button)

        getUserDate()

        btnRefrescar.setOnClickListener{
            getUserDate()
        }


    }

    private fun getUserDate(){
        CoroutineScope(Dispatchers.IO)
            .launch {
                val call = getRetrofit().create(ApiService::class.java).getRandomUser("api/")


                //gestiono los datos que recibo
                val response = call.body()

                runOnUiThread {
                    if(call.isSuccessful){
                        val user = response?.results?.get(0)

                        showUserData(user)

                    }

                }

            }

    }


    private fun showUserData(user: User?){
        if(user!= null){
            val nombre = "Nombre: " + user.name.first
            val apellido = "Apellido: " + user.name.last
            val edad = "Edad: " + user.dob.age
            val nacionalidad = "Nacionalidad: " + user.nat
            val ciudad = "Ciudad: " + user.location.city
            val email = "Email: " + user.email
            val telefono = "Telefono: " + user.phone


            tv_nombre.text = nombre
            tv_apellido.text = apellido
            tv_edad.text = edad
            tv_nacionalidad.text = nacionalidad
            tv_ciudad.text = ciudad
            tv_email.text = email
            tv_telefono.text = telefono

            Glide.with(this)
                .load(user.picture.large)
                .into(imageView)
        }
    }


    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    companion object {
        const val BASE_URL = "https://randomuser.me/"
    }
}