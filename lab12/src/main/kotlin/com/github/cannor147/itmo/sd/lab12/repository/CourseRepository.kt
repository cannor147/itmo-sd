package com.github.cannor147.itmo.sd.lab12.repository

import kotlinx.coroutines.flow.Flow
import com.github.cannor147.itmo.sd.lab12.model.Course
import com.github.cannor147.itmo.sd.lab12.model.Currency
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CourseRepository : CoroutineCrudRepository<Course, Long> {
    fun findAllByFromInAndTo(from: List<Currency>, to: Currency): Flow<Course>
}