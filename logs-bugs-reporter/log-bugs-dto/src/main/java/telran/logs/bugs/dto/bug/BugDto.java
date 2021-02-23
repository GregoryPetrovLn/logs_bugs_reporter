package telran.logs.bugs.dto.bug;

import telran.logs.bugs.dto.enums.Seriousness;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Objects;

public class BugDto {
    @NotNull
    public Seriousness seriousness;
    @NotEmpty
    public String description;
    public LocalDate dateOpen;

    public BugDto(@NotNull Seriousness seriousness, @NotEmpty String description, LocalDate dateOpen) {
        this.seriousness = seriousness;
        this.description = description;
        this.dateOpen = dateOpen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BugDto bugDto = (BugDto) o;
        return seriousness == bugDto.seriousness && Objects.equals(description, bugDto.description) && Objects.equals(dateOpen, bugDto.dateOpen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seriousness, description, dateOpen);
    }

    @Override
    public String toString() {
        return "BugDto{" +
                "seriousness=" + seriousness +
                ", description='" + description + '\'' +
                ", dateOpen=" + dateOpen +
                '}';
    }
}
