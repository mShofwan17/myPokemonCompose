package me.project.uipokemon.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.rememberAsyncImagePainter
import me.project.domain.models.UiPokemon
import me.project.uipokemon.R

@Composable
fun ListGridContent(
    modifier: Modifier = Modifier,
    itemsList: List<UiPokemon>? = null,
    itemsPaging: LazyPagingItems<UiPokemon>? = null,
    onItemClick: (UiPokemon) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier
            .semantics { contentDescription = "ListContent" },
        columns = GridCells.Fixed(2)
    ) {
        itemsList?.let {
            items(it.size) { position ->
                ItemPokemon(
                    modifier = Modifier.padding(8.dp),
                    item = it[position],
                    onClick = { item -> onItemClick.invoke(item) }
                )
            }
        }

        itemsPaging?.let {
            items(
                count = it.itemCount,
                key = it.itemKey { item -> item.name }
            ) { position ->
                it[position]?.let { uipokemon ->
                    ItemPokemon(
                        modifier = Modifier.padding(8.dp),
                        item = uipokemon,
                        onClick = { item -> onItemClick.invoke(item) }
                    )
                }
            }
        }
    }
}

@Composable
fun ItemPokemon(
    modifier: Modifier = Modifier,
    item: UiPokemon,
    onClick: (UiPokemon) -> Unit
) {
    val painterImage = rememberAsyncImagePainter(
        model = item.imgUrl,
        error = rememberVectorPainter(image = Icons.Default.Close)
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(shape = RoundedCornerShape(12.dp))
            .clickable { onClick.invoke(item) }
            .semantics { contentDescription = "Item" },
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.White
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(all = 12.dp)
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 8.dp)
                    .height(120.dp),
                painter = painterImage,
                contentDescription = stringResource(R.string.image_url),
                contentScale = ContentScale.Inside
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                text = item.name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            item.nickName?.let {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp),
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0x9EFFFFFF)
@Composable
private fun ItemProductPrev() {
    val item = listOf(
        UiPokemon(
            name = "Bulbasaur",
            url = "",
            imgUrl = "https://img.pokemondb.net/artwork/bulbasaur.jpg",
        ),
        UiPokemon(
            name = "Bulbasaur",
            url = "",
            imgUrl = "https://img.pokemondb.net/artwork/bulbasaur.jpg",
        ),
    )

    val nicknameItems = listOf(
        UiPokemon(
            name = "Bulbasaur",
            url = "",
            imgUrl = "https://img.pokemondb.net/artwork/bulbasaur.jpg",
            nickName = "Jhonny - 0"
        ),
        UiPokemon(
            name = "Bulbasaur",
            url = "",
            imgUrl = "https://img.pokemondb.net/artwork/bulbasaur.jpg",
            nickName = "Saur - 1"
        ),
    )
    Column {
        ListGridContent(itemsList = item, onItemClick = {})
        ListGridContent(itemsList = nicknameItems, onItemClick = {})
    }


}