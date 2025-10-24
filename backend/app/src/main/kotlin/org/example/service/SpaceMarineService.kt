package org.example.service

import jakarta.enterprise.context.ApplicationScoped
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.example.exceptions.NotFoundException
import org.example.model.Page
import org.example.model.SpaceMarine
import org.example.model.requests.SpaceMarineCreateRequest
import org.example.model.requests.SpaceMarineUpdateRequest
import kotlin.math.ceil

@ApplicationScoped
open class SpaceMarineService {
    constructor()

    @PersistenceContext(unitName = "my-pu")
    private lateinit var em: EntityManager

    open fun findAll(
        page: Int,
        size: Int,
    ): Page<SpaceMarine> {
        val total = em.createQuery("SELECT COUNT(s) FROM SpaceMarine s", Long::class.java).singleResult
        val content =
            em
                .createQuery("SELECT s FROM SpaceMarine s", SpaceMarine::class.java)
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

    open fun findById(id: Int): SpaceMarine =
        em.find(SpaceMarine::class.java, id) ?: throw NotFoundException("SpaceMarine not found with id: $id")

    @Transactional
    open fun create(
        @Valid spaceMarine: SpaceMarineCreateRequest,
    ): SpaceMarine {
        val entity =
            SpaceMarine(
                id = 0,
                name = spaceMarine.name,
                coordinatesId = spaceMarine.coordinatesId,
                chapterId = spaceMarine.chapterId,
                health = spaceMarine.health,
                loyal = spaceMarine.loyal,
                category = spaceMarine.category,
                weaponType = spaceMarine.weaponType,
            )
        em.persist(entity)
        return entity
    }

    @Transactional
    open fun update(
        id: Int,
        @Valid update: SpaceMarineUpdateRequest,
    ): SpaceMarine {
        val existing = findById(id)
        val updated =
            SpaceMarine(
                id = id,
                name = update.name ?: existing.name,
                coordinatesId = update.coordinatesId ?: existing.coordinatesId,
                chapterId = update.chapterId ?: existing.chapterId,
                health = update.health ?: existing.health,
                loyal = update.loyal ?: existing.loyal,
                category = update.category ?: existing.category,
                weaponType = update.weaponType ?: existing.weaponType,
                creationDate = existing.creationDate, // immutable
            )
        em.merge(updated)
        return updated
    }

    @Transactional
    open fun delete(id: Int) {
        val entity = findById(id)
        em.remove(entity)
    }
}
