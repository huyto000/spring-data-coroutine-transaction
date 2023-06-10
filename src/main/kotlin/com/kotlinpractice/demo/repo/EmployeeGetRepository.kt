package com.kotlinpractice.demo.repo;

import com.kotlinpractice.demo.model.Employee
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*


@Repository
interface EmployeeGetRepository : JpaRepository<Employee, Long> {


        @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
        override fun findById(id: Long): Optional<Employee>
        }
