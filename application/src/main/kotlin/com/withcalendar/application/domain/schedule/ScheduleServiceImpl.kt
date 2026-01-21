package com.withcalendar.application.domain.schedule

import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class ScheduleServiceImpl(
    private val scheduleRepository: ScheduleRepository
) : ScheduleService {

    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private val dayOfWeekFormatter = DateTimeFormatter.ofPattern("EEE", java.util.Locale.ENGLISH)
    private val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a", java.util.Locale.ENGLISH)

    override fun getScheduleCalendar(baseDate: LocalDate): ScheduleCalendar {
        val today = LocalDate.now()
        
        // 오늘을 기준으로 -3일 ~ +3일 범위 계산
        val from = today.minusDays(3)
        val to = today.plusDays(3)
        
        // LocalDate를 LocalDateTime으로 변환하여 조회
        val fromDateTime = from.atStartOfDay()
        val toDateTime = to.plusDays(1).atStartOfDay()
        
        // 범위 내 스케줄 조회 (JPA Entity)
        val scheduleEntities = scheduleRepository.findSchedulesByDateRange(fromDateTime, toDateTime)
        
        // Entity → Domain 모델 변환
        val schedules = scheduleEntities.map { entity ->
            Schedule(
                id = entity.id,
                userId = entity.userId,
                title = entity.title,
                startedAt = formatDateTime(entity.startedAt),
                endedAt = formatDateTime(entity.endedAt)
            )
        }
        
        // 날짜별로 그룹화 (총 7일: -3일 ~ +3일)
        val days = mutableListOf<DaySchedule>()
        for (i in 0..6) {
            val date = from.plusDays(i.toLong())
            val dateStr = date.format(dateFormatter)
            val dayOfWeek = date.format(dayOfWeekFormatter)
            val isToday = date == today
            
            // 해당 날짜의 스케줄 필터링
            val schedulesForDay = schedules.filter { schedule ->
                val scheduleDateStr = schedule.startedAt.substring(0, 10)
                scheduleDateStr == dateStr
            }
            
            days.add(
                DaySchedule(
                    date = dateStr,
                    dayOfWeek = dayOfWeek,
                    isToday = isToday,
                    schedules = schedulesForDay
                )
            )
        }
        
        // 내일 스케줄 조회
        val tomorrow = today.plusDays(1)
        val tomorrowFromDateTime = tomorrow.atStartOfDay()
        val tomorrowToDateTime = tomorrow.plusDays(1).atStartOfDay()
        val tomorrowEntities = scheduleRepository.findSchedulesByDateRange(tomorrowFromDateTime, tomorrowToDateTime)
        val tomorrowSchedules = tomorrowEntities.map { entity ->
            Schedule(
                id = entity.id,
                userId = entity.userId,
                title = entity.title,
                startedAt = formatDateTime(entity.startedAt),
                endedAt = formatDateTime(entity.endedAt)
            )
        }
        
        return ScheduleCalendar(
            baseDate = baseDate.format(dateFormatter),
            range = DateRange(
                from = from.format(dateFormatter),
                to = to.format(dateFormatter)
            ),
            days = days,
            tomorrowSchedule = tomorrowSchedules
        )
    }
    
    /**
     * LocalDateTime을 String 형식으로 변환
     * 전세계 서비스 고려: 필요시 타임존 변환 로직 추가 가능
     */
    private fun formatDateTime(dateTime: LocalDateTime): String {
        return dateTime.format(dateTimeFormatter)
    }
}
