package com.example.attendance.ui.screen.viewSubjectAttendanceScreen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.datastore.dataStore
import com.example.attendance.util.SubjectAttendanceAddDialogState
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddAttendanceDialogBox(
    modifier: Modifier = Modifier,
    viewModel: ViewSubjectAttendanceScreenViewModel,
    subjectAttendanceAddDialogState: SubjectAttendanceAddDialogState
) {

        Dialog(
            onDismissRequest = { viewModel.onSubjectAttendanceDialogShow(false) },
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            )
        ) {
           Surface(
               modifier = Modifier
                   .fillMaxWidth()
                   .padding(0.dp)
                   .clip(shape = RoundedCornerShape(20.dp))
           ) {
               Column(
                   modifier = Modifier
                       .fillMaxWidth()
                       .padding(vertical = 15.dp)
               ) {
                   var selectedDate by remember { mutableStateOf(LocalDate.now()) }
                   val calendar = Calendar.getInstance()
                   val today = LocalDate.now()

                   // Create a list of valid dates (e.g., allow selecting dates up to 7 days from today)
                   val enabledDates = List(7) { today.plusDays(it.toLong()) }
//                   calendar.set(1990, 0, 22)
                   val dateState = rememberDatePickerState(
                       initialSelectedDateMillis = calendar.timeInMillis,
                   )

                   DatePicker(
                       state = dateState,
                       dateFormatter = DatePickerDefaults.dateFormatter(

                       ),
                       modifier = Modifier.fillMaxWidth(),
                       title = {
                           Column(
                               modifier = Modifier
                                   .fillMaxWidth()
                           ) {
                               Text(
                                   text = "Add Attendance",
                                   style = MaterialTheme.typography.displayMedium,
                                   fontSize = 25.sp,
                                   modifier = Modifier
                                       .align(alignment = Alignment.CenterHorizontally)
                               )
                               Text(
                                   text = subjectAttendanceAddDialogState.subjectName,
                                   style = MaterialTheme.typography.displayMedium,
                                   fontSize = 30.sp,
                                   color = Color(0xFF108A15),
                                   modifier = Modifier
                                       .align(alignment = Alignment.CenterHorizontally)
                               )
                               Spacer(modifier = Modifier.height(8.dp))
                           }
                       },
                       showModeToggle = false
                   )
                   Row(
                       modifier = Modifier
                           .fillMaxWidth()
                           .padding(horizontal = 8.dp)
                   ) {
                       Button(
                           onClick = {
                               dateState.selectedDateMillis?.let { viewModel.addAttendance(attendance = 0,date = it) }
                           },
                           modifier = Modifier
                               .weight(0.5f),
                           colors = ButtonDefaults.buttonColors(
                               containerColor = Color(0xD5D61F1F)
                           )
                       ) {
                           Text(text = "Absent")
                       }
                       Spacer(modifier = Modifier.width(8.dp))
                       Button(
                           onClick = {
                               dateState.selectedDateMillis?.let { viewModel.addAttendance(attendance = 1,date = it) }
                                     },
                           modifier = Modifier
                               .weight(0.5f),
                           colors = ButtonDefaults.buttonColors(
                               containerColor = Color(0xFF108A15)
                           )
                       ) {
                           Text(text = "Present")
                       }
                       
                   }
               }
           }
        }
//    AlertDialog(
//        modifier = Modifier
//            .fillMaxWidth(),
//        onDismissRequest = {  },
//        confirmButton = { /*TODO*/ },
//        title = {
//            Column(
//                modifier = Modifier
//            ) {
//                Text(
//                    text = "Add Attendance",
//                    style = MaterialTheme.typography.displayMedium,
//                    fontSize = 25.sp,
//                    modifier = Modifier
//                        .align(alignment = Alignment.CenterHorizontally)
//                )
//                Text(
//                    text = "Java",
//                    style = MaterialTheme.typography.displayMedium,
//                    fontSize = 30.sp,
//                    color = Color(0xFF108A15),
//                    modifier = Modifier
//                        .align(alignment = Alignment.CenterHorizontally)
//                )
//                Divider(thickness = 1.dp, modifier = Modifier.padding(8.dp))
//            }
//        },
//        icon = {
//            Icon(
//                painter = painterResource(id = R.drawable.attendance),
//                contentDescription = null,
//                modifier = Modifier
//                    .size(30.dp))
//        },
//        text = {
//            Column {
//                val selectedDate = remember { mutableStateOf<LocalDate?>(LocalDate.now().minusDays(3)) }
//
//                val datestate = rememberDatePickerState()
////                DatePicker(state = datestate)
////                CalendarDialog(
////                    state = rememberUseCaseState(visible = true, true, onCloseRequest = closeSelection),
////                    config = CalendarConfig(
////                        yearSelection = true,
////                        style = CalendarStyle.WEEK,
////                    ),
////                    selection = CalendarSelection.Date(
////                        selectedDate = selectedDate.value
////                    ) { newDate ->
////                        selectedDate.value = newDate
////                    }
////                )
//            }
//        }
//    )
}