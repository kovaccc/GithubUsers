package com.example.githubusers.features.users.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.githubusers.R


@Composable
fun UserProfileImage(avatarUrl: String) {
    Image(
        painter = rememberAsyncImagePainter(avatarUrl),
        contentDescription = stringResource(R.string.profile_image_content_description),
        modifier = Modifier.size(128.dp),
        contentScale = ContentScale.Crop
    )
}