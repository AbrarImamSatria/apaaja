package com.example.pertemuan8.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pertemuan8.ui.view.screen.DetailMahasiswaView
import com.example.pertemuan8.ui.view.screen.MahasiswaFormView
import com.example.pertemuan8.ui.view.screen.RencanaStudyView
import com.example.pertemuan8.ui.view.screen.SplashView
import com.example.pertemuan8.ui.view.viewmodel.MahasiswaViewModel
import com.example.pertemuan8.ui.view.viewmodel.RencanaStudyViewModel

enum class Halaman {
    Splash,
    Mahasiswa,
    Matakuliah,
    Tampil
}

@Composable
fun MahasiswaApp(
    modifier: Modifier = Modifier,
    mahasiswaViewModel: MahasiswaViewModel = viewModel(),
    krsViewModel: RencanaStudyViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
){
    val mahasiswaUiState = mahasiswaViewModel.mahasiswaUIState.collectAsState().value
    val rencanaStudiUiState = krsViewModel.krsStateUI.collectAsState().value
    NavHost(
        navController = navController,
        startDestination = Halaman.Splash.name,
        modifier = Modifier.padding()
    ){
        composable(route = Halaman.Splash.name){
            SplashView(onMulaiButton = {
                navController.navigate(
                    Halaman.Mahasiswa.name
                )
            })
        }
        composable(route = Halaman.Mahasiswa.name) {
            MahasiswaFormView(
                onSubmitButtonClicked = { mahasiswa ->
                    mahasiswaViewModel.saveDataMahasiswa(mahasiswa)
                    navController.navigate(Halaman.Matakuliah.name)
                },
                onBackButtonClicked = {
                    navController.popBackStack()
                }
            )
        }
        composable(route = Halaman.Matakuliah.name) {
            RencanaStudyView(
                mahasiswa = mahasiswaUiState,
                onSubmitButtonClicked = { rencanaStudi ->
                    krsViewModel.saveDataKRS(rencanaStudi)
                    navController.navigate(Halaman.Tampil.name)
                },
                onBackButtonClicked = { navController.popBackStack() }
            )
        }
        composable(route = Halaman.Tampil.name) {
            DetailMahasiswaView(
                dataMhs = mahasiswaUiState,
                dataRencanaStudi = rencanaStudiUiState,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}

