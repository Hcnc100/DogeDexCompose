package com.nullpointer.dogedex.ui.screen.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nullpointer.dogedex.R
import com.nullpointer.dogedex.models.profile.ProfileData
import com.nullpointer.dogedex.ui.preview.config.SimplePreview

@Composable
fun ProfileInfo(
    profileData: ProfileData,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = stringResource(R.string.title_info_user),
            style = MaterialTheme.typography.h5
        )
        Text(
            text = stringResource(R.string.title_email_user, profileData.email)
        )
        Text(text = stringResource(R.string.title_dogs_caught, profileData.dogsCaught))
    }
}

@SimplePreview
@Composable
private fun ProfileInfoPreview() {
    ProfileInfo(profileData = ProfileData.preview)
}