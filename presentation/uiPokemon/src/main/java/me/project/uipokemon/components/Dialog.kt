package me.project.uipokemon.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import me.project.domain.models.UiDetailPokemon
import me.project.uipokemon.R

@Composable
private fun BaseDialog(
    state: Boolean = true,
    content: @Composable () -> Unit,
    title: String,
    needToShowConfirmButton: Boolean = true,
    onConfirmButton: () -> Unit = {}
) {
    var showDialog by remember {
        mutableStateOf(true)
    }
    showDialog = state

    if (showDialog) {
        AlertDialog(
            containerColor = Color.White,
            onDismissRequest = { },
            title = { },
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    content.invoke()
                    Spacer(modifier = Modifier.padding(top = 8.dp))
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                }
            },
            confirmButton = {
                if (needToShowConfirmButton) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {

                            showDialog = false
                            onConfirmButton.invoke()
                        }
                    ) {
                        Text(stringResource(R.string.ok))
                    }
                }

            },
            dismissButton = {}
        )
    }

}

@Composable
fun DialogRename(
    context: Context,
    detailPokemon: UiDetailPokemon?,
    onConfirmButton: (name: String, nickName: String) -> Unit,
    onDismissButton: () -> Unit = {}
) {
    var text by remember { mutableStateOf("") }
    AlertDialog(
        containerColor = Color.White,
        onDismissRequest = { },
        title = {
            detailPokemon?.name?.let { name ->
                detailPokemon.nickName?.let {
                    Text(text = stringResource(R.string.change_nickname))
                } ?: run {
                    Text(text = stringResource(R.string.yeay_you_have_catch, name))
                }

            }
        },
        text = {
            Column {
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text(stringResource(R.string.pokemon_nickname)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    detailPokemon?.let {
                        if (text.isNotEmpty()) onConfirmButton.invoke(it.name, text)
                        else Toast.makeText(context,
                            context.getString(R.string.cannot_be_empty), Toast.LENGTH_SHORT).show()
                    }
                },
            ) {
                Text(stringResource(R.string.confirm))
            }
        },
        dismissButton = {
            detailPokemon?.nickName?.let {
                Button(
                    onClick = {
                        onDismissButton.invoke()
                    }
                ) {
                    Text(stringResource(R.string.cancel))
                }
            }

        }
    )
}

@Composable
fun DialogInfo(
    title: String,
    icon: ImageVector,
    tintColor: Color = Color.Red,
    onConfirmButton: () -> Unit
) {

    BaseDialog(
        content = {
            Icon(
                modifier = Modifier.size(150.dp),
                imageVector = icon,
                tint = tintColor,
                contentDescription = stringResource(R.string.icon_info)
            )
        },
        title = title,
        onConfirmButton = onConfirmButton
    )

}

@Composable
fun DialogLoading(
    state: Boolean
) {

    BaseDialog(
        content = {
            CircularProgressIndicator()
        },
        title = "Loading...",
        state = state,
        needToShowConfirmButton = false
    )
}