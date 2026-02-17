package io.alramdhan.lanadi.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.alramdhan.lanadi.domain.models.MetodePembayaran

@Composable
fun PaymentOptionCard(
    metode: MetodePembayaran,
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        border = if(isSelected) BorderStroke(2.dp, color = MaterialTheme.colorScheme.primary) else null,
        onClick = onClick,
        colors = CardColors(
            containerColor = if (isSelected)
                MaterialTheme.colorScheme.primaryContainer
            else
                MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = MaterialTheme.colorScheme.surface,
            disabledContentColor = Color.Gray,
        )
    ) {
        ListItem(
            modifier = Modifier.fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = ListItemColors(
                containerColor = Color.Transparent,
                leadingIconColor = Color.Transparent,
                trailingIconColor = Color.Transparent,
                headlineColor = MaterialTheme.colorScheme.onSurface,
                supportingTextColor = MaterialTheme.colorScheme.onSurface,
                overlineColor = Color.Black,
                disabledHeadlineColor = Color.Gray,
                disabledLeadingIconColor = Color.Gray,
                disabledTrailingIconColor = Color.Gray
            ),
            headlineContent = { Text(metode.label, style = MaterialTheme.typography.titleLarge) },
            leadingContent = {
                Box(
                    Modifier
                        .size(60.dp)
                        .background(
                            if(isSelected)
                                MaterialTheme.colorScheme.primary
                            else
                                Color.Gray,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = metode.icon,
                        contentDescription = "icon ${metode.label}",
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }
            },
            trailingContent = {
                RadioButton(
                    selected = isSelected,
                    onClick = {}
                )
            }
        )
    }
}
