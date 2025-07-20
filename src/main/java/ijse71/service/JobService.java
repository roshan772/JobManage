package ijse71.service;

import ijse71.dto.JobDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface JobService {
    void saveJob(JobDTO jobDTO);

    public Page<JobDTO> getAllJobs(Pageable pageable);


    void deleteJob(Integer id);

    void updateJob(JobDTO jobDTO);

    JobDTO getJobById(Integer id);

//    Page<JobDTO> getPaginatedJobs(int page, int size);
}
