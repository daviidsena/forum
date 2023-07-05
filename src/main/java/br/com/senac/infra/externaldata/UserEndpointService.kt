package br.com.senac.infra.externaldata

import br.com.senac.infra.externaldata.model.Aluno
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class UserEndpointService(private val baseUrl: String) {

    private val client = OkHttpClient()

    fun getAllUser(): List<Aluno> {
        val url = "$baseUrl"
        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        val response: Response = client.newCall(request).execute()

        if (response.isSuccessful) {
            val responseBody = response.body?.string();
            val gson = Gson();

            return gson.fromJson(responseBody, Array<Aluno>::class.java).toList();
        } else {
            throw Exception("Failed to retrieve user. Status code: ${response.code}")
        }
    }
}