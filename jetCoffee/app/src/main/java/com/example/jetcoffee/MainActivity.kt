package com.example.jetcoffee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetcoffee.model.Menu
import com.example.jetcoffee.model.dummyBestSellerMenu
import com.example.jetcoffee.model.dummyCategory
import com.example.jetcoffee.model.dummyMenu
import com.example.jetcoffee.ui.components.*
import com.example.jetcoffee.ui.theme.JetCoffeeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetCoffeeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    JetCoffeeApp()
                }
            }
        }
    }
}

@Composable
fun JetCoffeeApp() {
    Scaffold(
        bottomBar = { BottomBar() }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            Banner()
            HomeSection(title = stringResource(id = R.string.section_category)) {
                CategoryRow()
            }
            HomeSection(title = stringResource(id = R.string.section_favorite_menu)) {
                MenuItemRow(items = dummyMenu)
            }
            HomeSection(title = stringResource(id = R.string.section_best_seller_menu)) {
                MenuItemRow(items = dummyBestSellerMenu)
            }
        }
    }
}

@Composable
fun HomeSection(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(modifier) {
        SectionText(title = title)
        content()
    }
}
@Composable
fun Banner(modifier: Modifier = Modifier) {
    Box(modifier = modifier){
        Image(
            painterResource(id = R.drawable.banner),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.height(160.dp)
        )
        SearchBar()
    }
}

@Composable
fun CategoryRow() {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(dummyCategory) { item ->
            CategoryItem(item)
        }
    }
}

@Composable
fun MenuItemRow(items: List<Menu>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp)
    ){
        items(items){ item ->
            MenuItem(item)
        }
    }
}
@Preview(showBackground = true)
@Composable
fun CategoryRowPreview() {
    JetCoffeeTheme {
       CategoryRow()
    }
}
@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun JetCoffeeAppPreview() {
    JetCoffeeTheme {
        JetCoffeeApp()
    }
}
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetCoffeeTheme {
        Greeting("Android")
    }
}