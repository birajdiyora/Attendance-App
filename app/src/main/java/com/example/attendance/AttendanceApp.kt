package com.example.attendance

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.attendance.data.AttendanceRepository
import com.example.attendance.navigation.AttendanceNavGraph
import com.example.attendance.navigation.AttendanceScreen
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AttendanceApp(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController(),
    startingScreen : String
) {

//
//    Log.d("Attendance123",logedin.toString())
    AttendanceNavGraph(
        navController = navHostController,
        startingScreen = startingScreen)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title : String,
    onNavigationBack : () -> Unit
) {
    TopAppBar(
        title = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Column {
                    Text(
                        text = title,
                        overflow = TextOverflow.Ellipsis,
                        softWrap = false,
                        fontWeight = FontWeight.Bold)
                }
            }
        },
        navigationIcon = {
            IconButton(onClick = { onNavigationBack() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.inverseOnSurface
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarWithDeleteIcon(
    title : String,
    onDeleteButtonClick : () -> Unit,
    onNavigateBack : () -> Unit
) {
    TopAppBar(
        title = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(0.8f)
                ) {
                    Text(
                        text = title,
                        overflow = TextOverflow.Ellipsis,
                        softWrap = false,
                        fontWeight = FontWeight.Bold)
                }
//                Spacer(modifier = Modifier.width(8.dp))
                Row {
                    IconButton(onClick = {
                        onDeleteButtonClick()
                    },
                        modifier = Modifier
                            .size(33.dp)) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            modifier = Modifier.size(25.dp))
                    }

                }
            }
        },
        navigationIcon = {
            IconButton(onClick = { onNavigateBack() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.inverseOnSurface
        )
    )
}

@Composable
fun NoDataFound() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "No any data found.",
            fontSize = 25.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    }
}

fun convertDateInMillisToDate(dateInMillis: Long): String {
    val simpleDateFormat = SimpleDateFormat("dd MMM yyyy")
    val result = Date(dateInMillis)
    return simpleDateFormat.format(result)
}
fun findPercentageOfAttendance(presentLecture : Int, absentLecture : Int) : Double {
    val totalNoOfLecture = presentLecture + absentLecture
    if(totalNoOfLecture == 0){
        return 0.00
    }else{
        val num = (presentLecture.toDouble()/totalNoOfLecture.toDouble()) * 100.0
        val percentage = String.format("%.2f",num).toDouble()
        return percentage
    }
//    return totalNoOfLecture.toDouble()
}

suspend fun updateTotalAbsentLecture(repository: AttendanceRepository) {
    repository.getTotalNoOfAbsentLecture().collect{
        repository.updateAbsentLecture(absentLecture = it)
    }
}
suspend fun updateTotalPresentLecture(repository: AttendanceRepository) {
    repository.getTotalNoOfPresentLecture().collect{
        repository.updatePresentLecture(presentLecture = it)
    }
}
