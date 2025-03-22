package com.nabssam.bestbook.presentation.ui.home.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nabssam.bestbook.R
import com.nabssam.bestbook.presentation.ui.components.ErrorScreen
import com.nabssam.bestbook.presentation.ui.components.TranslucentLoader

@Composable
fun HomeScreenRowItem(
    modifier: Modifier = Modifier,
    title: String,
    leadingIcon: ImageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,  // right
    trailingIcon: ImageVector = Icons.Default.Home,     // left
    onBookClick: () -> Unit,
    content: @Composable () -> Unit,
    @DrawableRes icon: Int,
    isLoading: Boolean = false,
    error: String? = null,
) {
    val containerModifier = Modifier
        .height(dimensionResource(R.dimen.book_height_home))
        .clip(RoundedCornerShape(8.dp))
    Column(
        modifier = modifier/*.clip(RoundedCornerShape(8.dp))*/
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(vertical = 12.dp)
                .clip(CircleShape)
                .fillMaxWidth()
                .background(
//                    color = MaterialTheme.colorScheme.surface,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.secondaryContainer,
                            MaterialTheme.colorScheme.surface
                        )
                    )
                ),
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(icon),
                contentDescription = "",
                modifier = Modifier
                    .padding(4.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(6.dp)
                    .size(24.dp)
            )
            Text(
                text = title, fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.weight(1f))

            IconButton(onClick = onBookClick) {
                Icon(imageVector = leadingIcon, contentDescription = "")
            }
        }
        error?.let {
            ErrorScreen(
                modifier = containerModifier, message = it
            )
        } ?: if (isLoading) TranslucentLoader(
            modifier = containerModifier
//                .height(dimensionResource(R.dimen.banner_height))
//                .clip(shape = RoundedCornerShape(8.dp))
        )
        else content()
    }

}