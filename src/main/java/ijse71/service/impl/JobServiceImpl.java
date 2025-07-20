package ijse71.service.impl;

import ijse71.dto.JobDTO;
import ijse71.entity.Job;
import ijse71.repository.JobRepository;
import ijse71.service.JobService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    private final ModelMapper modelMapper;
    public JobServiceImpl(JobRepository jobRepository, ModelMapper modelMapper) {
        this.jobRepository = jobRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public void saveJob(JobDTO jobDTO) {
        jobRepository.save(modelMapper.map(jobDTO, Job.class));
    }

    @Override
    public Page<JobDTO> getAllJobs(Pageable pageable) {
        return jobRepository.findAll(pageable)
                .map(job -> modelMapper.map(job, JobDTO.class));
    }



    @Override
    public void deleteJob(Integer id) {
        if (jobRepository.existsById(id)) {
            jobRepository.deleteById(id);
        }
        else {
            throw new RuntimeException("Cannot Delete Job");
        }
    }

    @Override
    public void updateJob(JobDTO jobDTO) {
        if (jobDTO.getId() == null || !jobRepository.existsById(jobDTO.getId())) {
            throw new RuntimeException("Job not found with ID: " + jobDTO.getId());
        }
        jobRepository.save(modelMapper.map(jobDTO, Job.class));
    }

    @Override
    public JobDTO getJobById(Integer id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found with id: " + id));
        return modelMapper.map(job, JobDTO.class);
    }



}
