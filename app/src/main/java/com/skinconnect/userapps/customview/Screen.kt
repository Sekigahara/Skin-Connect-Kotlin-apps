package com.skinconnect.userapps.customview

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val id:String,
    val title:String,
    val icon: ImageVector,
){

    object Schedule:Screen("Schedule","Schedule",Icons.Outlined.Schedule)
    object Home:Screen("Home","Home", Icons.Outlined.Home)
    object Profile:Screen("Profile","Profile",Icons.Outlined.Person)

    object Items{
        val list= listOf(
            Schedule,Home,Profile
        )
    }
}
