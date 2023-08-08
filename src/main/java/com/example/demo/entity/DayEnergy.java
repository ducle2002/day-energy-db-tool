package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "DayEnergy")
@Builder
@Table(name = "dbo.DayEnergy")
@IdClass( DayEnergyId.class )

//@Access(value=AccessType.FIELD)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class DayEnergy{
    @Id
    @Column(name = "RentCd")
    private String RentCd;
    @Id
    @Column(name = "EnergyDay")
    private String EnergyDay;

    @Id
    @Column(name = "FloorNo")
    private Integer FloorNo;

    @Id
    @Column(name = "RoomNo")
    private String RoomNo;
    //    @AttributeOverrides({
//            @AttributeOverride(
//                    name = "RentCd",
//                    column = @Column(name = "RentCd")
//            ),
//            @AttributeOverride(
//                    name = "EnergyDay",
//                    column = @Column(name = "EnergyDay")
//            ),
//            @AttributeOverride(
//                    name = "FloorNo",
//                    column = @Column(name = "FloorNo")
//            ),
//            @AttributeOverride(
//                    name = "RoomNo",
//                    column = @Column(name = "RoomNo")
//            )
//    })
//    private DayEnergyId dayEnergyId;
    private Integer ElectricityVal;
    private Integer WaterVal;
    private Integer GasVal;
    private Integer ElectricityUse;
    private Integer WaterUse;
    private String Flag1;
    private String Flag2;
    private String Flag3;
    public DayEnergyId getId() {
        return new DayEnergyId(
                RentCd,
                EnergyDay,
                FloorNo,
                RoomNo
        );
    }

    public void setId(DayEnergyId id) {
        this.RentCd = id.getRentCd();
        this.EnergyDay = id.getEnergyDay();
        this.FloorNo = id.getFloorNo();
        this.RoomNo = id.getRoomNo();
    }
    public Integer getElectricityVal() {
        return ElectricityVal;
    }

    public void setElectricityVal(Integer electricityVal) {
        ElectricityVal = electricityVal;
    }

    public Integer getWaterVal() {
        return WaterVal;
    }

    public void setWaterVal(Integer waterVal) {
        WaterVal = waterVal;
    }

    public Integer getGasVal() {
        return GasVal;
    }

    public void setGasVal(Integer gasVal) {
        GasVal = gasVal;
    }

    public Integer getElectricityUse() {
        return ElectricityUse;
    }

    public void setElectricityUse(Integer electricityUse) {
        ElectricityUse = electricityUse;
    }

    public Integer getWaterUse() {
        return WaterUse;
    }

    public void setWaterUse(Integer waterUse) {
        WaterUse = waterUse;
    }

    public String getFlag1() {
        return Flag1;
    }

    public void setFlag1(String flag1) {
        Flag1 = flag1;
    }

    public String getFlag2() {
        return Flag2;
    }

    public void setFlag2(String flag2) {
        Flag2 = flag2;
    }

    public String getFlag3() {
        return Flag3;
    }

    public void setFlag3(String flag3) {
        Flag3 = flag3;
    }

    @Override
    public String toString() {
        return "DayEnergy{" +
                "RentCd='" + RentCd + '\'' +
                ", EnergyDay='" + EnergyDay + '\'' +
                ", FloorNo=" + FloorNo +
                ", RoomNo='" + RoomNo + '\'' +
                ", ElectricityVal=" + ElectricityVal +
                ", WaterVal=" + WaterVal +
                ", GasVal=" + GasVal +
                ", ElectricityUse=" + ElectricityUse +
                ", WaterUse=" + WaterUse +
                ", Flag1='" + Flag1 + '\'' +
                ", Flag2='" + Flag2 + '\'' +
                ", Flag3='" + Flag3 + '\'' +
                '}';
    }
}



