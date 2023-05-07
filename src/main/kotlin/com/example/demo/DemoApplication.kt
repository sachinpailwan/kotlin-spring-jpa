package com.example.demo




import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

import org.springframework.data.jpa.repository.Query


import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
	runApplication<DemoApplication>(*args)
}

@RestController
class MessageResource(val service:MessageService) {

	@GetMapping("/")
	fun index(): List<Message> = listOf( Message(UUID.randomUUID(),"Hello"), Message(UUID.randomUUID(),"hola!"))

	@PostMapping("/")
	fun postMethod(@RequestBody  message: Message ) = service.post(message)
}



@Service
class MessageService(val db:MessageRepository) {
	fun findMessages():List<Message> = db.findMessages()

	fun post(message: Message) = db.save(message)
}

interface MessageRepository : CrudRepository<Message, String>{
	@Query ("select * from messages", nativeQuery = true)
	fun findMessages(): List<Message>
}

@Entity
@Table(name = "messages")
data class Message (@Id var uuid: UUID = UUID.randomUUID(), val text:String)