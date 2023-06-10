package com.kotlinpractice.demo.controller


import com.kotlinpractice.demo.model.Employee
import com.kotlinpractice.demo.repo.EmployeeGetRepository
import com.kotlinpractice.demo.repo.EmployeeRepository
import kotlinx.coroutines.*
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.TransactionStatus
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.interceptor.TransactionAspectSupport
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*
import kotlin.system.measureTimeMillis


@RestController
class TestController(
    @Autowired private val employeeRepository: EmployeeRepository,
    @Autowired private val employeeGetRepository: EmployeeGetRepository,
) {

    @GetMapping("employees")
    fun getEmployeeByName(): ResponseEntity<List<Employee>> {
        val employees = employeeGetRepository.findAll()
        return ResponseEntity(employees.toList(), HttpStatus.OK)
    }

    @GetMapping("employees/get")
    suspend fun modify(): ResponseEntity<Any>? {
        var listget: List<Employee>;
        val scope = CoroutineScope(Dispatchers.Unconfined)
        val time = measureTimeMillis {
                val employees = employeeRepository.findAll()
                println("Employee: ${employees.also { println("Thread: ${Thread.currentThread()}")  }}")
                val newEmployees: List<Employee> = employees.map { it.apply { name = "iii" } }
                var first : Deferred<Optional<Employee>>?
                var second : Deferred<Optional<Employee>>?
                var third : Deferred<Optional<Employee>>?
                runBlocking {
                    first = scope.async {
                        delay(1000)
                        println("Thread: ${Thread.currentThread()}. x")
                        employeeRepository.findById(1L)

                    }

                    second = scope.async {
                        delay(1000)
                        println("Thread: ${Thread.currentThread()}. y")
                        employeeRepository.findById(2L)

                    }

                    third = scope.async {
                        delay(1000)
                        println("Thread: ${Thread.currentThread()}. z")
                        employeeRepository.findById(3L)


                    }
                }
                    println("Start to calculate")
                    val x = first!!.await().get()
                    val y = second!!.await().get()
                    val z = third!!.await().get()
                    listget = listOf(x, y, z)

//            }
        }
        println("Time calculated: $time")
            return ResponseEntity(listget, HttpStatus.OK)
    }

//    @GetMapping("employees/isolationAttack")
//    fun isolationAttack(): ResponseEntity<Any>? {
//        val employee1 = getEmployee().get()
//        println("middle read: $employee1")
//        return ResponseEntity("", HttpStatus.OK)
//    }

    @GetMapping("employees/isolationTest")
    @Transactional
    fun isolationTest(): ResponseEntity<Any>? {
        updateEmployee()
        val employee2 = employeeRepository.findById(1L).get()
        println("third read: $employee2")
        return ResponseEntity("", HttpStatus.OK)
    }

//    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
//    fun getEmployee() : Optional<Employee> {
//        return employeeGetRepository.findById(1L)
//    }

     @Transactional
    fun updateEmployee() {
        val employee = employeeRepository.findById(1L).get()
        println("First read: $employee")

         //val employee1 = getEmployee().get()
         val newEmployee = Employee(employee.id, ((employee.name!!.toInt() + 1)).toString(), employee.email)
         employeeRepository.saveAndFlush(newEmployee)
         val employee1 = employeeGetRepository.findById(1L).get()
         println("middle read: $employee1")
         val employee2 = employeeRepository.findById(1L).get()
         println("second read: $employee2")
//        employeeRepository.save(employee)


    }

    @GetMapping("employees/get1")
    suspend fun modify1(): ResponseEntity<Any>? {

        coroutineScope {
            println("1")
            val first = async {
                delay(5000)
                println("${Thread.currentThread().name}: I am lazy and launched only now")
                "Hello, "
            }

            println("2")
            val second = async {
                delay(5000)
                println("${Thread.currentThread().name}: I am eager!")
                "world!"
            }
            println("3")
            runBlocking {
                println(first.await() + second.await())
            }
            println("4")
        }
        return ResponseEntity.ok("Done")
    }

    @GetMapping("/employee/name/{name}")
    suspend fun getEmployeeByName(@PathVariable name: String?): ResponseEntity<Employee?>? {
        val employee = employeeRepository.findByName(name)
        return ResponseEntity(employee, HttpStatus.OK)
    }

    @GetMapping("/employee/email/{email}")
    suspend fun getEmployeeByEmail(@PathVariable email: String?): ResponseEntity<Employee?>? {
        val employee = employeeRepository!!.findByEmail(email)
        return ResponseEntity(employee, HttpStatus.OK)
    }

    @GetMapping("/employee/add")
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = [Exception::class])
    suspend fun addEmployee(@RequestBody names : List<String>): ResponseEntity<String> {
        val employees = employeeRepository.findAll()
        val supervisorJob = SupervisorJob()
        val scope = CoroutineScope(supervisorJob)
        var job1: Job? = null
        var job2: Job? = null
                employees.forEachIndexed { index, employee ->
                    if(index == 0) {
                        job1 = scope.launch {
//                            try {
                                throw Exception("Job has exeption")

//                            } catch (e : Exception){
//                                println("Exception handled ^^")
//                            }
                            }
                    } else {
                        job2 = scope.launch {
                            employee.name = names.get(index)
                            //if(index == 2) throw Exception("OMG")
                            println("Thread: ${Thread.currentThread()}")
                            employeeRepository.save(employee)
                            Thread.sleep(2000)
                        }




//                }
            }
                    println("Index $index : Job1 is active: ${job1?.isActive}")
                    println("Index $index : Job2 is active: ${job2?.isActive}")
                    println("Index $index : Scope is active: ${scope.isActive}")
        }
        return ResponseEntity.ok("Done")
    }

//    suspend fun sleep() {
//        delay(1000)
//        print("Done sleep")
//    }
//
//    suspend fun chargeIphone() {
//        delay(2000)
//        print("Done charging")
//    }
//
//    @GetMapping("/")
//    fun helloWorld(): Set<Student> =
//        setOf(
//            Student(1, "A", 8.0),
//            Student(1, "A", 8.0),
//            Student(1, "A", 8.0),
//            Student(1, "A", 8.0),
//            Student(1, "A", 8.0),
//            Student(1, "A", 8.0),
//            Student(1, "A", 8.0),
//            Student(1, "A", 8.0),
//        ).apply { print(this.size) }



}