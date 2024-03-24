package com.example.attendance.ui.screen.viewAttendanceScreen

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.attendance.NoDataFound
import com.example.attendance.TopBar
import com.example.attendance.data.AttendanceItem
import com.example.attendance.data.AttendanceSubjectItem
import com.example.attendance.util.AttendancePieChartState

@Composable
fun ViewAttendanceScreen(
    onNavigateBack: () -> Unit
) {
    val viewModel  = hiltViewModel<ViewAttendanceScreenViewModel>()
    val attendanceListState by viewModel.attendanceListState.collectAsState()
    val attendancePieChartState by viewModel.attendancePieChartState.collectAsState()
    Scaffold(
        topBar = {
            TopBar(title = "Overall Attendance", onNavigationBack = {onNavigateBack()})
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            PieChart(
                data = mapOf(
                    Pair("Sample-1", attendancePieChartState.totalPresentLecture),
                    Pair("Sample-2", attendancePieChartState.totalAbsentLecture)
                ),
                modifier = Modifier.padding(top = 20.dp),
                overallPercentage = attendancePieChartState.overallPercentage
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.End
            ) {
                Card(
                    modifier = Modifier
                        .height(80.dp)
                        .width(80.dp),
                    colors = CardDefaults.cardColors(

                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "${attendancePieChartState.totalPresentLecture}/${attendancePieChartState.totalLecture}",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Lectures",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(29.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 3.dp
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Overall Attendance",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
//                val attendanceItem = listOf<AttendanceItem>(
//                    AttendanceItem("Java",1,"12 Jan 2023"),
//                    AttendanceItem("RDBMS",0,"2 Feb 2023"),
//                    AttendanceItem("Ktolin",1,"14 Feb 2022"),
//                    AttendanceItem("Java",1,"12 Jan 2023"),
//                    AttendanceItem("RDBMS",0,"2 Feb 2023"),
//                    AttendanceItem("Ktolin",1,"14 Feb 2022"),
//                    AttendanceItem("Java",1,"12 Jan 2023"),
//                    AttendanceItem("RDBMS",0,"2 Feb 2023"),
//                    AttendanceItem("Ktolin",1,"14 Feb 2022")
//                )
                val attendanceItem = listOf<AttendanceItem>()
                if(attendanceListState.attendanceList.isEmpty()){
                    NoDataFound()
                }else{
                    LazyColumn(){
                        items(items = attendanceListState.attendanceList){
                            AttendanceDisplayItem(attendanceItem = it, viewModel = viewModel)
                        }
                    }
                }


            }
            if(viewModel.deleteAttendanceDialogStateShow){
                DeleteAttendanceDialog(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun AttendanceDisplayItem(
    modifier: Modifier = Modifier,
    attendanceItem: AttendanceItem,
    viewModel: ViewAttendanceScreenViewModel
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            Color(0xFFDBE5DD)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .clickable {  }
                .padding(start = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Text(
                    text = attendanceItem.subjectName,
                    fontSize = 16.sp,
                    color = if (attendanceItem.attendance == 1) {
                        Color(0xFF108A15)
                    } else {
                        Color(0xD5D61F1F)
                    },
                    fontWeight = FontWeight.Bold
                )
            }
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = attendanceItem.date,
                    fontSize = 16.sp,
                    color = if (attendanceItem.attendance == 1) {
                        Color(0xFF108A15)
                    } else {
                        Color(0xD5D61F1F)
                    },
                    fontWeight = FontWeight.Bold
                )
                IconButton(
                    onClick = {
                        viewModel.onDeleteAttendanceDialogStateChange(true)
                        viewModel.setValueDeleteAttendanceItemData(attendanceItem)
                    },
                    modifier = Modifier
                        .size(35.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = Color(0xCD222020)
                    )
                }
            }
        }
    }
}

@Composable
fun DeleteAttendanceDialog(
    modifier: Modifier = Modifier,
    viewModel: ViewAttendanceScreenViewModel
) {
    AlertDialog(
        onDismissRequest = {
            viewModel.onDeleteAttendanceDialogStateChange(false)
        },
        confirmButton = {
            TextButton(onClick = {
                viewModel.onDeleteAttendance()
            }) {
                Text(text = "Delete")
            }
        },
        dismissButton = {
            TextButton(onClick = { viewModel.onDeleteAttendanceDialogStateChange(false) }) {
                Text(text = "Cancel")
            }
        },
        icon = {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null,
                modifier = Modifier
                    .size(35.dp),
                tint = Color.Red
            )
        },
        title = {
            Text(text = "Are You Sure?")
        },
        text = {
            Text(text = "This will delete attendance of particular day. after that data will delete permanently.")
        }
    )
}

@Composable
fun PieChart(
    modifier: Modifier = Modifier,
    data: Map<String, Int>,
    radiusOuter: Dp = 90.dp,
    chartBarWidth: Dp = 20.dp,
    animDuration: Int = 1000,
    overallPercentage : Double
) {

    val totalSum = data.values.sum()
    val floatValue = mutableListOf<Float>()

    data.values.forEachIndexed { index, values ->
        floatValue.add(index, 360 * values.toFloat() / totalSum.toFloat())
    }

    val colors = listOf(
        Color(0xF222D829),
        Color(0xF3E62424)
    )

    var animationPlayed by remember { mutableStateOf(false) }

    var lastValue = 0f

    // it is the diameter value of the Pie
    val animateSize by animateFloatAsState(
        targetValue = if (animationPlayed) radiusOuter.value * 2f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    )

    // if you want to stabilize the Pie Chart you can use value -90f
    // 90f is used to complete 1/4 of the rotation
    val animateRotation by animateFloatAsState(
        targetValue = if (animationPlayed) 90f * 11f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    )

    // to play the animation only once when the function is Created or Recomposed
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier.size(animateSize.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier
                    .size(radiusOuter * 2f)
                    .rotate(animateRotation)
            ) {
                // draw each Arc for each data entry in Pie Chart
                floatValue.forEachIndexed { index, value ->
                    drawArc(
                        color = colors[index],
                        lastValue,
                        value,
                        useCenter = false,
                        style = Stroke(chartBarWidth.toPx(), cap = StrokeCap.Butt)
                    )
                    lastValue += value
                }
            }
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${overallPercentage}%",
                    fontSize = 30.sp,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
//    Text(text = "Hello")
}
