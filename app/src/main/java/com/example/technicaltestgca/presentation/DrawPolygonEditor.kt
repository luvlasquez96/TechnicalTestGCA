package com.example.technicaltestgca.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.example.technicaltestgca.domain.model.Point

@Composable
fun DrawPolygonEditor(
    modifier: Modifier = Modifier,
    points: List<Point>,
    onPointMoved: (index: Int, newPoint: Point) -> Unit
) {
    if (points.isEmpty()) return

    BoxWithConstraints(
        modifier = modifier
    ) {
        val canvasWidth = constraints.maxWidth.toFloat()
        val canvasHeight = constraints.maxHeight.toFloat()

        val density = LocalDensity.current
        val marginPx = with(density) { 5.dp.toPx() }

        val minX = points.minOfOrNull { it.x } ?: 0f
        val minY = points.minOfOrNull { it.y } ?: 0f
        val maxX = points.maxOfOrNull { it.x } ?: 0f
        val maxY = points.maxOfOrNull { it.y } ?: 0f

        val width = maxX - minX
        val height = maxY - minY
        val scaleX = (canvasWidth - 2 * marginPx) / width
        val scaleY = (canvasHeight - 2 * marginPx) / height
        val scale = minOf(scaleX, scaleY)

        val offsetX = ((canvasWidth - width * scale) / 2) - minX * scale
        val offsetY = ((canvasHeight - height * scale) / 2) - minY * scale

        Canvas(modifier = Modifier.fillMaxSize()) {
            points.forEachIndexed { index, point ->
                val start = point
                val end = points[(index + 1) % points.size]
                drawLine(
                    color = Color.Black,
                    start = Offset((start.x * scale) + offsetX, (start.y * scale) + offsetY),
                    end = Offset((end.x * scale) + offsetX, (end.y * scale) + offsetY),
                    strokeWidth = 4f
                )
                drawCircle(
                    color = Color.Red,
                    center = Offset((point.x * scale) + offsetX, (point.y * scale) + offsetY),
                    radius = 15f
                )
            }
        }

        points.forEachIndexed { index, point ->
            val pointOffsetX = with(density) { ((point.x * scale) + offsetX).toDp() }
            val pointOffsetY = with(density) { ((point.y * scale) + offsetY).toDp() }

            Box(
                modifier = Modifier
                    .offset(
                        x = pointOffsetX - 15.dp,
                        y = pointOffsetY - 15.dp
                    )
                    .size(30.dp)
                    .background(Color.Transparent)
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()

                            val newUnscaledX = point.x + dragAmount.x / scale
                            val newUnscaledY = point.y + dragAmount.y / scale

                            onPointMoved(index, Point(newUnscaledX, newUnscaledY))
                        }
                    }
            )
        }
    }
}