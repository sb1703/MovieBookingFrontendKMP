//package components
//
//import androidx.compose.foundation.Canvas
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.size
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableIntStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.geometry.Rect
//import androidx.compose.ui.geometry.Size
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.Color.Companion.LightGray
//import androidx.compose.ui.graphics.Path
//import androidx.compose.ui.graphics.drawscope.clipPath
//import androidx.compose.ui.graphics.drawscope.scale
//import androidx.compose.ui.graphics.drawscope.translate
//import androidx.compose.ui.graphics.vector.PathParser
//import androidx.compose.ui.unit.Dp
//import androidx.compose.ui.unit.dp
//import moviebooking.composeapp.generated.resources.Res
//import org.jetbrains.compose.resources.stringResource
//import org.jetbrains.compose.ui.tooling.preview.Preview
//
//val starColor = Color(0xFFFFC94D)
//
//@Composable
//fun RatingWidget(
//    modifier: Modifier,
//    rating: Double,
//    scaleFactor: Float = 3f,
//    spaceBetween: Dp = 6.dp
//) {
//    val result = calculateStars(rating = rating)
//
////    val starPathString = stringResource(id = R.string.star_path)
//    val starPathString = stringResource(Res.strings.star_path)
//    val starPath = remember {
//        PathParser().parsePathString(pathData = starPathString).toPath()
//    }
//    val starPathBounds = remember{
//        starPath.getBounds()
//    }
//
//    Row(
//        modifier = modifier,
//        horizontalArrangement = Arrangement.spacedBy(spaceBetween)
//    ) {
//        result["filledStars"]?.let {
//            repeat(it){
//                FilledStar(
//                    starPath = starPath,
//                    starPathBounds = starPathBounds,
//                    scaleFactor = scaleFactor
//                )
//            }
//        }
//        result["halfFilledStars"]?.let {
//            repeat(it){
//                HalfFilledStar(
//                    starPath = starPath,
//                    starPathBounds = starPathBounds,
//                    scaleFactor = scaleFactor
//                )
//            }
//        }
//        result["emptyStars"]?.let {
//            repeat(it){
//                EmptyStar(
//                    starPath = starPath,
//                    starPathBounds = starPathBounds,
//                    scaleFactor = scaleFactor
//                )
//            }
//        }
//    }
//
//}
//
//@Composable
//fun FilledStar(
//    starPath: Path,
//    starPathBounds: Rect,
//    scaleFactor: Float
//) {
//    Canvas(
//        modifier = Modifier
//            .size(24.dp)
//    ) {
//        val canvasSize = size
//        scale(scale = scaleFactor){
//            val pathWidth = starPathBounds.width
//            val pathHeight = starPathBounds.height
//            val left = (canvasSize.width/2f)-(pathWidth/1.7f)
//            val top = (canvasSize.height/2f)-(pathHeight/1.7f)
//            translate(left = left,top = top){
//                drawPath(
//                    path = starPath,
//                    color = starColor
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun HalfFilledStar(
//    starPath: Path,
//    starPathBounds: Rect,
//    scaleFactor: Float
//) {
//    Canvas(
//        modifier = Modifier
//            .size(24.dp)
//    ) {
//        val canvasSize = size
//        scale(scale = scaleFactor){
//            val pathWidth = starPathBounds.width
//            val pathHeight = starPathBounds.height
//            val left = (canvasSize.width/2f)-(pathWidth/1.7f)
//            val top = (canvasSize.height/2f)-(pathHeight/1.7f)
//            translate(left = left,top = top){
//                drawPath(
//                    path = starPath,
//                    color = LightGray.copy(alpha = 0.5f)
//                )
//                clipPath(path = starPath){
//                    drawRect(
//                        color = starColor,
//                        size = Size(
//                            width = starPathBounds.maxDimension/ 1.7f,
//                            height = starPathBounds.maxDimension * scaleFactor
//                        )
//                    )
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun EmptyStar(
//    starPath: Path,
//    starPathBounds: Rect,
//    scaleFactor: Float
//) {
//    Canvas(
//        modifier = Modifier
//            .size(24.dp)
//    ) {
//        val canvasSize = size
//        scale(scale = scaleFactor){
//            val pathWidth = starPathBounds.width
//            val pathHeight = starPathBounds.height
//            val left = (canvasSize.width/2f)-(pathWidth/1.7f)
//            val top = (canvasSize.height/2f)-(pathHeight/1.7f)
//            translate(left = left,top = top){
//                drawPath(
//                    path = starPath,
//                    color = LightGray.copy(alpha = 0.5f)
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun calculateStars(rating: Double): Map<String,Int> {
//    val maxStars by remember { mutableIntStateOf(5) }
//    var filledStars by remember { mutableIntStateOf(0) }
//    var halfFilledStars by remember { mutableIntStateOf(0) }
//    var emptyStars by remember { mutableIntStateOf(0) }
//
//    LaunchedEffect(key1 = rating){
//        val (firstNumber,lastNumber) = rating.toString()
//            .split(".")
//            .map { it.toInt() }
//        if(firstNumber in 0..5 && lastNumber in 0..9){
//            filledStars = firstNumber
//            if(lastNumber in 1..5){
//                halfFilledStars++
//            }
//            if(lastNumber in 6..9){
//                filledStars++
//            }
//            if(firstNumber == 5 && lastNumber > 0){
//                emptyStars = 5
//                filledStars = 0
//                halfFilledStars = 0
//            }
//        }
//    }
//    emptyStars = maxStars - (filledStars + halfFilledStars)
//    return mapOf(
//        "filledStars" to filledStars,
//        "halfFilledStars" to halfFilledStars,
//        "emptyStars" to emptyStars
//    )
//}
//
//@Preview()
//@Composable
//fun FilledStarPreview() {
//    val starPathString = stringResource(Res.drawable.star_path)
//    val starPath = remember {
//        PathParser().parsePathString(pathData = starPathString).toPath()
//    }
//    val starPathBounds = remember{
//        starPath.getBounds()
//    }
//
//    FilledStar(starPath = starPath, starPathBounds = starPathBounds,scaleFactor = 3f)
//}
//
//@Preview()
//@Composable
//fun HalfFilledStarPreview() {
//    val starPathString = stringResource(Res.drawable.star_path)
//    val starPath = remember {
//        PathParser().parsePathString(pathData = starPathString).toPath()
//    }
//    val starPathBounds = remember{
//        starPath.getBounds()
//    }
//
//    HalfFilledStar(starPath = starPath, starPathBounds = starPathBounds,scaleFactor = 3f)
//}
//
//@Preview()
//@Composable
//fun EmptyStarPreview() {
//    val starPathString = stringResource(Res.drawable.star_path)
//    val starPath = remember {
//        PathParser().parsePathString(pathData = starPathString).toPath()
//    }
//    val starPathBounds = remember{
//        starPath.getBounds()
//    }
//
//    EmptyStar(starPath = starPath, starPathBounds = starPathBounds,scaleFactor = 3f)
//}