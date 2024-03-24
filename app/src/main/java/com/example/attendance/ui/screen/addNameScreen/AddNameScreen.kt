package com.example.attendance.ui.screen.addNameScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.attendance.data.Student

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNameScreen(
    navigateToHomeScreen : (Student) -> Unit
) {
    val viewModel = hiltViewModel<AddNameScreenViewModel>()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
    ) {
    Column(
        modifier = Modifier
            .weight(1f)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Attendance",
            fontSize = 50.sp
        )
    }
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
//            Text(
//                text = "Enter Your Name",
//                fontSize = 25.sp,
//                fontWeight = FontWeight.Medium
//                )
            OutlinedTextField(
                value = viewModel.fullNameTextFieldState.value,
                onValueChange = {
                    viewModel.fullNameTextFieldValueChange(it)
                },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.Transparent
                ),
                label = {
                    Text(
                        text = "Enter Your Full Name",
                        fontWeight = FontWeight.W500,
                        fontSize = 16.sp
                        )
                },
                singleLine = true,
                isError = false,
                supportingText = {
                    Text(
                        text = viewModel.fullNameTextFiledErrorState.value,
                        color = Color.Red,
                    )
                }
            )
            Spacer(modifier = Modifier.height(15.dp))
            Button(
                onClick = {
                    viewModel.onSubmitButtonClick()
                        navigateToHomeScreen(Student(name = viewModel.fullNameTextFieldState.value))
                          },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = "Submit",
                    fontSize = 18.sp
                    )
            }
        }
    }
}

