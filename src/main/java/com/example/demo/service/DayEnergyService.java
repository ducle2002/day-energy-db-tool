package com.example.demo.service;
import com.example.demo.dto.Job;
import com.example.demo.entity.DayEnergy;

import java.util.List;
import java.util.Map;

public interface DayEnergyService {

    Map<String, Job> getScheduledJobs();

    void deleteScheduledJob(String jobId);

    void scheduleGetJob(Job job);

    void schedulePostJob(Job job);

    List<DayEnergy> search();

    List<DayEnergy> publist();
}
