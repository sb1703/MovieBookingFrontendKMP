package tab

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarTab(val title: String, val icon: ImageVector, val color: Color) {
    data object Home: BottomBarTab("Home", Icons.Default.Home, Color(0xFFFA6FFF))
    data object Ticket: BottomBarTab("Ticket", Icons.Default.Person, Color(0xFFFFA574))
    data object Settings: BottomBarTab("Settings", Icons.Default.Settings, Color(0xFFADFF64))
}

val tabs = listOf(
    BottomBarTab.Home,
    BottomBarTab.Ticket,
    BottomBarTab.Settings
)