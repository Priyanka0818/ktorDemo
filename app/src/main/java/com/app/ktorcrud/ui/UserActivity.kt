package com.app.ktorcrud.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.app.ktorcrud.response.Data
import com.app.ktorcrud.utils.AllEvents
import com.app.ktorcrud.viewmodel.LoginViewModel

/**
 * Created by Priyanka.
 */

@Composable
fun loadUsers(loginViewModel: LoginViewModel){
    loginViewModel.getUsers()
    val state by loginViewModel.users.collectAsState()
    when (state) {
        is AllEvents.Loading -> {
            ProgressDialog(showDialog = true)
        }
        is AllEvents.Success<*> -> {
            UserActivity((state as AllEvents.Success<*>).data as ArrayList<Data>)
            ProgressDialog(showDialog = false)
        }
        else -> {
            ProgressDialog(showDialog = false)
        }
    }
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserActivity(userData: ArrayList<Data>) {
    Box() {
        Column() {
            CompositionLocalProvider(
                LocalOverscrollConfiguration provides null
            ) {
                LazyColumn() {
                    itemsIndexed(userData) { index, item ->
                        Row(modifier = Modifier.fillMaxHeight()) {
                            Image(
                                painter = rememberAsyncImagePainter(
                                    item.avatar
                                ),
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(150.dp,150.dp)
                                    .clip(CircleShape)
                            )
                            Text(text = item.first_name!!, color = Color.Red, modifier = Modifier.align(
                                Alignment.CenterVertically).padding(start = 20.dp))
                        }
                    }
                }
            }
        }
    }
}