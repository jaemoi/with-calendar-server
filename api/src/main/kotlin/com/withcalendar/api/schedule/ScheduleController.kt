package com.withcalendar.api.schedule

import com.withcalendar.api.schedule.model.DateRange
import com.withcalendar.api.schedule.model.DaySchedule
import com.withcalendar.api.schedule.model.ScheduleItem
import com.withcalendar.api.schedule.model.ScheduleListResponse
import com.withcalendar.application.common.ApiResponse
import com.withcalendar.application.domain.schedule.ScheduleCalendar
import com.withcalendar.application.domain.schedule.ScheduleService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@Tag(name = "Schedule", description = "스케줄 API")
@RestController
@RequestMapping("/schedule")
class ScheduleController(
    private val scheduleService: ScheduleService
) {

    @Operation(
        summary = "스케줄 목록 조회",
        description = "기준 날짜를 기준으로 주간 스케줄 목록을 조회합니다."
    )
    @GetMapping("/list")
    fun getScheduleList(): ResponseEntity<ApiResponse<ScheduleListResponse>> {
        val date = LocalDate.now()
        val scheduleCalendar = scheduleService.getScheduleCalendar(date)
        val response = toResponse(scheduleCalendar)
        return ResponseEntity.ok(ApiResponse.ok(response))
    }

    private fun toResponse(calendar: ScheduleCalendar): ScheduleListResponse {
        return ScheduleListResponse(
            baseDate = calendar.baseDate,
            range = DateRange(
                from = calendar.range.from,
                to = calendar.range.to
            ),
            days = calendar.days.map { day ->
                DaySchedule(
                    date = day.date,
                    dayOfWeek = day.dayOfWeek,
                    isToday = day.isToday,
                    schedules = day.schedules.map { schedule ->
                        ScheduleItem(
                            id = schedule.id,
                            userId = schedule.userId,
                            title = schedule.title,
                            startedAt = schedule.startedAt,
                            endedAt = schedule.endedAt
                        )
                    }
                )
            },
            tomorrowSchedule = calendar.tomorrowSchedule.map { schedule ->
                ScheduleItem(
                    id = schedule.id,
                    userId = schedule.userId,
                    title = schedule.title,
                    startedAt = schedule.startedAt,
                    endedAt = schedule.endedAt
                )
            }
        )
    }
}
