package com.withcalendar.application.domain.schedule

/**
 * 캘린더 조회 결과를 표현하는 도메인 조회 모델
 * DB Entity가 아닌 조회 전용 모델
 */
data class ScheduleCalendar(
    val baseDate: String,
    val range: DateRange,
    val days: List<DaySchedule>,
    val tomorrowSchedule: List<Schedule>
)

data class DateRange(
    val from: String,
    val to: String
)

data class DaySchedule(
    val date: String,
    val dayOfWeek: String,
    val isToday: Boolean,
    val schedules: List<Schedule>
)

data class Schedule(
    val id: Long,
    val userId: Long,
    val title: String?,
    val startedAt: String,
    val endedAt: String
)
