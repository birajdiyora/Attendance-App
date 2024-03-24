package com.example.attendance.ui.screen.subjectsScreen


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.attendance.util.SubjectListState

@Composable
fun SubjectsScreen(
    onNavigationBack : () -> Unit
) {
    val viewModel = hiltViewModel<SubjectsScreenViewModel>()
    val subjectListState by viewModel.subjectListState.collectAsState()
    Scaffold(
        topBar = {
            Surface(
                shadowElevation = 3.dp,
            ) {
                TopBar("Subjects", onNavigationBack = onNavigationBack)
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.addSubjectDialogShowStateChange(true)},
                containerColor = MaterialTheme.colorScheme.tertiaryContainer
            ) {
                Icon(imageVector = Icons.Default.Add,
                    contentDescription = null)
            }
        }
    ) {
        if(subjectListState.subjectList.isEmpty()){
            EmptySubjectData()
        }else{
            SubjectDisplay(modifier = Modifier.padding(it), viewModel = viewModel, subjectListState = subjectListState)
        }

        if(viewModel.addSubjectDialogShow){
            AddSubjectDialogBox(viewModel = viewModel)
        }
        if(viewModel.editSubjectNameDialogShow){
            EditSubjectNameDialog(viewModel = viewModel, subject = viewModel.currentSubjectOfEditDialog)
        }
    }
}

@Composable
fun EmptySubjectData() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "There is not any subject.",
            fontSize = 25.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "To add subject press Add icon located in bottom right corner.",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            fontSize = 17.sp
        )
    }
}

@Composable
fun SubjectDisplay(
    modifier: Modifier = Modifier,
    viewModel: SubjectsScreenViewModel,
    subjectListState: SubjectListState
) {
//    val subjects = listOf<SubjectItem>(
//        SubjectItem(1,"Java",10,12),
//        SubjectItem(2,"Kotlin",8,8),
//        SubjectItem(3,"RDBMS",8,8),
//        SubjectItem(1,"Java",10,12),
//        SubjectItem(2,"Kotlin",8,8),
//        SubjectItem(3,"RDBMS",8,8)
//    )
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier) {
        items(items = subjectListState.subjectList) {
            SubjectItm(subject = it, viewModel = viewModel)
        }
    }
}

@Composable
fun SubjectItm(
    subject: Subject,
    viewModel : SubjectsScreenViewModel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .height(100.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.outlineVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = subject.subjectName,
                fontSize = 25.sp,
                overflow = TextOverflow.Ellipsis,
                softWrap = false,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(top = 12.dp, start = 6.dp, end = 6.dp)
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(28.dp),
                shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiary
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            viewModel.editSubjectNameDialogShowStateChange(true, subject)
                            viewModel.editSubjectNameDialogEditText = subject.subjectName
//                            viewModel.currentSubjectOfEditDialog = subject
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Edit",
                        modifier = Modifier,
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}

@Composable
fun AddSubjectDialogBox(
    viewModel: SubjectsScreenViewModel
) {
    AlertDialog(
        onDismissRequest = { viewModel.addSubjectDialogShowStateChange(false) },
        confirmButton = {
                        TextButton(onClick = {
                            viewModel.addSubject(Subject(subjectName = viewModel.addSubjectEditTextState))

                        }) {
                            Text(text = "Add")
                        }
        },
        dismissButton = {
            TextButton(onClick = { viewModel.addSubjectDialogShowStateChange(false) }) {
                Text(text = "Cancel")
            }
        },
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.add_button),
                contentDescription = null,
                modifier = Modifier
                    .size(45.dp)
            )
        },
        title = {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Add Subject")
                HorizontalDivider(
                    thickness = 1.dp,
                    modifier = Modifier
                        .padding(12.dp)
                )
            }
        },
        text = {
            OutlinedTextField(
                value = viewModel.addSubjectEditTextState,
                onValueChange = {
                                viewModel.addSubjectEditTextState = it
                },
                label = {
                        Text(text = "Subject Name")
                },
                shape = RoundedCornerShape(8.dp),
                supportingText = {
                    Text(
                        text = viewModel.addSubjectDialogTextErrorText,
                        color = Color.Red
                    )
                },
                singleLine = true
            )
        }

    )
}
@Composable
fun EditSubjectNameDialog(
    modifier: Modifier = Modifier,
    viewModel : SubjectsScreenViewModel,
    subject: Subject
) {
    AlertDialog(
        onDismissRequest = { viewModel.editSubjectNameDialogShowStateChange(false,Subject()) },
        confirmButton = { 
                       TextButton(onClick = { viewModel.updateSubject(subjectId = subject.subjectId) }) {
                           Text(text = "Change")
                       }
        },
        dismissButton = {
                        TextButton(onClick = { viewModel.editSubjectNameDialogShowStateChange(false,Subject()) }) {
                            Text(text = "Cancel")
                        }
        },
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.edit),
                contentDescription = null,
                modifier = Modifier
                    .size(35.dp)
            )
        },
        title = {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Change Name"
                )
                HorizontalDivider(
                    thickness = 1.dp,
                    modifier = Modifier
                        .padding(12.dp)
                )
            }
        },
        text = {
            Column() {
                OutlinedTextField(
                    value = viewModel.editSubjectNameDialogEditText,
                    onValueChange = {
                                    viewModel.onEditSubjectNameEditTextChange(it)
                    },
                    shape = RoundedCornerShape(8.dp),
                    label = {
                        Text(text = "Name")
                    },
                    singleLine = true,
                    maxLines = 1
                )
            }
        }
    )
}


