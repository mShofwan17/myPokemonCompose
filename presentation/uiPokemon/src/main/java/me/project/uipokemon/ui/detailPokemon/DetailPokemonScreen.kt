package me.project.uipokemon.ui.detailPokemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import me.project.domain.models.UiDetailPokemon
import me.project.domain.models.UiStats
import me.project.shared.extension.heightWeightCalculate
import me.project.shared.extension.toTypeColor
import me.project.shared.ui.Grass
import me.project.uipokemon.R
import me.project.uipokemon.components.BoxTitleSubtitleText
import me.project.uipokemon.components.CommonBox
import me.project.uipokemon.components.DialogInfo
import me.project.uipokemon.components.DialogLoading
import me.project.uipokemon.components.DialogRename
import me.project.uipokemon.components.MyButton
import me.project.uipokemon.components.MyClipText
import me.project.uipokemon.components.ProgressBarWithText

@Composable
fun DetailPokemonScreen(
    navHostController: NavHostController,
    viewModel: DetailPokemonViewModel = hiltViewModel()
) {
    val detailPokemon = viewModel.detailPokemon.collectAsState()
    val isMyPokemon = viewModel.isMyPokemon.collectAsState()
    val showDialog = viewModel.catch.collectAsState()
    val release = viewModel.release.collectAsState()
    val error = viewModel.errorMsg.collectAsState()
    val loading = viewModel.loading.collectAsState()

    var contentLoading by remember {
        mutableStateOf(false)
    }

    DetailContent(
        navHostController = navHostController,
        contentLoading = contentLoading,
        detailPokemon = detailPokemon,
        isMyPokemon = isMyPokemon,
        onEditClicked = { viewModel.showDialog() },
        onPrimaryButtonClicked = {
            it?.let { name ->
                contentLoading = true
                if (isMyPokemon.value) viewModel.releasePokemon(name)
                else viewModel.catchPokemon(name)
            }
        }
    )

    DetailScreenDialogs(
        navHostController = navHostController,
        release = release,
        showDialog = showDialog,
        uiDetailPokemon = detailPokemon.value,
        isLoading = loading.value,
        error = error,
        onContentLoadingChange = { contentLoading = it },
        onConfirmDialogRename = { name, nickname ->
            viewModel.resetState()
            viewModel.renamePokemon(name, nickname)
        },
        onResetStateDialog = {
            viewModel.resetState()

        }
    )

}

@Composable
private fun DetailContent(
    navHostController: NavHostController,
    contentLoading: Boolean,
    detailPokemon: State<UiDetailPokemon?>,
    isMyPokemon: State<Boolean>,
    onEditClicked: () -> Unit,
    onPrimaryButtonClicked: (name: String?) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(
                        start = 16.dp, end = 16.dp,
                        bottom = 16.dp, top = 6.dp
                    ),
            ) {
                detailPokemon.value?.let {
                    val colorByType = it.types[0].toTypeColor()
                    val modifier = Modifier.align(Alignment.BottomCenter)
                    var title = stringResource(R.string.catch_pokemon)
                    var backgroundColor = colorByType
                    var icon = Icons.Default.Star

                    if (isMyPokemon.value) {
                        title = stringResource(R.string.release_pokemon)
                        backgroundColor = Color.Black
                        icon = Icons.Default.Refresh
                    }

                    MyButton(
                        modifier = modifier,
                        title = title,
                        backgroundColor = backgroundColor,
                        icon = icon,
                        contentLoading = contentLoading,
                        onClick = {
                            onPrimaryButtonClicked.invoke(it.name)
                        },
                        isClickAble = !contentLoading
                    )
                }
            }

        }
    ) {
        detailPokemon.value?.let { detailPokemon ->
            val painterImage = rememberAsyncImagePainter(
                model = detailPokemon.imgUrl,
                error = rememberVectorPainter(image = Icons.Default.Close)
            )
            val detailPokemonStats = detailPokemon.stats
            val colorByType = detailPokemon.types[0].toTypeColor()
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .verticalScroll(rememberScrollState())
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp),
                        painter = painterResource(id = R.drawable.half_elips),
                        colorFilter = ColorFilter.tint(color = colorByType),
                        contentDescription = stringResource(R.string.half_elips),
                        contentScale = ContentScale.Crop
                    )
                    Icon(
                        modifier = Modifier
                            .size(50.dp)
                            .padding(top = 16.dp)
                            .clickable { navHostController.popBackStack() },
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = stringResource(R.string.ic_back),
                        tint = Color.White
                    )

                    Image(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 30.dp),
                        painter = painterImage,
                        contentDescription = stringResource(id = R.string.image_url),
                        contentScale = ContentScale.Fit
                    )
                }

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    text = detailPokemon.name,
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )

                detailPokemon.nickName?.let {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            modifier = Modifier.padding(end = 8.dp),
                            text = it,
                            color = Color.DarkGray,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center,
                        )

                        IconButton(
                            onClick = { onEditClicked.invoke() }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = stringResource(
                                    R.string.editname
                                )
                            )
                        }
                    }

                }

                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    detailPokemon.types.forEach {
                        MyClipText(
                            modifier = Modifier.padding(8.dp),
                            color = it.toTypeColor(), text = it
                        )
                    }
                }

                HorizontalDivider(
                    modifier = Modifier
                        .padding(top = 8.dp, start = 16.dp, end = 16.dp),
                    thickness = 1.dp,
                    color = Color.LightGray
                )

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    BoxTitleSubtitleText(
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(1f),
                        title = "${detailPokemon.weight.heightWeightCalculate()} KG",
                        subtitle = "Weight"
                    )

                    BoxTitleSubtitleText(
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(1f),
                        title = "${detailPokemon.height.heightWeightCalculate()} M",
                        subtitle = "Height"
                    )
                }

                StatsView(items = detailPokemonStats, trackColor = colorByType)

            }
        }
    }
}

