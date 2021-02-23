package telran.logs.bugs.dto.programmer;

import javax.validation.constraints.*;

public class ProgrammerDto {
    @Min(1)
    public long id;
    @NotEmpty
    public String name;
    @Email
    public String email;

    public ProgrammerDto(@Min(1) long id, @NotEmpty String name, @Email String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }


}
