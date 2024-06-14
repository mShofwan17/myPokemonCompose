package me.project.mypokemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import me.project.mypokemon.ui.theme.MyPokemonTheme
import me.project.navigation.Screens
import me.project.navigation.SetupNavGraph

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyPokemonTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }
                    val navBackStackEntry by navController.currentBackStackEntryAsState()

                    when (navBackStackEntry?.destination?.route) {
                        Screens.Home.route -> bottomBarState.value = true
                        Screens.MyPokemon.route -> bottomBarState.value = true
                        else -> bottomBarState.value = false
                    }

                    Scaffold(
                        topBar = {
                            if (bottomBarState.value) MainTopBar()
                        },
                        bottomBar = {
                            Surface(
                                shadowElevation = 9.dp
                            ) {
                                if (bottomBarState.value) BottomNavigationApp(navController = navController)
                            }
                        }
                    ) { innerPadding ->
                        SetupNavGraph(navController, Modifier.padding(innerPadding))
                    }

                }
            }
        }
    }
}

@Composable
fun MainTopBar(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            painter = painterResource(id = me.project.uipokemon.R.drawable.half_elips),
            colorFilter = ColorFilter.tint(color = me.project.shared.ui.PrimaryColor),
            contentDescription = stringResource(me.project.uipokemon.R.string.half_elips),
            contentScale = ContentScale.FillWidth
        )

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .padding(top = 15.dp),
            painter = painterResource(id = me.project.uipokemon.R.drawable.pokemon),
            contentDescription = stringResource(id = me.project.uipokemon.R.string.image_url),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun BottomNavigationApp(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    AnimatedVisibility(
        visible = true,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
    ) {
        NavigationBar(
            modifier = modifier,
            containerColor = Color.White,
        ) {

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            val items = listOf(Screens.Home, Screens.MyPokemon)
            items.forEach { screen ->
                NavigationBarItem(
                    label = {
                        Text(text = screen.label)
                    },
                    icon = {
                        screen.icon?.let {
                            Icon(
                                imageVector = it,
                                contentDescription = stringResource(R.string.bottomicon)
                            )
                        }

                    },
                    selected = currentRoute == screen.route,
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedTextColor = Color.Gray,
                        selectedTextColor = me.project.shared.ui.PrimaryColor,
                        selectedIconColor = me.project.shared.ui.PrimaryColor,
                        unselectedIconColor = Color.Gray
                    ),
                )
            }
        }
    }
}

