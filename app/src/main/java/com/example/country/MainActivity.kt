package com.example.country

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.country.data.Country
import com.example.country.ui.theme.CountryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CountryTheme {
                CountryApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryTopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Liste des Pays",
                style = MaterialTheme.typography.headlineLarge
            )
        },
        modifier = modifier
    )
}

@Composable
fun CountryIcon(
    @DrawableRes flagResId: Int,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(flagResId),
        contentDescription = null,
        modifier = modifier
            .size(72.dp)
            .clip(CircleShape),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun CountryItem(country: Country, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CountryIcon(flagResId = country.flagResId)
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 12.dp)
                ) {
                    Text(
                        text = country.name,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "Capitale : ${country.capital}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                CountryItemButton(
                    expanded = expanded,
                    onClick = { expanded = !expanded }
                )
            }

            if (expanded) {
                Text(
                    text = "Code ISO : ${country.code}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
    }
}

@Composable
private fun CountryItemButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(onClick = onClick, modifier = modifier) {
        Icon(
            imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
            contentDescription = "Bouton d’expansion",
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
fun LandingScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "KARIBU",
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onContinueClicked
        ) {
            Text("Voir mes pays")
        }
    }
}

@Composable
fun CountryApp() {
    var shouldShowLanding by rememberSaveable { mutableStateOf(true) }

    if (shouldShowLanding) {
        LandingScreen(onContinueClicked = { shouldShowLanding = false })
    } else {
        Scaffold(
            topBar = { CountryTopAppBar() }
        ) { innerPadding ->
            LazyColumn(contentPadding = innerPadding) {
                items(getSampleCountries()) { country ->
                    CountryItem(country = country)
                }
            }
        }
    }
}

fun getSampleCountries(): List<Country> {
    return listOf(
        Country("France", "Paris", "FR", R.drawable.france),
        Country("Canada", "Ottawa", "CA", R.drawable.canada),
        Country("Japon", "Tokyo", "JP", R.drawable.japon),
        Country("Belgique", "Bruxelles", "BE", R.drawable.belgique),
        Country("Suisse", "Berne", "CH", R.drawable.suise),
        Country("Maroc", "Rabat", "MA", R.drawable.maroc),
        Country("Sénégal", "Dakar", "SN", R.drawable.senegale),
        Country("Brésil", "Brasília", "BR", R.drawable.bresil),
        Country("Allemagne", "Berlin", "DE", R.drawable.allmagne),
        Country("Italie", "Rome", "IT", R.drawable.itali)
    )
}

@Preview(showBackground = true)
@Composable
fun CountryPreview() {
    CountryTheme {
        CountryApp()
    }
}
