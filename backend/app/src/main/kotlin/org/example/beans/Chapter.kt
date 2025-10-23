import jakarta.persistence.Embeddable
import jakarta.validation.constraints.*

@Embeddable
data class Chapter(

    @field:NotBlank
    val name: String,

    @field:Positive
    @field:Max(1000)
    val marinesCount: Long
)
