package com.app.ktorcrud.ui

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.app.ktorcrud.R
import com.app.ktorcrud.response.Data
import com.app.ktorcrud.utils.AllEvents
import com.app.ktorcrud.viewmodel.LoginViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter

/**
 * Created by Priyanka.
 */

@Composable
fun LoadUsers(loginViewModel: LoginViewModel, isNetworkAvailable: Boolean) {
    val state by loginViewModel.users.collectAsState()
    val textState = remember { mutableStateOf(TextFieldValue("")) }

    LaunchedEffect(isNetworkAvailable) {
        if (!isNetworkAvailable) {
            loginViewModel.users.tryEmit(AllEvents.StringResource(R.string.noInternet))
        } else {
            loginViewModel.getUsers()
        }
    }

    when (state) {
        is AllEvents.Loading -> {
            ProgressDialog(showDialog = (state as AllEvents.Loading).loading)
        }

        is AllEvents.StringResource -> {
            Toast.makeText(
                LocalContext.current,
                (state as AllEvents.StringResource).asString().toString(),
                Toast.LENGTH_SHORT
            ).show()
        }

        is AllEvents.Success<*> -> {
            UserActivity((state as AllEvents.Success<*>).data as Flow<PagingData<Data>>, textState)
            ProgressDialog(showDialog = false)
        }

        else -> {
            ProgressDialog(showDialog = false)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserActivity(userData: Flow<PagingData<Data>>, searchText: MutableState<TextFieldValue>) {
    val userList=userData.collectAsLazyPagingItems()
    Box {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            CompositionLocalProvider(
                LocalOverscrollConfiguration provides null
            ) {
                SearchView(state = searchText)
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.padding(10.dp)
                ) {
                    items(userList.itemCount) { i ->
                        val data = userList[i]
                        Row(modifier = Modifier.fillMaxHeight()) {
                            Image(
                                painter = rememberAsyncImagePainter(
                                    data?.avatar
                                ),
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(80.dp, 80.dp)
                                    .clip(CircleShape)
                            )
                            Text(
                                text = "${data?.first_name} ${data?.last_name}",
                                color = Color.Red,
                                modifier = Modifier
                                    .align(
                                        Alignment.CenterVertically
                                    )
                                    .padding(start = 20.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SearchView(state: MutableState<TextFieldValue>) {
    TextField(value = state.value, onValueChange = { value ->
        state.value = value
    }, modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp), leadingIcon = {
        Icon(
            Icons.Default.Search,
            contentDescription = "",
            modifier = Modifier
                .padding(15.dp)
                .size(24.dp)
        )
    }, trailingIcon = {
        if (state.value != TextFieldValue("")) {
            IconButton(onClick = {
                state.value =
                    TextFieldValue("")
            }) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(15.dp)
                        .size(24.dp)
                )
            }
        }
    }, singleLine = true, shape = CircleShape, colors = TextFieldDefaults.textFieldColors(
        textColor = Color.White,
        cursorColor = Color.White,
        leadingIconColor = Color.White,
        trailingIconColor = Color.White,
        backgroundColor = colorResource(id = R.color.orange),
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent
    )
    )
}


@Preview
@Composable
fun SearchPreview() {
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    SearchView(textState)
}