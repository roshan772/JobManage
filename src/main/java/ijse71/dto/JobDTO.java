package ijse71.dto;

import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class JobDTO {
    private Integer id;
    private String jobTitle;
    private String company;
    private String location;
    private String type;
    private String jobDescription;
    private String status;

}
