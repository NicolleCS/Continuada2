package com.example.continuada2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_tela_adicionar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TelaAdicionar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_adicionar)
    }

    fun camposAdicionarOk(v: View) {

        val erro_vazio = getString(R.string.erro_campoVazio)

        if (et_adicionar_ano.text.length == 0 || et_adicionar_mensal.text.length == 0 || et_adicionar_mes.text.length == 0) {
            Toast.makeText(this, "${erro_vazio}", Toast.LENGTH_LONG).show()
        } else {
            adicionarInvestimento(v)
        }
    }

    fun adicionarInvestimento(componente: View) {

        var isMinimo = false;
        val aporteMinimo = intent.extras!!.getDouble("aporteMinimo")
        val aporteMensal = et_adicionar_mensal.text.toString().toBigDecimal()
        val erroAporte = getString(R.string.erro_aporteMensal)
        val erro = getString(R.string.erro)

        while (!isMinimo) {
            if (aporteMensal.toDouble() >= aporteMinimo) {
                isMinimo = true;
            } else {
                Toast.makeText(
                    this,
                    "${erroAporte} ${String.format("%.2f", aporteMinimo)}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        val taxa = intent.extras!!.getDouble("taxa")
        val aporteInicial = intent.extras!!.getDouble("aporteInicial")
        val lucro = (aporteMensal.toDouble() + aporteInicial) * taxa
        val aporteComRendimento = ((aporteMensal.toDouble() + lucro) * (1.0 + taxa)).toBigDecimal()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://5f95b9ec2de5f50016ca226c.mockapi.io/investimentos/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val requests = retrofit.create(ApiInvestimentoRequests::class.java)

        val novoInvestimento = Investimento(
            null,
            aporteComRendimento,
            et_adicionar_mes.text.toString(),
            et_adicionar_ano.text.toString().toInt()
        )

        val callCriarInvestimento = requests.postInvestimento(novoInvestimento)

        callCriarInvestimento.enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(baseContext, "${erro} $t", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Toast.makeText(baseContext, getString(R.string.investido), Toast.LENGTH_SHORT)
                    .show()
                voltarTelaInvestir(componente)
            }
        })
    }

    fun voltarTelaInvestir(componente: View) {
        val telaInvestir = Intent(this, TelaInvestir::class.java)

        val taxa = intent.extras!!.getDouble("taxa")
        val aporteMinimo = intent.extras!!.getDouble("aporteMinimo")
        val aporteInicial = intent.extras!!.getDouble("aporteInicial")

        telaInvestir.putExtra("taxa", taxa)
        telaInvestir.putExtra("aporteMinimo", aporteMinimo)
        telaInvestir.putExtra("aporteInicial", aporteInicial)

        startActivity(telaInvestir)
    }

}