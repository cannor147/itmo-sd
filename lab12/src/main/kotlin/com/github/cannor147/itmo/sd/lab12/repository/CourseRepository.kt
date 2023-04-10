package com.github.cannor147.itmo.sd.lab12.repository

import com.github.cannor147.itmo.sd.lab12.model.Course
import com.github.cannor147.itmo.sd.lab12.model.Currency
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CourseRepository : JpaRepository<Course, Long> {
    fun findAllByFromInAndTo(from: List<Currency>, to: Currency): List<Course>
}