@Composable
private fun StatsView(
    modifier: Modifier = Modifier,
    items: List<UiStats>,
    trackColor: Color
) {
    CommonBox(
        modifier = modifier
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 8.dp),
                text = "Stats",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            items.forEach { stats ->
                val color = when (stats.name) {
                    "hp" -> Color.Green
                    "attack" -> Color.Red
                    "defense" -> Color.DarkGray
                    "special-attack" -> Color.Magenta
                    "special-defense" -> Color.Cyan
                    else -> Color.Blue
                }

                ProgressBarWithText(
                    progress = stats.stat,
                    title = stats.name.uppercase(),
                    modifier = Modifier,
                    color = color.copy(alpha = 0.6f),
                    trackColor = trackColor
                )
            }
        }
    }
}

@Composable
fun DetailScreenDialogs(
    navHostController: NavHostController,
    release: State<Boolean?>,
    showDialog: State<Boolean?>,
    isLoading: Boolean,
    error: State<String?>,
    uiDetailPokemon: UiDetailPokemon?,
    onContentLoadingChange: (Boolean) -> Unit,
    onConfirmDialogRename: (name: String, nickname: String) -> Unit,
    onResetStateDialog: () -> Unit
) {

    release.value?.let {
        onContentLoadingChange.invoke(false)
        val title = if (it) stringResource(id = R.string.success_release)
        else stringResource(id = R.string.failed_release)

        val icon = if (it) Icons.Rounded.CheckCircle
        else Icons.Rounded.Clear

        DialogInfo(
            title = title,
            icon = icon,
            tintColor = if (it) Grass
            else Color.Red,
            onConfirmButton = {
                onResetStateDialog()
            }
        )
    }

    showDialog.value?.let {
        onContentLoadingChange.invoke(false)
        if (it) {
            DialogRename(
                context = LocalContext.current,
                detailPokemon = uiDetailPokemon,
                onConfirmButton = { name, nickName ->
                    onConfirmDialogRename(name, nickName)
                },
                onDismissButton = onResetStateDialog
            )

        } else {
            DialogInfo(
                title = stringResource(id = R.string.gagal_menangkap_pokemon),
                icon = Icons.Rounded.Clear,
                onConfirmButton = {
                    onResetStateDialog()
                }
            )
        }
    }

    DialogLoading(state = isLoading)
    error.value?.let {
        DialogInfo(
            title = it,
            icon = Icons.Rounded.Clear,
            onConfirmButton = {
                onResetStateDialog()
                navHostController.popBackStack()
            }
        )
    }

}