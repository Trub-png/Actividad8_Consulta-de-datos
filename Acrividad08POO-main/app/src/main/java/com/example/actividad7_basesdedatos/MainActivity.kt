package com.example.actividad7_basesdedatos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    //DECLARACION DE ELEMENTOS
    private lateinit var etCorreo : EditText
    private lateinit var etPassword : EditText
    private lateinit var btnIngresar : Button
    private lateinit var tvCrearCuenta : TextView

    //Instancia de DatabaseHelper
    private lateinit var dbHelper : DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        //Inicializar los objetos de los componentes
        etCorreo = findViewById(R.id.etCorreo)
        etPassword = findViewById(R.id.etPass)
        btnIngresar = findViewById(R.id.btnIngresar)
        tvCrearCuenta = findViewById(R.id.tvCrearCuenta)
        dbHelper = DatabaseHelper(this)

        //Evento para ingresar / iniciar sesion
        btnIngresar.setOnClickListener {
            iniciarSesion()
        }

        //Evento para ir a la pantalla de registro
        tvCrearCuenta.setOnClickListener {
            val intent = Intent(this, RegistrarActivity::class.java)
            startActivity(intent)
        }
    }

    private fun iniciarSesion(){
        val correo = etCorreo.text.toString().trim()
        val password = etPassword.text.toString().trim()

        //Validar que los campos no esten vacios
        if(correo.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        //Validar el inicio de sesion con la BD
        val inicioExitoso = dbHelper.iniciarSesion(correo, password)

        if(inicioExitoso){
            Toast.makeText(this, "Inicio de sesion exitoso", Toast.LENGTH_SHORT).show()
            etCorreo.text.clear()
            etPassword.text.clear()

            //Navegar a HomeActivity
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show()
        }
    }
}
