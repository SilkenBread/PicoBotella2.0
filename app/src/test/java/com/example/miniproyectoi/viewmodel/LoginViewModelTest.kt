package com.example.miniproyectoi.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.miniproyectoi.repository.LoginRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class LoginViewModelTest {

    @get:Rule
    val ruletwo = InstantTaskExecutorRule()

    @Mock
    lateinit var mockloginrepository : LoginRepository

    @Mock
    lateinit var mockobserver : Observer<Boolean>
    private lateinit var loginViewModel: LoginViewModel

    @Mock
    lateinit var firebaseAuth: FirebaseAuth

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        loginViewModel = LoginViewModel(mockloginrepository)

    }

    @Test
    fun `fallo en login`() {

        var email = ""
        var password = ""
        var isloginresult= true

        loginViewModel.loginUser(email  ,password){ isSuccess->
            isloginresult = isSuccess
        }

        assert(isloginresult == false)

    }
    @Test
    fun `test validacion password exitoso`() {
        val password = "123456"

        loginViewModel.validatePassword(password)

        // Verifica si no hay error
        assertNull(loginViewModel.passwordError.value)
    }
    @Test
    fun `test password vacio`() {
        val email = "prueba@android.com"
        val password = ""

        loginViewModel.validatePassword(password)

        // Verifica si el error de la contraseña se ha establecido
        assertNotNull(loginViewModel.passwordError.value)
        assertEquals("Mínimo 6 dígitos", loginViewModel.passwordError.value)
    }

    @Test
    fun `test validacion de formulario`() {
        val email = "frank@hoze.com"
        val password = "123456"

        loginViewModel.validateForm(email, password)

        // Verifica si el formulario es válido
        assertTrue(loginViewModel.isFormValid.value ?: false)
    }


}