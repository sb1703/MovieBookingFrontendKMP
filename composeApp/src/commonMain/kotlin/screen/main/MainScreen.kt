package screen.main

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import components.CustomDrawer
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import domain.model.CustomDrawerState
import domain.model.User
import domain.model.isOpened
import domain.model.opposite
import screen.home.HomeScreen
import screen.settings.SettingsScreen
import screen.ticket.TicketScreen
import tab.BottomBarNavigation

class MainScreen(val user: User): Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val mainViewModel = getScreenModel<MainViewModel>()

        val hazeState = remember { HazeState() }
        var selectedTabIndex by rememberSaveable { mutableStateOf(0) }
        var drawerState by rememberSaveable { mutableStateOf(CustomDrawerState.Closed) }

        LaunchedEffect(Unit) {
            mainViewModel.setUser(user)
        }

        BoxWithConstraints(
            modifier = Modifier.fillMaxSize(),
            propagateMinConstraints = true
        ) {

            val screenWidth = remember { derivedStateOf { constraints.maxWidth } }
            val offsetValue by remember { derivedStateOf { (screenWidth.value/4.5).dp } }
            val animatedOffset by animateDpAsState(
                targetValue = if (drawerState.isOpened()) offsetValue else 0.dp,
                label = "Animated Offset"
            )
            val animatedScale by animateFloatAsState(
                targetValue = if (drawerState.isOpened()) 0.9f else 1f,
                label = "Animated Scale"
            )

            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
                    .statusBarsPadding()
                    .navigationBarsPadding()
                    .fillMaxSize()
            ) {
                CustomDrawer(
                    name = user.name,
                    image = user.profilePhoto,
                    onCloseClick = {
                        drawerState = CustomDrawerState.Closed
                    }
                )
            }

            Scaffold(
                modifier = Modifier
                    .offset(x = animatedOffset)
                    .scale(scale = animatedScale)
                    .fillMaxSize()
                    .clickable(enabled = drawerState == CustomDrawerState.Opened) {
                        drawerState = CustomDrawerState.Closed
                    },
                bottomBar = { BottomBarNavigation(hazeState, selectedTabIndex, drawerState) { selectedTabIndex = it } },
            ) { padding ->
                Box(
                    modifier = if(!drawerState.isOpened())
                    Modifier
                        .haze(
                            state = hazeState,
                            style = HazeDefaults.style(
                                backgroundColor = MaterialTheme.colorScheme.background,
                                tint = Color.Black.copy(alpha = 0.2f),
                                blurRadius = 30.dp
                            )
                        )
                        .fillMaxSize()
                    else Modifier
                ) {
                    when(selectedTabIndex) {
                        0 -> HomeScreen(user._id).Content()
                        1 -> TicketScreen(user._id).Content()
                        2 -> SettingsScreen().Content()
                    }
                    IconButton(
                        modifier = Modifier
                            .statusBarsPadding()
                            .size(48.dp),
                        onClick = {
                            drawerState = drawerState.opposite()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu Icon",
                            tint = Color.White
                        )
                    }
                }
            }
        }

//        BoxWithConstraints(
//            modifier = Modifier.fillMaxSize(),
//            propagateMinConstraints = true
//        ) {
//            val screenWidth = remember { derivedStateOf { constraints.maxWidth } }
//            val offsetValue by remember { derivedStateOf { (screenWidth.value / 4.5).dp } }
//            val animatedOffset by animateDpAsState(
//                targetValue = if (drawerState.isOpened()) offsetValue else 0.dp,
//                label = "Animated Offset"
//            )
//            val animatedScale by animateFloatAsState(
//                targetValue = if (drawerState.isOpened()) 0.9f else 1f,
//                label = "Animated Scale"
//            )
//
//            Box(
//                modifier = Modifier
//                    .background(MaterialTheme.colorScheme.surface)
//                    .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
//                    .statusBarsPadding()
//                    .navigationBarsPadding()
//                    .fillMaxSize()
//            ) {
//                CustomDrawer(
//                    onCloseClick = {
//                        drawerState = CustomDrawerState.Closed
//                    }
//                )
//            }
//
//            Scaffold(
//                modifier = Modifier
//                    .offset(x = animatedOffset)
//                    .scale(scale = animatedScale)
//                    .fillMaxSize()
//                    .clickable(enabled = drawerState == CustomDrawerState.Opened) {
//                        drawerState = CustomDrawerState.Closed
//                    },
//                bottomBar = {
//                    Box(
//                        modifier = Modifier
//                            .haze(
//                                state = hazeState,
//                                style = HazeDefaults.style(
//                                    backgroundColor = MaterialTheme.colorScheme.background,
//                                    tint = Color.Black.copy(alpha = 0.2f),
//                                    blurRadius = 30.dp
//                                )
//                            )
//                            .border(10.dp, Color.Red)
//                    ) {
////                        Box(
////                            modifier = Modifier
////                                .matchParentSize()
////                                .haze(
////                                    state = hazeState,
////                                    style = HazeDefaults.style(
////                                        backgroundColor = MaterialTheme.colorScheme.background,
////                                        tint = Color.Black.copy(alpha = 0.2f),
////                                        blurRadius = 30.dp
////                                    )
////                                )
////                                .border(10.dp, Color.Black)
////                        )
//                        BottomBarNavigation(hazeState, selectedTabIndex) {
//                            selectedTabIndex = it
//                        }
//                    }
//                },
//                topBar = {
//                    TopAppBar(
//                        title = { Text(text = "Top App Bar") },
//                        navigationIcon = {
//                            IconButton(
//                                modifier = Modifier.size(48.dp),
//                                onClick = {
//                                    drawerState = drawerState.opposite()
//                                }
//                            ) {
//                                Icon(
//                                    imageVector = Icons.Default.Menu,
//                                    contentDescription = "Menu Icon"
//                                )
//                            }
//                        }
//                    )
//                }
//            ) { padding ->
//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                ) {
//                    when (selectedTabIndex) {
//                        0 -> HomeScreen().Content()
//                        1 -> TicketScreen().Content()
//                        2 -> SettingsScreen().Content()
//                    }
//                }
//            }
//        }
    }
}