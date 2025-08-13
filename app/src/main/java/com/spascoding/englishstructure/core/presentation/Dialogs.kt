package com.spascoding.englishstructure.core.presentation

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Dialog
import com.spascoding.englishstructure.R
import com.spascoding.englishstructure.core.constants.Padding

@Composable
fun AboutDialog(
    onDismissRequest: () -> Unit,
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            shape = RoundedCornerShape(Padding.MEDIUM),
        ) {
            Column(
                modifier = Modifier.padding(Padding.MEDIUM),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.app_name),
                    fontWeight = FontWeight.Bold,
                )

                Row(
                    modifier = Modifier.padding(vertical = Padding.SMALL)
                ) {
                    val context = LocalContext.current

                    val uriHandler = LocalUriHandler.current
                    OutlinedButton(onClick = { uriHandler.openUri("https://github.com/spasarnaudov/English-structure") }) {
                        Text(text = "Github")
                    }

                    IconButton(onClick = { startMailClient(context, "spasarnaudov@gmail.com") }) {
                        Icon(
                            imageVector = Icons.Filled.Email,
                            contentDescription = stringResource(R.string.contacts)
                        )
                    }

                    IconButton(onClick = { shareApp(context) }) {
                        Icon(
                            imageVector = Icons.Filled.Share,
                            contentDescription = stringResource(R.string.share)
                        )
                    }
                }

                Text(
                    text = stringResource(
                        R.string.app_version,
                        getAppVersion(LocalContext.current)
                    )
                )
            }
        }
    }
}

fun getAppVersion(context: Context): String {
    return try {
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        packageInfo.versionName.toString()
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        ""
    }
}

fun startMailClient(context: Context, emailAddress: String) {
    val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:$emailAddress")
    }
    if (emailIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(emailIntent)
    }
}

fun shareApp(context: Context) {
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        type = "text/plain"
        putExtra(
            Intent.EXTRA_SUBJECT,
            context.getString(R.string.i_found_this_amazing_app_you_should_try_it_out)
        )
        putExtra(
            Intent.EXTRA_TEXT,
            "https://play.google.com/store/apps/details?id=com.spascoding.englishstructure"
        )
    }
    val shareChooserIntent = Intent.createChooser(shareIntent, "Share via")
    if (shareIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(shareChooserIntent)
    }
}