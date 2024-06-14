package me.project.uipokemon.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.project.shared.ui.PrimaryColor

@Composable
fun ProgressBarWithText(
    progress: Int,
    title: String,
    modifier: Modifier = Modifier,
    color: Color,
    trackColor: Color
) {

    val max = 270
    val progressRatio = remember(progress, max) {
        progress.toFloat() / max.toFloat()
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            LinearProgressIndicator(
                progress = { progressRatio },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp),
                color = color,
                trackColor = trackColor.copy(alpha = 0.4f),
                strokeCap = StrokeCap.Round,
            )
            Text(
                text = title,
                color = Color.Black,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun CircularProgressBarWithText(
    progress: Int,
    title: String,
    modifier: Modifier = Modifier,
    color: Color = PrimaryColor
) {
    val max = 270
    val progressRatio = remember(progress, max) {
        progress.toFloat() / max.toFloat()
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        CircularProgressIndicator(
            progress = { progressRatio },
            modifier = Modifier.size(100.dp),
            color = color,
            trackColor = Color.White,
            strokeWidth = 16.dp,
        )
        Text(
            text = title,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            maxLines = 2
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0x9EFFFFFF)
@Composable
private fun Preview() {
    Column {
        ProgressBarWithText(
            progress = 101,
            title = "HP",
            modifier = Modifier.padding(16.dp),
            color = PrimaryColor,
            trackColor = PrimaryColor
        )

        CircularProgressBarWithText(
            progress = 100,
            title = "HP",
            modifier = Modifier.padding(16.dp)
        )

    }
}