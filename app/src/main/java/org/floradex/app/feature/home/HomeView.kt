package org.floradex.app.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.floradex.app.designsystem.theme.FloraDexTheme

@Composable
fun HomeView(
    onGoToIdentifierClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to the Home View")
        Button(onClick = onGoToIdentifierClick) {
            Text(text = "Go to Identifier")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeViewPreview() {
    FloraDexTheme {
        HomeView(
            onGoToIdentifierClick = { }
        )
    }
}
