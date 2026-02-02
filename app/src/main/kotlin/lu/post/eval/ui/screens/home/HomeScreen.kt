package lu.post.eval.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import lu.post.eval.R
import lu.post.eval.presentation.home.HomeUiState
import lu.post.eval.presentation.home.HomeViewModel
import lu.post.eval.ui.components.MovieListItem
import lu.post.eval.ui.theme.EvalTheme
import androidx.compose.foundation.lazy.itemsIndexed


/**
 * Home screen composable that displays the movie search interface.
 * Collects state from the ViewModel and delegates rendering to [HomeScreenContent].
 *
 * @param viewModel The ViewModel managing the screen state (injected by Hilt)
 */
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreenContent(
        uiState = uiState,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        onSearchClick = viewModel::searchMovies
    )
}

/**
 * Stateless content composable for the Home screen.
 * Separated from [HomeScreen] to enable Compose Preview support.
 *
 * @param uiState The current UI state to render
 * @param onSearchQueryChange Callback when the search text changes
 * @param onSearchClick Callback when the search button is clicked
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenContent(
    uiState: HomeUiState,
    onSearchQueryChange: (String) -> Unit,
    onSearchClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = uiState.searchQuery,
                    onValueChange = onSearchQueryChange,
                    placeholder = { Text(stringResource(R.string.search_hint)) },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                Button(onClick = onSearchClick) {
                    Text(stringResource(R.string.search_button))
                }
            }

            if (uiState.movies.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemsIndexed(
                        items = uiState.movies,
                        key = { index, movie -> "${movie.imdbId}_$index" }
                    ) { _, movie ->
                        MovieListItem(movie = movie)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    EvalTheme {
        HomeScreenContent(
            uiState = HomeUiState(searchQuery = "Star Wars"),
            onSearchQueryChange = {},
            onSearchClick = {}
        )
    }
}
