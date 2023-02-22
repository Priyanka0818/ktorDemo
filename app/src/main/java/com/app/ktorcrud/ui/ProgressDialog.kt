package com.app.ktorcrud.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.app.ktorcrud.R

/**
 * Created by Priyanka.
 */

@Composable
fun ProgressDialog(showDialog: Boolean) {
    if (showDialog) {
        Dialog(onDismissRequest = { }) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(100.dp)
            ) {
                CircularProgressIndicator(color = colorResource(id = R.color.colorAccent))
            }
        }
    }
}