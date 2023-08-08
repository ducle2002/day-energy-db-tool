package com.example.demo.entity;

import jakarta.persistence.Column;

import java.io.Serializable;
import java.util.Objects;

public class DayEnergyId implements Serializable {
    @Column(name = "RentCd")
    private String RentCd;
    @Column(name = "EnergyDay")
    private String EnergyDay;
    @Column(name = "FloorNo")
    private Integer FloorNo;
    @Column(name = "RoomNo")
    private String RoomNo;

    public String getRentCd() {
        return RentCd;
    }

    public void setRentCd(String rentCd) {
        RentCd = rentCd;
    }

    public String getEnergyDay() {
        return EnergyDay;
    }

    public void setEnergyDay(String energyDay) {
        EnergyDay = energyDay;
    }

    public Integer getFloorNo() {
        return FloorNo;
    }

    public void setFloorNo(Integer floorNo) {
        FloorNo = floorNo;
    }

    public String getRoomNo() {
        return RoomNo;
    }

    public void setRoomNo(String roomNo) {
        RoomNo = roomNo;
    }

    public DayEnergyId(String rentCd, String energyDay, Integer floorNo, String roomNo) {
        RentCd = rentCd;
        EnergyDay = energyDay;
        FloorNo = floorNo;
        RoomNo = roomNo;
    }
    public DayEnergyId(){};
    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        DayEnergyId DayEnergyId = (DayEnergyId) o;
        return Objects.equals( RentCd, DayEnergyId.RentCd ) &&
                Objects.equals( EnergyDay, DayEnergyId.EnergyDay) &&
                Objects.equals( FloorNo, DayEnergyId.FloorNo) &&
                Objects.equals( RoomNo, DayEnergyId.RoomNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash( RentCd, EnergyDay,FloorNo,RoomNo );
    }
}
