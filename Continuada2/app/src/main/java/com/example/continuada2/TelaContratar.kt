package com.example.continuada2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_tela_contratar.*

class TelaContratar : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_contratar)
    }

    fun camposOk(v: View) {

        val erro_vazio = getString(R.string.erro_campoVazio)

        if (et_inicial.text.length == 0 || et_mensal.text.length == 0 || et_taxa.text.length == 0) {
            Toast.makeText(this, "${erro_vazio}", Toast.LENGTH_LONG).show()
        } else {
            verificarTaxaPeriodo(v)
        }
    }

    fun escolhaTaxa(v: View): Double {

        val taxa: String? = et_taxa.text.toString().toLowerCase()

        when (taxa) {
            "selic" -> return 0.02
            "cdi" -> return 0.0384
            "ipca" -> return 0.0231
            else -> return -1.0
        }
    }

    fun verificarTaxaPeriodo(v: View) {
        val aporteInicial = et_inicial.text.toString().toDouble()
        val aporteMensal = et_mensal.text.toString().toDouble()
        val erroTaxa = getString(R.string.erro_taxaInvalida)
        val erroAporte = getString(R.string.erro_aporteInvalido)

        val valorTaxa = escolhaTaxa(v)

        if (valorTaxa == -1.0) {
            Toast.makeText(this, "${erroTaxa}", Toast.LENGTH_LONG).show()
        } else if (aporteInicial < 0 || aporteMensal < 0) {
            Toast.makeText(this, "${erroAporte}", Toast.LENGTH_LONG).show()
        } else {
            irTelaInvestir(v)
        }
    }

    fun irTelaInvestir(componente: View) {

        val aporteInicial = et_inicial.text.toString().toDouble()
        val aporteMensal = et_mensal.text.toString().toDouble()
        val taxa = escolhaTaxa(componente)

        val telaInvestir = Intent(this, TelaInvestir::class.java)

        telaInvestir.putExtra("taxa", taxa)
        telaInvestir.putExtra("aporteInicial", aporteInicial)
        telaInvestir.putExtra("aporteMinimo", aporteMensal)
        startActivity(telaInvestir)

    }

    /* fun deletarDados() {

        val erro = getString(R.string.erro)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://5f95b9ec2de5f50016ca226c.mockapi.io/investimentos/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val requests = retrofit.create(ApiInvestimentoRequests::class.java)
        val callInvestimentos = requests.getInvestimentos()

        callInvestimentos.enqueue(object : Callback<List<Investimento>> {

            override fun onFailure(call: Call<List<Investimento>>, t: Throwable) {
                Toast.makeText(baseContext, "${erro} $t", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<List<Investimento>>,
                response: Response<List<Investimento>>
            ) {

                response.body()?.forEach {

                    val deleteInvestimentos = requests.deleteInvestimentoPorId(it.id)

                    deleteInvestimentos.enqueue(object : Callback<Void> {
                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            Toast.makeText(baseContext, "${erro} $t", Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                            Toast.makeText(baseContext, "OK", Toast.LENGTH_SHORT).show()
                        }
                    })

                }
            }
        })
    }*/

}