package com.example.demo.controller;

import com.example.demo.dto.Job;
import com.example.demo.entity.DayEnergy;
import com.example.demo.service.DayEnergyService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/day-energy")
public class DayEnergyController {
    @Autowired
    DayEnergyService dayEnergyService;

    @GetMapping("/hello")
    public String sayHello(){
        return "Ola Amigos!";
    }

    @PostMapping("/ola")
    public String sayHelloWithName(@RequestBody Job job){
        return "Ola " + job.getJobName();
    }

    @PostMapping("/scheduleGet")
    public void scheduleGetJob(@RequestBody Job job){
        if(null!=job){
            dayEnergyService.scheduleGetJob(job);
        }
    }

    @PostMapping("/schedulePost")
    public void schedulePostJob(@RequestBody Job job){
        if(null!=job){
            dayEnergyService.schedulePostJob(job);
        }
    }

    @GetMapping("/scheduledJobs")
    public Map<String, Job> getScheduledJobs(){
        return dayEnergyService.getScheduledJobs();
    }

    @DeleteMapping("/deleteJob/{jobId}")
    public void deleteScheduledJob(@PathVariable String jobId){
        dayEnergyService.deleteScheduledJob(jobId);
    }


    @ApiOperation(value = "search", notes = "{}")
    @GetMapping("/search")
    public String search() {
        try {
            dayEnergyService.search();
            return "success";
        } catch (Exception ex) {
            return String.valueOf(ex);
        }
    }
    @GetMapping("/publish")
    public List<DayEnergy> publish(){
            List<DayEnergy> data = dayEnergyService.publist();
            return data;

    }
}
