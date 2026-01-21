package com.withcalendar.application.domain.schedule

import java.time.LocalDate

interface ScheduleService {
    fun getScheduleCalendar(baseDate: LocalDate): ScheduleCalendar
}
