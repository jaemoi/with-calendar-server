package com.withcalendar.application.domain.schedule

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface ScheduleRepository : JpaRepository<ScheduleEntity, Long> {
    
    @Query("""
        SELECT s FROM ScheduleEntity s 
        WHERE s.startedAt >= :fromDateTime AND s.startedAt < :toDateTime
        ORDER BY s.startedAt ASC
    """)
    fun findSchedulesByDateRange(
        @Param("fromDateTime") fromDateTime: LocalDateTime,
        @Param("toDateTime") toDateTime: LocalDateTime
    ): List<ScheduleEntity>
}
