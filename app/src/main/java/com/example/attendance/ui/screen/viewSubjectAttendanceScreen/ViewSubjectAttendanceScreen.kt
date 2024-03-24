package com.example.attendance.ui.screen.viewSubjectAttendanceScreen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.attendance.NoDataFound
import com.example.attendance.TopBarWithDeleteIcon
import com.example.attendance.data.AttendanceSubjectItem
import com.example.attendance.util.SubjectAttendanceAddDialogState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ViewSubjectAttendanceScreen(
    modifier: Modifier = Modifier,
    onNavigateBack : () -> Unit
) {
    val viewModel = hiltViewModel<ViewSubjectAttendanceScreenViewModel>()
    val subjectAttendanceAddDialogState by viewModel.addAttendanceDialogState.collectAsState()
    val subjectAttendancePieChartState by viewModel.subjectAttendancePieChartState.collectAsState()
    val subjectAttendanceListState by viewModel.subjectAttendanceListState.collectAsState()
    val scope = rememberCoroutineScope()
    Scaffold(
        topBar = {
           TopBarWithDeleteIcon(title = viewModel.topBarSubjectNameState,
               onDeleteButtonClick = {
                                     viewModel.deleteSubjectDialogStateShow = true
                                     },
               onNavigateBack = {
                   onNavigateBack()
               })
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(MaterialTheme.colorScheme.inverseOnSurface)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                ) {
                    Button(
                        onClick = {
                                  viewModel.onSubjectAttendanceDialogShow(show = true)
                        },
                        modifier = Modifier
                            .weight(0.5f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF087E0D)
                        )
                    ) {
                        Text(text = "Add Attendance")
                    }
                }
            }
        }
    ) {
        if(viewModel.deleteSubjectAttendanceDialogStateShow){
            DeleteSubjectAttendanceDialog(viewModel = viewModel)
        }

        if(subjectAttendanceAddDialogState.show){
            AddAttendanceDialogBox(viewModel = viewModel, subjectAttendanceAddDialogState = subjectAttendanceAddDialogState)
        }
        if(viewModel.deleteSubjectDialogStateShow){
            DeleteSubjectDialog(viewModel = viewModel, onNavigateBack = onNavigateBack)
        }
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            PieChartSubject(
                data = mapOf(
                    Pair("Sample-1", subjectAttendancePieChartState.presentLecture),
                    Pair("Sample-2", subjectAttendancePieChartState.absentLecture)
                ),
                modifier = Modifier.padding(top = 20.dp),
                percentage = subjectAttendancePieChartState.percentage
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
                            text = "${subjectAttendancePieChartState.presentLecture}/${subjectAttendancePieChartState.totalLecture}",
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
                            text = "Attendance",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                if(subjectAttendanceListState.subjectAttendanceList.isEmpty()){
                    NoDataFound()
                }else {
                    LazyColumn() {
                        items(items = subjectAttendanceListState.subjectAttendanceList) {
                            AttendanceSubjectDisplayItem(attendanceItem = it, viewModel = viewModel)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AttendanceSubjectDisplayItem(
    modifier: Modifier = Modifier,
    attendanceItem: AttendanceSubjectItem,
    viewModel: ViewSubjectAttendanceScreenViewModel
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
                .clickable { }
                .padding(start = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
            ) {
                Text(
                    text = attendanceItem.attendance,
                    fontSize = 16.sp,
                    color = if (attendanceItem.attendance.equals("Present", true)) {
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
                    modifier = Modifier,
                    color = if (attendanceItem.attendance.equals("Present", true)) {
                        Color(0xFF108A15)
                    } else {
                        Color(0xD5D61F1F)
                    },
                    fontWeight = FontWeight.Bold
                )
                IconButton(
                    onClick = {
                              viewModel.deleteSubjectAttendanceDialogStateShow = true
                        viewModel.deleteAttendanceDataState = attendanceItem
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
fun DeleteSubjectAttendanceDialog(
    modifier: Modifier = Modifier,
    viewModel: ViewSubjectAttendanceScreenViewModel
) {
    AlertDialog(
        onDismissRequest = {
                            viewModel.deleteSubjectAttendanceDialogStateShow = false
                           },
        confirmButton = {
                        TextButton(onClick = {
                            viewModel.onDeleteAttendance()
                            viewModel.deleteSubjectAttendanceDialogStateShow = false
                        }) {
                            Text(text = "Delete")
                        }
        },
        dismissButton = {
               TextButton(onClick = {
                   viewModel.deleteSubjectAttendanceDialogStateShow = false
               }) {
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
fun DeleteSubjectDialog(
    viewModel: ViewSubjectAttendanceScreenViewModel,
    onNavigateBack: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { viewModel.deleteSubjectDialogStateShow = false },
        confirmButton = {
            TextButton(onClick = {
                viewModel.onSubjectDeleteButtonClick()
                onNavigateBack()
                viewModel.deleteSubjectDialogStateShow = false
            }) {
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = { viewModel.deleteSubjectDialogStateShow = false }) {
                Text(text = "Cancel")
            }
        },
        icon = {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null,
                tint = Color.Red
            )
        },
        title = {
            Text(
                text = "Delete subject?",
                fontWeight = FontWeight.W500
            )
        },
        text = {
            Text(
                text = "This will delete subject and there all attendance record. After delete you lose all data of this subject.",
                fontWeight = FontWeight.W400
            )
        }
    )
}
@Composable
fun PieChartSubject(
    modifier: Modifier = Modifier,
    data: Map<String, Int>,
    radiusOuter: Dp = 90.dp,
    chartBarWidth: Dp = 20.dp,
    animDuration: Int = 1000,
    percentage : Double
) {

    val totalSum = data.values.sum()
    val floatValue = mutableListOf<Float>()

    // To set the value of each Arc according to
    // the value given in the data, we have used a simple formula.
    // For a detailed explanation check out the Medium Article.
    // The link is in the about section and readme file of this GitHub Repository
    data.values.forEachIndexed { index, values ->
        floatValue.add(index, 360 * values.toFloat() / totalSum.toFloat())
    }

    // add the colors as per the number of data(no. of pie chart entries)
    // so that each data will get a color
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

        // Pie Chart using Canvas Arc
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
                    text = "$percentage%",
                    fontSize = 30.sp,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
//    Text(text = "Hello")
}
