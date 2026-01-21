package com.withcalendar.application.domain.schedule

import jakarta.persistence.*
import java.time.LocalDateTime

/**
 * 스케줄 조회용 Entity
 * DB 테이블과 1:1 매핑되는 조회 전용 Entity
 */
@Entity
@Table(name = "schedules")
data class ScheduleEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    
    @Column(name = "user_id", nullable = false)
    val userId: Long,
    
    @Column(name = "title")
    val title: String? = null,
    
    @Column(name = "started_at", nullable = false)
    val startedAt: LocalDateTime,
    
    @Column(name = "ended_at", nullable = false)
    val endedAt: LocalDateTime
)
