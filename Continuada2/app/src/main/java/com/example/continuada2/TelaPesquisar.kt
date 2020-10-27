package com.example.continuada2

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_tela_investir.*
import kotlinx.android.synthetic.main.activity_tela_pesquisar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TelaPesquisar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_pesquisar)

        val erro = getString(R.string.erro)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://5f95b9ec2de5f50016ca226c.mockapi.io/investimentos/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val requests = retrofit.create(ApiInvestimentoRequests::class.java)
        val mes = intent.extras!!.getString("mes").toString()
        val ano = intent.extras!!.getInt("ano").toString()

        val callInvestimentoId = requests.getInvestimentosPorMesEAno(mes, ano)
        val mensalTxt = getString(R.string.contrate_mensal)
        val mesTxt = getString(R.string.mes)
        val idTxt = getString(R.string.id)
        val anoTxt = getString(R.string.ano)

        callInvestimentoId.enqueue(object : Callback<List<Investimento>?> {

            override fun onFailure(call: Call<List<Investimento>?>, t: Throwable) {
                Toast.makeText(baseContext, "${erro} $t", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<List<Investimento>?>,
                response: Response<List<Investimento>?>
            ) {
                response.body()?.forEach {

                    val novoTv = TextView(baseContext)

                    tv_pesquisa.visibility = View.GONE
                    novoTv.text = "${idTxt}:${it.id} \n" +
                            " ${mensalTxt}: ${String.format("%.2f", it.aporteMensal)} \n" +
                            " ${anoTxt}: ${it.ano} \n" +
                            " ${mesTxt}: ${it.mes}"

                    novoTv.setTextColor(Color.parseColor("#FFFFFF"))
                    novoTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)

                    ll_pesquisa.addView(novoTv)
                }
            }
        })
    }
}