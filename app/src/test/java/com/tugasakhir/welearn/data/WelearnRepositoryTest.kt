package com.tugasakhir.welearn.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.whenever
import com.tugasakhir.welearn.core.data.source.remote.RemoteDataSource
import com.tugasakhir.welearn.core.data.source.remote.network.ApiService
import com.tugasakhir.welearn.domain.model.Soal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineContext
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.koin.core.definition.Kind
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.io.IOException
import javax.sql.DataSource

class WelearnRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val welearnRepository = FakeWelearnRepository(remote)

    @Test
    fun getSoalHuruf() {
//        val dataSourceFactory = mock(Kind.Factory::class.java) as Kind.Factory<Int, Soal>
    }

    @Test
    fun `should throw error`() = runBlocking {

        // Mock
         // Test and Verify
//        val flow: Flow<List<Soal>> = welearnRepository.randAngka()
//
//        flow.test {
//            assertThat(
//                expectError(),
//                instanceOf(IOException::class.java)
//            )
//        }
    }


}