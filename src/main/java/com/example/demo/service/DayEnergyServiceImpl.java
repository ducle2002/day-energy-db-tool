package com.example.demo.service;

import com.example.demo.entity.DayEnergy;
import com.example.demo.dto.Job;
import com.example.demo.repository.DayEnergyRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ScheduledFuture;

@EnableScheduling
@Service
public class DayEnergyServiceImpl implements DayEnergyService {
    private TaskScheduler taskScheduler;
    private WebClient webClient;
    private Map<String, Job> schedulerMap = new HashMap<>();

    private Map<String, ScheduledFuture> schedulerFutureMap = new HashMap<>();

    @Autowired
    DayEnergyServiceImpl(TaskScheduler taskScheduler, WebClient webClient){
        this.taskScheduler = taskScheduler;
        this.webClient = webClient;
    }
    @Autowired
    DayEnergyRepository dayEnergyRepository;

    @Autowired
    private RabbitTemplate template;

    @Override
    public Map<String, Job> getScheduledJobs(){
        return schedulerMap;
    }

    @Override
    public void deleteScheduledJob(String jobId) {
        if(schedulerMap.containsKey(jobId)) {
            System.out.println(schedulerFutureMap.get(jobId));
            schedulerFutureMap.get(jobId).cancel(true);
            schedulerFutureMap.remove(jobId);
            schedulerMap.remove(jobId);
        }
        else
            System.out.println("No jobs exists with Id:"+ jobId);
    }

    @Override
    public ScheduledFuture<?> scheduleGetJob(Job job) {
        System.out.println("scheduleGetJobing get job:"+ job.toString());
        ScheduledFuture<?> future = taskScheduler.schedule(
                () -> {
                    ResponseEntity<String> response = webClient.get()
                            .uri(job.getBaseURL() + job.getApiURL())
                            .header("Content-Type", "application/json")
                            .retrieve()
                            .toEntity(String.class)
                            .block();
                    if(response.getStatusCode().isError()){
                        System.out.println("Failure calling Job:"+ job.getJobName()+ "with URL:"+ job.getBaseURL()+job.getApiURL());
                    } else {
                        System.out.println(response.getBody());
                    }
                },
                new CronTrigger(job.getCron(), TimeZone.getTimeZone(TimeZone.getDefault().toZoneId()))
        );
        String key = job.getJobName() + "-" + System.currentTimeMillis();

        schedulerMap.put(key, job);
        schedulerFutureMap.put(key, future);
        return future;
    }

    @Override
    public ScheduledFuture<?> schedulePostJob(Job job){
        System.out.println("scheduling post job:"+ job.toString());
        ScheduledFuture<?> future = taskScheduler.schedule(
                () -> {
                    ResponseEntity<String> response = webClient.post()
                            .uri(job.getBaseURL() + job.getApiURL())
                            .header("Content-Type", "application/json")
                            .bodyValue(job)
                            .retrieve()
                            .toEntity(String.class)
                            .block();

                    if(response.getStatusCode().isError()){
                        System.out.println("Failure calling Job:"+ job.getJobName()+ "with URL:"+ job.getBaseURL()+job.getApiURL());
                    } else {
                        System.out.println(response.getBody());
                    }
                },
                new CronTrigger(job.getCron(), TimeZone.getTimeZone(TimeZone.getDefault().toZoneId()))
        );
        String key = job.getJobName() + "-" + System.currentTimeMillis();
        schedulerMap.put(key, job);
        schedulerFutureMap.put(key, future);
        return future;
    }
    @Override
    public List<DayEnergy> search() {
        Date date = new Date();

        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");

        String[] time = formatDate.format(date).toString().split("/");
        String newTime = time[2] + time[1] + time[0];
        List<DayEnergy> energyList = dayEnergyRepository.search(newTime);
        return energyList;
    }

    @Override
    public List<DayEnergy> publist(String exchange, String key) {
        List<DayEnergy> data = search();
        template.convertAndSend(exchange, key, data);
        System.out.println(data);
        String newData = toString();
        return data;
    }
}
