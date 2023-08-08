package com.example.demo.repository;

import com.example.demo.entity.DayEnergy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DayEnergyRepository extends JpaRepository<DayEnergy, String> {
    @Query(value = "SELECT TOP (10) d.RentCd" +
            "      ,d.EnergyDay" +
            "      ,d.FloorNo" +
            "      ,d.RoomNo" +
            "      ,d.ElectricityVal" +
            "      ,d.WaterVal" +
            "      ,d.GasVal" +
            "      ,d.ElectricityUse" +
            "      ,d.WaterUse" +
            "      ,d.GasUse" +
            "      ,d.Flag1" +
            "      ,d.Flag2" +
            "      ,d.Flag3 FROM dbo.DayEnergy d" +
            "       WHERE d.EnergyDay = :time" ,nativeQuery = true)
    List<DayEnergy> search(String time);

}
