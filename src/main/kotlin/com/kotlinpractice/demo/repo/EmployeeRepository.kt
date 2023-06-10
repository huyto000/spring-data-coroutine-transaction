package com.kotlinpractice.demo.repo
import com.kotlinpractice.demo.model.Employee
import jakarta.persistence.LockModeType
import org.springframework.context.annotation.Configuration

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface EmployeeRepository : JpaRepository<Employee, Long> {
    override fun findById(id: Long): Optional<Employee>
    fun findByName(name: String?): Employee?
    fun findByEmail(email: String?): Employee?
}