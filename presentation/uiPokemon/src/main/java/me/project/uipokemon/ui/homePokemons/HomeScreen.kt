package me.project.uipokemon.ui.homePokemons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import me.project.domain.models.UiPokemon
import me.project.navigation.Screens
import me.project.uipokemon.R
import me.project.uipokemon.components.ListGridContent

@Composable
fun HomeScreen(
    navHostController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val pokemons = viewModel.getPokemons.collectAsLazyPagingItems()
    val error = viewModel.errorMsg.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PagingResult(
            items = pokemons,
            onSuccess = {
                ListGridContent(
                    itemsPaging = pokemons,
                    onItemClick = {
                        navHostController.navigate(Screens.DetailPokemon.passName(it.name))
                    }
                )
            },
            onError = { throwable, refresh ->
                viewModel.errorMessage(throwable)
                error.value?.let { msg ->
                    ErrorPageHome(
                        message = msg,
                        onRefresh = {
                            viewModel.resetBaseState()
                            refresh.invoke()
                        }
                    )
                }
            },
            onLoading = {
                CircularProgressIndicator(
                    modifier = Modifier.size(50.dp),
                )
            }
        )
    }
}

@Composable
private fun PagingResult(
    items: LazyPagingItems<UiPokemon>,
    onSuccess: @Composable () -> Unit,
    onError: @Composable (error: Throwable, refresh: () -> Unit) -> Unit,
    onLoading: @Composable () -> Unit,
) {
    items.apply {
        when {
            loadState.append is LoadState.Error -> {
                onError((loadState.append as LoadState.Error).error) { refresh() }
            }

            loadState.refresh is LoadState.Loading -> {
                onLoading()
            }

            loadState.refresh is LoadState.Error -> {
                onError((loadState.refresh as LoadState.Error).error) { refresh() }
            }

            else -> onSuccess()

        }
    }
}

@Composable
fun ErrorPageHome(
    message: String,
    onRefresh: () -> Unit
) {
    Column(
        modifier = Modifier.padding(all = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier.size(150.dp),
            imageVector = Icons.Rounded.Clear,
            tint = Color.Red,
            contentDescription = stringResource(R.string.icon_info)
        )
        Spacer(modifier = Modifier.padding(top = 8.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = message,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.padding(top = 8.dp))

        Button(
            onClick = {
                onRefresh.invoke()
            }
        ) {
            Text(stringResource(R.string.refresh))
        }
    }
}