package br.com.senac.infra.externaldata

data class User (
    val userId: String,
    val username: String,
) {
    constructor() : this(String.toString(), String.toString()) {
    }
}
