package com.teste.hexagonapp.view

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.rememberImagePainter
import com.teste.hexagonapp.model.User
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditUserScreen(user: User, onSaveClick: (User) -> Unit, onCancelClick: () -> Unit) {
    var name by remember { mutableStateOf(user.name) }
    var cpf by remember { mutableStateOf(user.cpf) }
    var city by remember { mutableStateOf(user.city) }
    var photoUri by remember { mutableStateOf(user.photoUri) }
    var isActive by remember { mutableStateOf(user.isActive) }

    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    var birthDate by remember { mutableStateOf(user.birthDate) }

    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { photoUri = it.toString() }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            launcher.launch("image/*")
        } else {
            Toast.makeText(context, "Permissão negada. Não é possível selecionar uma imagem.", Toast.LENGTH_SHORT).show()
        }
    }

    val permissionToRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = cpf,
            onValueChange = { cpf = it },
            label = { Text("CPF") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("Cidade") },
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(8.dp))

        var dia by remember { mutableStateOf(dateFormat.format(birthDate).split("/")[0]) }
        var mes by remember { mutableStateOf(dateFormat.format(birthDate).split("/")[1]) }
        var ano by remember { mutableStateOf(dateFormat.format(birthDate).split("/")[2]) }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                value = dia,
                onValueChange = { if (it.length <= 2 && it.all { char -> char.isDigit() }) dia = it },
                label = { Text("Dia") },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedTextField(
                value = mes,
                onValueChange = { if (it.length <= 2 && it.all { char -> char.isDigit() }) mes = it },
                label = { Text("Mês") },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedTextField(
                value = ano,
                onValueChange = { if (it.length <= 4 && it.all { char -> char.isDigit() }) ano = it },
                label = { Text("Ano") },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }

        LaunchedEffect(dia, mes, ano) {
            if (dia.length == 2 && mes.length == 2 && ano.length == 4) {
                try {
                    val diaInt = dia.toInt()
                    val mesInt = mes.toInt()
                    val anoInt = ano.toInt()
                    
                    if (diaInt in 1..31 && mesInt in 1..12 && anoInt in 1900..2100) {
                        val dataString = "$dia/$mes/$ano"
                        val parsedDate = dateFormat.parse(dataString)
                        if (parsedDate != null) {
                            birthDate = parsedDate
                        } else {
                            Toast.makeText(context, "Data inválida", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context, "Data inválida", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(context, "Formato de data inválido", Toast.LENGTH_SHORT).show()
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (photoUri.isNotEmpty()) {
                Image(
                    painter = rememberImagePainter(data = photoUri),
                    contentDescription = "Foto do usuário",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.LightGray, CircleShape)
                        .clip(CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Ícone de usuário",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = {
                when {
                    ContextCompat.checkSelfPermission(
                        context,
                        permissionToRequest
                    ) == PackageManager.PERMISSION_GRANTED -> {
                        launcher.launch("image/*")
                    }
                    else -> {
                        permissionLauncher.launch(permissionToRequest)
                    }
                }
            }) {
                Text("Selecionar Foto")
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = isActive,
                onCheckedChange = { isActive = it }
            )
            Text("Usuário Ativo")
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = onCancelClick) {
                Text("Cancelar")
            }
            Button(onClick = {
                onSaveClick(
                    user.copy(
                        name = name,
                        birthDate = birthDate,
                        cpf = cpf,
                        city = city,
                        photoUri = photoUri,
                        isActive = isActive
                    )
                )
            }) {
                Text("Salvar")
            }
        }
    }
}