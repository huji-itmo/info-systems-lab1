import jakarta.persistence.*
import jakarta.validation.constraints.*
import java.time.LocalDateTime
import Coordinates

@Entity
@Table(name = "space_marines")
data class SpaceMarine(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:Positive
    val id: Int = 0,

    @field:NotBlank
    @Column(nullable = false)
    val name: String,

    @field:NotNull
    @Embedded
    val coordinates: Coordinates,

    @field:NotNull
    @Column(nullable = false, updatable = false)
    val creationDate: LocalDateTime = LocalDateTime.now(),

    @field:NotNull
    @Embedded
    val chapter: Chapter,

    @field:NotNull
    @field:Positive
    @Column(nullable = false)
    val health: Long,

    @Column
    val loyal: Boolean? = null,

    @Enumerated(EnumType.STRING)
    @Column
    val category: AstartesCategory? = null,

    @field:NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val weaponType: Weapon
)
