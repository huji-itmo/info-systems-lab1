package org.example.service

import jakarta.enterprise.context.ApplicationScoped
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.example.exceptions.NotFoundException
import org.example.model.AstartesCategory
import org.example.model.Page
import org.example.model.SpaceMarine
import org.example.model.Weapon
import org.example.model.requests.SpaceMarineCreateRequest
import org.example.model.requests.SpaceMarineUpdateRequest
import kotlin.math.ceil

@ApplicationScoped
open class SpaceMarineService {
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
        // Convert string values to enums safely
        val category =
            spaceMarine.category?.let {
                try {
                    AstartesCategory.valueOf(it.uppercase())
                } catch (e: IllegalArgumentException) {
                    throw IllegalArgumentException(
                        "Invalid category value: $it. " +
                            "Valid values are: ${AstartesCategory.values().joinToString()}",
                    )
                }
            }

        val weaponType =
            try {
                Weapon.valueOf(spaceMarine.weaponType.uppercase())
            } catch (e: IllegalArgumentException) {
                throw IllegalArgumentException(
                    "Invalid weapon type: ${spaceMarine.weaponType}. " +
                        "Valid values are: ${Weapon.values().joinToString()}",
                )
            }

        val entity =
            SpaceMarine(
                id = 0,
                name = spaceMarine.name,
                coordinatesId = spaceMarine.coordinatesId,
                chapterId = spaceMarine.chapterId,
                health = spaceMarine.health,
                loyal = spaceMarine.loyal,
                category = category,
                weaponType = weaponType,
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

        // Convert string values to enums with null safety
        val category =
            update.category?.let { categoryStr ->
                try {
                    AstartesCategory.valueOf(categoryStr.uppercase())
                } catch (e: IllegalArgumentException) {
                    throw IllegalArgumentException(
                        "Invalid category value: $categoryStr. " +
                            "Valid values are: ${AstartesCategory.values().joinToString()}",
                    )
                }
            } ?: existing.category

        val weaponType =
            update.weaponType?.let { weaponStr ->
                try {
                    Weapon.valueOf(weaponStr.uppercase())
                } catch (e: IllegalArgumentException) {
                    throw IllegalArgumentException(
                        "Invalid weapon type: $weaponStr. " +
                            "Valid values are: ${Weapon.values().joinToString()}",
                    )
                }
            } ?: existing.weaponType

        val updated =
            SpaceMarine(
                id = id,
                name = update.name ?: existing.name,
                coordinatesId = update.coordinatesId ?: existing.coordinatesId,
                chapterId = update.chapterId ?: existing.chapterId,
                health = update.health ?: existing.health,
                loyal = update.loyal ?: existing.loyal,
                category = category,
                weaponType = weaponType,
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
