package com.example.demo.service;
import com.example.demo.dto.Job;
import com.example.demo.entity.DayEnergy;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

public interface DayEnergyService {

    Map<String, Job> getScheduledJobs();

    void deleteScheduledJob(String jobId);

    ScheduledFuture<?> scheduleGetJob(Job job);

    ScheduledFuture<?> schedulePostJob(Job job);

    List<DayEnergy> search();

    List<DayEnergy> publist(String exchange, String key);
}
