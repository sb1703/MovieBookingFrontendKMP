package components

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

val ShimmerLightGray = Color(0xFFF1F1F1)
val ShimmerMediumGray = Color(0xFFE3E3E3)
val ShimmerDarkGray = Color(0xFF1D1D1D)

@Composable
fun AnimatedShimmerItem(
    modifier: Modifier
) {
    val transition = rememberInfiniteTransition()
    val alphaAnim by transition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 500,
                easing = FastOutLinearInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )
    ShimmerItem(alpha = alphaAnim, modifier = modifier)
}

@Composable
fun ShimmerItem(
    alpha: Float,
    modifier: Modifier
) {
    Surface(
        color = if(isSystemInDarkTheme()) Color.Black else ShimmerLightGray,
        modifier = modifier
//            .width(250.dp)
//            .height(350.dp)
//            .clip(shape = TicketShape(cornerRadius = 100f))
//            .clip(shape = RoundedCornerShape(topStart = 100f, topEnd = 100f))
            .alpha(alpha)
    ) {  }
}

@Preview
@Composable
fun AnimatedShimmerItemPreview() {
    AnimatedShimmerItem(
        modifier = Modifier
    )
}