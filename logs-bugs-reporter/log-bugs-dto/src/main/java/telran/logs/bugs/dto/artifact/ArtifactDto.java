package telran.logs.bugs.dto.artifact;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class ArtifactDto {
    @NotEmpty
    public String artifactId;
    @Min(1)
    public long programmerId;

}
