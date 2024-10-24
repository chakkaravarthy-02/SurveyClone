package com.example.zohosurvey.screens.drawers

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.zohosurvey.viewmodels.DepartmentDrawerViewModel

@Composable
fun DepartmentDrawerContent(
    viewModel: DepartmentDrawerViewModel = viewModel(),
    onItemClicked: () -> Unit,
    onDepartmentClicked: () -> Unit
) {
    val widthDp = 400.dp

    Column(
        modifier = Modifier
            .width(widthDp)
            .fillMaxHeight()
            .padding(end = 60.dp)
            .verticalScroll(rememberScrollState())
            .background(Color.White)
    ) {
        IconButton(onClick = onDepartmentClicked) {
            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back")
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .background(Color.LightGray)
        ) {
            Text(
                text = "Saran Chakkaravarthy",
                modifier = Modifier
                    .padding(16.dp)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable {
                    viewModel.changeFirst()
                    onItemClicked()
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = "checked",
                tint = if (viewModel.firstDepartment.value) Color.Red else Color.Black
            )
            Text(
                color = if (viewModel.firstDepartment.value) Color.Red else Color.Black,
                text = "My department",
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
        ) {
            Text(
                text = "Saran Chakkaravarthy",
                modifier = Modifier
                    .padding(16.dp)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable {
                    viewModel.changeSecond()
                    onItemClicked()
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = "checked",
                tint = if (viewModel.secondDepartment.value) Color.Red else Color.Black
            )
            Text(
                color = if (viewModel.secondDepartment.value) Color.Red else Color.Black,
                text = "My department",
            )
        }
    }
}

@Preview
@Composable
private fun DepartmentPreview() {
    DepartmentDrawerContent(onItemClicked = {},
        onDepartmentClicked = {})
}