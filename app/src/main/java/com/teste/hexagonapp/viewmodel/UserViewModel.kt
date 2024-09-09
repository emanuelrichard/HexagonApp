package com.teste.hexagonapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teste.hexagonapp.model.User
import com.teste.hexagonapp.model.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userDao: UserDao
) : ViewModel() {

    private val _activeUsers = MutableStateFlow<List<User>>(emptyList())
    private val _inactiveUsers = MutableStateFlow<List<User>>(emptyList())

    private val _selectedUser = MutableStateFlow<User?>(null)
    val selectedUser: StateFlow<User?> = _selectedUser

    init {
        loadUsers()
    }

    private fun loadUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            _activeUsers.value = userDao.getActiveUsers()
            _inactiveUsers.value = userDao.getInactiveUsers()
        }
    }

    fun getActiveUsers(): StateFlow<List<User>> = _activeUsers
    fun getInactiveUsers(): StateFlow<List<User>> = _inactiveUsers

    fun insertUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            userDao.insertUser(user)
            loadUsers()
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            userDao.updateUser(user)
            loadUsers()
        }
    }

    fun selectUser(user: User) {
        _selectedUser.value = user
    }

    fun toggleUserActiveStatus(user: User) {
        viewModelScope.launch {
            userDao.updateUser(user.copy(isActive = !user.isActive))
            loadUsers()
        }
    }
}