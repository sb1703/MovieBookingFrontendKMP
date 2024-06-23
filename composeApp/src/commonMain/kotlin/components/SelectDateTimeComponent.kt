package components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDate

@Composable
fun SelectDateTimeComponent(
    isDate: Boolean,
    selectedDateTime: Int,
    updateSelectedDateTime: (Int) -> Unit,
    dates: List<String>,
    times: List<String>
) {

    var start by remember { mutableStateOf(0) }
//    println("SelectDateTimeComponent: $dates isDate: $isDate")
//    println("SelectDateTimeComponent: $times  isDate: $isDate")

    Row(
        modifier = Modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
//            verticalAlignment = Alignment.CenterVertically
    ) {
        if((isDate && dates.isNotEmpty()) || (!isDate && times.isNotEmpty())) {
            IconButton(
                onClick = {
                    if(start > 0) {
                        start -= 1
                    }
                },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        }

        if(isDate && dates.isNotEmpty()) {
            dates.slice(start..if(start+4 < dates.size) start+4 else dates.size-1).forEachIndexed { index, date ->
                DateTimeButton(
                    weekDay = LocalDate.parse(date).dayOfWeek.name.take(3),
                    numDay = date.takeLast(2),
                    idx = index,
                    selectedDateTime = selectedDateTime,
                    updateSelectedDateTime = { updateSelectedDateTime(it) },
                    isDate = isDate
                )
            }
        } else if(!isDate && times.isNotEmpty()) {
            times.slice(start..if(start+4 < times.size) start+4 else times.size-1).forEachIndexed { index, time ->
                DateTimeButton(
                    time = time.split('-')[0].take(5),
                    idx = index,
                    selectedDateTime = selectedDateTime,
                    updateSelectedDateTime = { updateSelectedDateTime(it) },
                    isDate = isDate
                )
            }
        }

        if((isDate && dates.isNotEmpty()) || (!isDate && times.isNotEmpty())) {
            IconButton(
                onClick = {
                    if(start < dates.size-5) {
                        start += 1
                    }
                },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Forward",
                    tint = Color.White
                )
            }
        }
    }

}