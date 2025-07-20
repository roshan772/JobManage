package ijse71.entity;
import jakarta.persistence.*;
import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity

public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "job_title")
    private String jobTitle;
    private String company;
    private String location;
    private String type;
    @Column(name = "job_description")
    private String jobDescription;
    private String status;
}
