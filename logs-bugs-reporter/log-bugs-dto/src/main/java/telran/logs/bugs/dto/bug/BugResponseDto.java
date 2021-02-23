package telran.logs.bugs.dto.bug;

import telran.logs.bugs.dto.enums.BugStatus;
import telran.logs.bugs.dto.enums.OpenningMethod;
import telran.logs.bugs.dto.enums.Seriousness;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

public class BugResponseDto extends BugAssignDto {
    public long bugId;
    public LocalDate dateClose;
    public BugStatus status;
    public OpenningMethod openingMethod;

    public BugResponseDto(long bugId,
                          @NotNull Seriousness seriousness,
                          @NotEmpty String description,
                          LocalDate dateOpen,
                          @Min(1) long programmerId,
                          LocalDate dateClose,
                          BugStatus status,
                          OpenningMethod openingMethod) {
        super(seriousness, description, dateOpen, programmerId);
        this.dateClose = dateClose;
        this.status = status;
        this.openingMethod = openingMethod;
        this.bugId = bugId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BugResponseDto that = (BugResponseDto) o;
        return bugId == that.bugId && Objects.equals(dateClose, that.dateClose) && status == that.status && openingMethod == that.openingMethod;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), bugId, dateClose, status, openingMethod);
    }
}

