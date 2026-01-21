package com.withcalendar.api.schedule.model

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "스케줄 목록 응답")
data class ScheduleListResponse(
    @Schema(description = "기준 날짜", example = "2026-01-09")
    val baseDate: String,
    
    @Schema(description = "조회 범위")
    val range: DateRange,
    
    @Schema(description = "날짜별 스케줄 목록")
    val days: List<DaySchedule>,
    
    @Schema(description = "내일 스케줄 목록")
    val tomorrowSchedule: List<ScheduleItem>
)

@Schema(description = "날짜 범위")
data class DateRange(
    @Schema(description = "시작 날짜", example = "2026-01-06")
    val from: String,
    
    @Schema(description = "종료 날짜", example = "2026-01-12")
    val to: String
)

@Schema(description = "날짜별 스케줄")
data class DaySchedule(
    @Schema(description = "날짜", example = "2026-01-07")
    val date: String,
    
    @Schema(description = "요일", example = "Wed")
    val dayOfWeek: String,
    
    @Schema(description = "오늘 여부", example = "false")
    val isToday: Boolean,
    
    @Schema(description = "스케줄 목록")
    val schedules: List<ScheduleItem>
)

@Schema(description = "스케줄 항목")
data class ScheduleItem(
    @Schema(description = "스케줄 ID", example = "2")
    val id: Long,
    
    @Schema(description = "사용자 ID", example = "123")
    val userId: Long,
    
    @Schema(description = "제목", example = "등산모임", required = false)
    val title: String? = null,
    
    @Schema(description = "시작 시간", example = "2026-01-07 10:00:00 AM")
    val startedAt: String,
    
    @Schema(description = "종료 시간", example = "2026-01-07 13:00:00 PM")
    val endedAt: String
)
