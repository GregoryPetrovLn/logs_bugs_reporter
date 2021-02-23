package telran.logs.bugs.dto.bug;

import javax.validation.constraints.*;
import java.time.LocalDate;

public class CloseBugData {
    @Min(1)
    public long bugId;
    public LocalDate dateClose;
    public String description;

    public CloseBugData(@Min(1) long bugId, LocalDate dateClose, String description) {
        this.bugId = bugId;
        this.dateClose = dateClose;
        this.description = description;
    }
}
