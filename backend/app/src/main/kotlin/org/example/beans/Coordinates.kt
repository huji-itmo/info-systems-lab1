import jakarta.persistence.Embeddable
import jakarta.validation.constraints.Max

@Embeddable
data class Coordinates(
    val x: Long,
    @field:Max(343)
    val y: Float,
)
