package com.example.technicaltestgca.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.technicaltestgca.domain.model.Point

@Composable
fun DrawPolygonEditor(
    modifier: Modifier = Modifier,
    points: List<Point>,
    onPointMoved: (index: Int, newPoint: Point) -> Unit
) {
    Canvas(modifier = modifier) {
        if (points.size > 1) {
            for (i in points.indices) {
                val start = points[i]
                val end = points[(i + 1) % points.size]
                drawLine(
                    color = Color.Black,
                    start = Offset(start.x, start.y),
                    end = Offset(end.x, end.y),
                    strokeWidth = 4f
                )
            }
        }

        points.forEachIndexed { index, point ->
            drawCircle(
                color = Color.Red,
                center = Offset(point.x, point.y),
                radius = 15f
            )
        }
    }

    points.forEachIndexed { index, point ->
        Box(
            modifier = Modifier
                .offset(x = point.x.dp, y = point.y.dp)
                .size(30.dp)
                .background(Color.Transparent)
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        val newPoint = point.copy(x = point.x + delta.toInt())
                        onPointMoved(index, newPoint)
                    }
                )
                .draggable(
                    orientation = Orientation.Vertical,
                    state = rememberDraggableState { delta ->
                        val newPoint = point.copy(y = point.y + delta.toInt())
                        onPointMoved(index, newPoint)
                    }
                )
        )
    }
}