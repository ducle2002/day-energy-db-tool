package com.example.demo.service;

import com.example.demo.config.MessagingConfig;
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

@EnableScheduling
@Service
public class DayEnergyServiceImpl implements DayEnergyService {
    private TaskScheduler taskScheduler;
    private WebClient webClient;
    private Map<String, Job> schedulerMap = new HashMap<>();

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
        if(schedulerMap.containsKey(jobId))
            schedulerMap.remove(jobId);
        else
            System.out.println("No jobs exists with Id:"+ jobId);
    }

    @Override
    public void scheduleGetJob(Job job) {
        System.out.println("scheduleGetJobing get job:"+ job.toString());
        taskScheduler.schedule(
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
        schedulerMap.put(job.getJobName() + "-" + System.currentTimeMillis(), job);
    }

    @Override
    public void schedulePostJob(Job job){
        System.out.println("scheduling post job:"+ job.toString());
        taskScheduler.schedule(
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
        schedulerMap.put(job.getJobName() + "-" + System.currentTimeMillis(), job);
    }
    @Override
    public List<DayEnergy> search() {
        Date date = new Date();

        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");

        String[] time = formatDate.format(date).toString().split("/");
        String newTime = time[2] + time[1] + time[0];
        String newTime2 = "20110310";
//        System.out.println(newTime);
        List<DayEnergy> energyList = dayEnergyRepository.search(newTime2);
        return energyList;
    }

    @Override
    public List<DayEnergy> publist() {
        List<DayEnergy> data = search();
        template.convertAndSend(MessagingConfig.EXCHANGE, MessagingConfig.ROUTING_KEY, data);
        return data;
    }
}
