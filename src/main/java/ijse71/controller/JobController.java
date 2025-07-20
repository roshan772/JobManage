package ijse71.controller;

import ijse71.dto.JobDTO;
import ijse71.dto.PaginatedResponse;
import ijse71.service.JobService;
import ijse71.service.impl.JobServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/job")
@RestController
@RequiredArgsConstructor
public class JobController {
    private final JobService jobService;


    @PostMapping("create")
    public ResponseEntity<JobDTO> createJob(@RequestBody JobDTO jobDTO) {
       jobService.saveJob(jobDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(jobDTO);
    }

    //http://localhost:8082/api/v1/job/getAll
    @GetMapping("getAll")
    public PaginatedResponse<JobDTO> getAllJobs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<JobDTO> jobPage = jobService.getAllJobs(PageRequest.of(page, size));
        return new PaginatedResponse<>(jobPage);
    }//completed


    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Integer id) {
        jobService.deleteJob(id);
        return ResponseEntity.noContent().build();
    }
     @PutMapping("edit")
    public ResponseEntity<JobDTO> updateJob(@RequestBody JobDTO jobDTO) {
        jobService.updateJob(jobDTO);
        return ResponseEntity.ok(jobDTO);
    }
    @GetMapping("{id}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable Integer id) {
        JobDTO jobDTO = jobService.getJobById(id);
        return ResponseEntity.ok(jobDTO);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }


}
