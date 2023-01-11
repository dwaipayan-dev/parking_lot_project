package com.parkinglot.manage.ParkingLot.Entities.ParkingTicket;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.parkinglot.manage.ParkingLot.Entities.Slot.Slot;
import com.parkinglot.manage.ParkingLot.Entities.Vehicle.Vehicle;

@Entity
public class ParkingTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "start_time")
    private Date startTime;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "slot_id")
    private Slot slot;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    public Long getId() {
        return id;
    }

    public ParkingTicket() {
    }

    public ParkingTicket(Date startTime, Slot slot) {
        this.startTime = startTime;
        this.slot = slot;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Slot getSlot() {
        return slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }

    public ParkingTicket(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public String toString() {
        return "ParkingTicket [id=" + id + ", startTime=" + startTime + ", slot=" + slot + ", vehicle=" + vehicle + "]";
    }

}
