package com.example.continuada2

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_tela_adicionar.*
import kotlinx.android.synthetic.main.activity_tela_investir.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TelaInvestir : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_investir)

        historicoInvestimentos()
    }

    fun camposPesquisarOk(v: View) {

        val erro_vazio = getString(R.string.erro_campoVazio)

        if (et_pesquisar_ano.text.length == 0 || et_pesquisar_mes.text.length == 0 ) {
            Toast.makeText(this, "${erro_vazio}", Toast.LENGTH_LONG).show()
        } else {
            irTelaPesquisar(v)
        }
    }

    fun irTelaAdicionar(componente: View) {
        val telaAdicionar = Intent(this, TelaAdicionar::class.java)

        var taxa = intent.extras!!.getDouble("taxa")
        var aporteMinimo = intent.extras!!.getDouble("aporteMinimo")
        var aporteInicial = intent.extras!!.getDouble("aporteInicial")

        telaAdicionar.putExtra("taxa", taxa)
        telaAdicionar.putExtra("aporteMinimo", aporteMinimo)
        telaAdicionar.putExtra("aporteInicial", aporteInicial)

        startActivity(telaAdicionar)
    }

    fun historicoInvestimentos() {

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
                val mensalTxt = getString(R.string.contrate_mensal)
                val mesTxt = getString(R.string.mes)
                val acumulado = getString(R.string.valor_acumulado)
                val aporteInicial = intent.extras!!.getDouble("aporteInicial")
                var total = aporteInicial

                response.body()?.forEach {
                    val novoTv = TextView(baseContext)

                    tv_conteudo_home.visibility = View.GONE
                    novoTv.text = "${mensalTxt}: ${String.format(
                        "%.2f",
                        it.aporteMensal
                    )} \n ${mesTxt}: ${it.mes} \n\n"
                    total += it.aporteMensal.toDouble()

                    novoTv.setTextColor(Color.parseColor("#FFFFFF"))
                    novoTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)


                    ll_todo_conteudo.addView(novoTv)
                }

                tv_acumulado.text = "${acumulado} ${String.format("%.2f", total)}"
            }
        })
    }

    fun irTelaPesquisar(componente: View) {
        val telaPesquisar = Intent(this, TelaPesquisar::class.java)
        var mes = et_pesquisar_mes.text.toString()
        var ano = et_pesquisar_ano.text.toString()

        telaPesquisar.putExtra("mes", mes)
        telaPesquisar.putExtra("ano", ano)
        startActivity(telaPesquisar)
    }

}