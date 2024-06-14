package me.project.uipokemon.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.project.shared.ui.PrimaryColor

@Composable
fun MyClipText(
    modifier: Modifier = Modifier,
    color: Color,
    text: String
) {
    Box(
        modifier = modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(12.dp))
            .background(color)
    ) {
        Text(
            modifier = Modifier
                .width(150.dp)
                .wrapContentWidth()
                .padding(12.dp),
            text = text,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun BoxTitleSubtitleText(
    modifier: Modifier = Modifier,
    color: Color = Color.LightGray,
    title: String,
    subtitle: String
) {
    CommonBox(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp),
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun CommonBox(
    modifier: Modifier = Modifier,
    color: Color = Color.LightGray,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(12.dp))
            .background(color)
    ) {
        content.invoke()
    }
}

@Preview(showBackground = true, backgroundColor = 0x9EFFFFFF)
@Composable
private fun Preview() {
    Column {
        MyClipText(color = PrimaryColor, text = "Grass")

        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            BoxTitleSubtitleText(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f),
                title = "95.KG", subtitle = "Weight"
            )
            BoxTitleSubtitleText(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f),
                title = "95.KG", subtitle = "Weight"
            )

        }
    }

}