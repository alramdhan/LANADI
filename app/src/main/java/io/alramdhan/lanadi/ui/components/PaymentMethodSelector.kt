package io.alramdhan.lanadi.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
        modifier = modifier.fillMaxWidth()
            .background(
                if(isSelected)
                    MaterialTheme.colorScheme.surfaceContainer
                else
                    MaterialTheme.colorScheme.surface
            ),
        border = if(isSelected) BorderStroke(2.dp, color = MaterialTheme.colorScheme.primary) else null,
        onClick = onClick
    ) {
        ListItem(
            headlineContent = { Text(metode.label, style = MaterialTheme.typography.titleLarge) },
            trailingContent = {
                Icon(metode.icon, contentDescription = "icon ${metode.label}", Modifier.size(40.dp))
            }
        )
    }
}