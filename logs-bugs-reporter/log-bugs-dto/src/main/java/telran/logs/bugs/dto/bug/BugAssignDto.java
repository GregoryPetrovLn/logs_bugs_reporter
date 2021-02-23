package telran.logs.bugs.dto.bug;

import telran.logs.bugs.dto.enums.Seriousness;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Objects;

public class BugAssignDto extends BugDto {
    @Min(1)
    public long programmerId;

    public BugAssignDto(@NotNull Seriousness seriousness, @NotEmpty String description, LocalDate dateOpen, @Min(1) long programmerId) {
        super(seriousness, description, dateOpen);
        this.programmerId = programmerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BugAssignDto that = (BugAssignDto) o;
        return programmerId == that.programmerId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), programmerId);
    }
}
