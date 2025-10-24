package org.example.service

import jakarta.enterprise.context.ApplicationScoped
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.example.exceptions.NotFoundException
import org.example.model.Coordinates
import org.example.model.Page
import kotlin.math.ceil

@ApplicationScoped
open class CoordinatesService {
    constructor()

    @PersistenceContext(unitName = "my-pu")
    private lateinit var em: EntityManager

    open fun findAll(
        page: Int,
        size: Int,
    ): Page<Coordinates> {
        val total = em.createQuery("SELECT COUNT(c) FROM Coordinates c", Long::class.java).singleResult
        val content =
            em
                .createQuery("SELECT c FROM Coordinates c", Coordinates::class.java)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .resultList

        return Page(
            content = content,
            page = page,
            size = size,
            totalElements = total,
            totalPages = ceil(total.toDouble() / size).toInt(),
        )
    }

    fun findById(id: Long): Coordinates =
        em.find(Coordinates::class.java, id) ?: throw NotFoundException("Coordinates not found with id: $id")

    @Transactional
    open fun create(
        @Valid coordinates: Coordinates,
    ): Coordinates {
        // Since Coordinates.id is auto-generated, we create a new instance without ID
        val entity =
            Coordinates(
                id = 0, // will be ignored due to @GeneratedValue
                x = coordinates.x,
                y = coordinates.y,
            )
        em.persist(entity)
        return entity
    }

    @Transactional
    open fun update(
        id: Long,
        @Valid coordinates: Coordinates,
    ): Coordinates {
        findById(id) // ensure exists
        val updated =
            Coordinates(
                id = id,
                x = coordinates.x,
                y = coordinates.y,
            )
        em.merge(updated)
        return updated
    }

    @Transactional
    open fun delete(id: Long) {
        val entity = findById(id)
        em.remove(entity)
    }
}
