package br.com.senac.infra.externaldata.model

data class Aluno(
    val id: Int,
    val dataCadastro: String,
    val dataTrancamento: String?,
    val dataFormatura: String?,
    val status: Int,
    val usuarioId: String,
    val usuario: Usuario,
    val ofertaId: Int,
    val oferta: Oferta
) {

}

data class Usuario(
    val cpf: String?,
    val foto: String?,
    val nomeCompleto: String,
    val apelido: String?,
    val email: String?,
    val dataNascimento: String?,
    val telefone: String?,
    val dataCadastro: String?,
    val status: String?,
    val senacCoin: String?,
    val id: String,
    val userName: String,
    val normalizedUserName: String?,
    val normalizedEmail: String?,
    val emailConfirmed: Boolean,
    val passwordHash: String?,
    val securityStamp: String?,
    val concurrencyStamp: String,
    val phoneNumber: String?,
    val phoneNumberConfirmed: Boolean,
    val twoFactorEnabled: Boolean,
    val lockoutEnd: String?,
    val lockoutEnabled: Boolean,
    val accessFailedCount: Int
)

data class Oferta(
    val id: Int,
    val descricao: String,
    val codigo: String?,
    val inicioOperacao: String,
    val status: String?,
    val cursoId: Int,
    val curso: String?,
    val modulos: List<String>?
)