package com.example.continuada2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun Comecar(componente: View) {
        tv_titulo.visibility = View.GONE
        tv_subtitulo.visibility = View.GONE
        iv_imagemhome.visibility = View.GONE
        bt_comecar.visibility = View.GONE
        tv_sem_planos.visibility = View.VISIBLE
        bt_contrate_plano.visibility = View.VISIBLE
    }

    fun Contratar(componente: View) {
        val telaContratar = Intent(this, TelaContratar::class.java)
        startActivity(telaContratar)
    }
}