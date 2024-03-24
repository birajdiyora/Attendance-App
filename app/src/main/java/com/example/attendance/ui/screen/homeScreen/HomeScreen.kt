package com.example.attendance.ui.screen.homeScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.attendance.R
import com.example.attendance.TopBar
import com.example.attendance.data.Subject
import com.example.attendance.data.SubjectItem
import com.example.attendance.util.StudentListState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToSubjectScreen : () -> Unit,
    navigateToViewAttendanceScreen : () -> Unit,
    navigateToViewSubjectAttendanceScreen : (Subject) -> Unit,
    navigateToAboutUsScreen : () -> Unit
) {
    val viewModel = hiltViewModel<HomeScreenViewModel>()
    val studentListState by viewModel.studentList.collectAsState()
    val subjectListState by viewModel.subjectListState.collectAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

        val scope = rememberCoroutineScope()

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                    ModalDrawerSheet {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(0.15f),
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(0.3f)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.academic),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(115.dp)
                                            .fillMaxWidth()
                                    )
                                }
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .weight(0.7f),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    studentListState.studentList?.get(0)?.let {
                                        Text(
                                            text = it.name,
                                            fontSize = 20.sp,
                                            overflow = TextOverflow.Ellipsis,
                                            softWrap = false,
                                            style = MaterialTheme.typography.displayMedium,
                                            modifier = Modifier
                                                .padding(start = 15.dp)
                                        )
                                    }
                                }
                            }
                            Divider(
                                color = Color.Black,
                                modifier = Modifier
                                    .padding(vertical = 10.dp),
                                thickness = 0.dp
                            )
                            Column(
                                modifier = Modifier
                                    .weight(0.85f),
                            ) {
                                NavigationDrawerItem(
                                    label = { Text(text = "Home") },
                                    selected = true,
                                    onClick = {  scope.launch {
                                        drawerState.close()
                                    }},
                                    icon = {
                                        Icon(imageVector = Icons.Outlined.Home,
                                            contentDescription = null)
                                    }
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                NavigationDrawerItem(
                                    label = { Text(text = "Subjects") },
                                    selected = false,
                                    onClick = {
                                        navigateToSubjectScreen()
                                        scope.launch {
                                            drawerState.close()
                                        }
                                              },
                                    icon = {
                                        Icon(painter = painterResource(id = R.drawable.book),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(23.dp))
                                    }
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                NavigationDrawerItem(
                                    label = { Text(text = "Overall Attendance") },
                                    selected = false,
                                    onClick = {
                                        navigateToViewAttendanceScreen()
                                        scope.launch {
                                        drawerState.close()
                                    } },
                                    icon = {
                                        Icon(painter = painterResource(id = R.drawable.view),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(23.dp))
                                    }
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                NavigationDrawerItem(
                                    label = { Text(text = "About Us") },
                                    selected = false,
                                    onClick = {
                                        navigateToAboutUsScreen()
                                        scope.launch {
                                        drawerState.close()
                                    }},
                                    icon = {
                                        Icon(painter = painterResource(id = R.drawable.about),
                                            contentDescription = null,
                                            modifier = Modifier.size(23.dp))
                                    }
                                )
                            }
                        }
                    }
            }
        ) {
            Scaffold(
                topBar = {
                    Surface(
                        shadowElevation = 3.dp,
                    ) {
                        CenterAlignedTopAppBar(
                            modifier = Modifier,
                            navigationIcon = {
                                IconButton(onClick = {
                                    scope.launch {
                                        drawerState.open()
                                    }
                                }
                                ) {
                                    Icon(imageVector = Icons.Default.Menu,
                                        contentDescription = "")
                                }
                            },
                            title = {
                                Text(
                                    text = "Attendance",
                                    fontSize = 25.sp,
                                    style = MaterialTheme.typography.displayMedium
                                )
                            },
                            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                containerColor = MaterialTheme.colorScheme.inverseOnSurface
                            )
                        )
                    }
                }
            ) {
                if(subjectListState.subjectList.isEmpty()){
                    EmptyData()
                }else {
                    SubjectItem(
                        modifier = Modifier.padding(it),
                        subjects = subjectListState.subjectList,
                        navigateToViewSubjectAttendanceScreen = navigateToViewSubjectAttendanceScreen
                    )
                }
                }
        }
}

@Composable
fun EmptyData() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Opps!! There is not any subject.",
            fontSize = 25.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
            )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "To add subject press three line in top left " +
                    "corner then go to subject page and add your subjects.",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            fontSize = 17.sp
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SubjectItem(
    modifier: Modifier = Modifier,
    subjects : List<Subject>,
    navigateToViewSubjectAttendanceScreen : (Subject) -> Unit
) {
//    SubjectDialogBox()

    LazyColumn(modifier = modifier){
        items(items = subjects){
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(8.dp)
                    .clickable { navigateToViewSubjectAttendanceScreen(it) },
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 3.dp
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.fillMaxHeight().weight(0.6f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.book_subject),
                            contentDescription = null,
                            modifier = Modifier
                                .size(30.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = it.subjectName,
                            overflow = TextOverflow.Ellipsis,
                            softWrap = false,
                            fontSize = 18.sp,
                            style = MaterialTheme.typography.displayMedium
                        )
                    }
//            Spacer(modifier = Modifier.fillMaxWidth(1f))
                    Row(
                        modifier = Modifier
                            .fillMaxHeight(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier,
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = it.presentLecture.toString(),
                                modifier = Modifier
                                    .align(alignment = Alignment.CenterHorizontally),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF108A15)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Attended",
                                color = Color(0xFF108A15)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(
                            modifier = Modifier,
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = it.absentLecture.toString(),
                                modifier = Modifier
                                    .align(alignment = Alignment.CenterHorizontally),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xD5D61F1F)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Absent",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color(0xD5D61F1F)
                            )
                        }
                    }
                }
            }
        }
    }

}