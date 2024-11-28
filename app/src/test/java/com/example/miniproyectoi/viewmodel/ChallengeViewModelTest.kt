package com.example.miniproyectoi.viewmodel

import android.app.Application
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.miniproyectoi.model.Challenge
import com.example.miniproyectoi.respository.ChallengeRepository
import kotlinx.coroutines.CoroutineDispatcher

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.any
import org.mockito.Mockito.anyObject
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class ChallengeViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()
    private lateinit var challengeRepository: ChallengeRepository
    private lateinit var challengeViewModel: ChallengeViewModel

    @Before
    fun setUp() {
        challengeRepository = mock(ChallengeRepository::class.java)
        challengeViewModel = ChallengeViewModel(challengeRepository)
    }


    @Test
    fun `test método guardar reto exitoso`()= runBlocking {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        val challenge = "prueba 1"
        challengeViewModel.saveChallenge(challenge)
        verify(challengeRepository).saveChallenge(challenge)

    }
    @Test
    fun `test método guardar reto con error`()= runBlocking {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        val challenge = "prueba 1"
        `when`(challengeRepository.saveChallenge(challenge)).thenThrow(RuntimeException("save failed"))
        challengeViewModel.saveChallenge(challenge)
        verify(challengeRepository).saveChallenge(challenge)

    }

    @Test
    fun `test método actualizar reto con error`()= runBlocking {
        Dispatchers.setMain(UnconfinedTestDispatcher())

        val challenge = Challenge("1","prueba 1 actualizado")

        `when`(challengeRepository.updateRepository(challenge)).thenThrow(RuntimeException("actualizacion failed"))

        challengeViewModel.updateChallenge(challenge)

        verify(challengeRepository).updateRepository(challenge)

        assertFalse(challengeViewModel.progresState.value==true)

    }
    @Test
    fun `test método actualizar reto valido`()= runBlocking {
        Dispatchers.setMain(UnconfinedTestDispatcher())

        val challenge = Challenge("1","prueba 1 actualizado")

        `when`(challengeRepository.updateRepository(challenge)).thenReturn(Unit)

        challengeViewModel.updateChallenge(challenge)

        verify(challengeRepository).updateRepository(challenge)

        assertFalse(challengeViewModel.progresState.value==true)

    }
    @Test
    fun `test método eliminar reto exitoso`()= runBlocking {
        Dispatchers.setMain(UnconfinedTestDispatcher())

        val challenge = Challenge("1","prueba 1 actualizado")

        `when`(challengeRepository.deleteChallenge(challenge)).thenReturn(Unit)

        challengeViewModel.deleteChallenge(challenge)

        verify(challengeRepository).deleteChallenge(challenge)

        assertFalse(challengeViewModel.progresState.value==true)

    }
    @Test
    fun `test método eliminar reto con error`()= runBlocking {
        Dispatchers.setMain(UnconfinedTestDispatcher())

        val challenge = Challenge("1","prueba 1 actualizado")

        `when`(challengeRepository.deleteChallenge(challenge)).thenThrow(RuntimeException("eliminacion failed"))

        challengeViewModel.deleteChallenge(challenge)

        verify(challengeRepository).deleteChallenge(challenge)

        assertFalse(challengeViewModel.progresState.value==true)

    }

    
}