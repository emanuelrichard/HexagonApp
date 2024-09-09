package com.teste.hexagonapp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.teste.hexagonapp.model.User
import com.teste.hexagonapp.viewmodel.UserViewModel
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    userViewModel: UserViewModel = hiltViewModel(),
    onInsertClick: () -> Unit,
    onEditClick: (User) -> Unit
) {
    val users = userViewModel.getActiveUsers().collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Usuários Ativo") },
                actions = {
                    IconButton(onClick = onInsertClick) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Adicionar Usuário")
                    }
                    IconButton(onClick = { navController.navigate("inactiveUsers") }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Ver Usuários Inativos"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        UserList(
            users = users.value,
            onEditClick = onEditClick,
            onToggleActiveStatus = { user ->
                userViewModel.toggleUserActiveStatus(user)
            },
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun UserList(
    users: List<User>,
    onEditClick: (User) -> Unit,
    onToggleActiveStatus: (User) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(users.size) { index ->
            UserItem(
                user = users[index],
                onEditClick = { onEditClick(users[index]) },
                onToggleActiveStatus = { onToggleActiveStatus(users[index]) }
            )
            if (index < users.size - 1) {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun UserItem(
    user: User,
    onEditClick: () -> Unit,
    onToggleActiveStatus: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onEditClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberImagePainter(data = user.photoUri),
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = user.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Idade: ${calculateAge(user.birthDate)} anos",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "CPF: ${user.cpf}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Cidade: ${user.city}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Status: Ativo",
                        fontSize = 14.sp,
                        color = Color.Green
                    )
                }
            }
            IconButton(onClick = onToggleActiveStatus) {
                Icon(
                    imageVector = if (user.isActive) Icons.Default.Close else Icons.Default.Check,
                    contentDescription = if (user.isActive) "Desativar Usuário" else "Ativar Usuário",
                    tint = if (user.isActive) Color.Red else Color.Green
                )
            }
        }
    }
}

fun calculateAge(birthDate: Date): Int {
    val calendar = Calendar.getInstance()
    val birth = Calendar.getInstance().apply { time = birthDate }
    var age = calendar.get(Calendar.YEAR) - birth.get(Calendar.YEAR)
    if (calendar.get(Calendar.DAY_OF_YEAR) < birth.get(Calendar.DAY_OF_YEAR)) {
        age--
    }
    return age
}