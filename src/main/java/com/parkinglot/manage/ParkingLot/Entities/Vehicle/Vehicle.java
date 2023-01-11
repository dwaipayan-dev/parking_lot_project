package com.parkinglot.manage.ParkingLot.Entities.Vehicle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.parkinglot.manage.ParkingLot.Enums.VehicleType;

@Entity
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long vehicleId;

    @Column(name = "vehicle_type")
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    public Vehicle() {
    }

    public Vehicle(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public boolean isTwoWheeler() {
        return this.vehicleType == VehicleType.TWO_WHEELER;
    }

    public boolean isFourWheeler() {
        return this.vehicleType == VehicleType.FOUR_WHEELER;
    }

    @Override
    public String toString() {
        return "Vehicle [vehicleId=" + vehicleId + ", vehicleType=" + vehicleType + "]";
    }

}
