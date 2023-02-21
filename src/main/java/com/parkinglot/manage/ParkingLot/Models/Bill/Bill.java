package com.parkinglot.manage.ParkingLot.Models.Bill;

import java.io.Serializable;
import java.util.Date;

import com.parkinglot.manage.ParkingLot.Entities.Slot.Slot;
import com.parkinglot.manage.ParkingLot.Entities.Vehicle.Vehicle;

public class Bill implements Serializable {
    private Long ticketId;
    private Vehicle vehicle;
    private Slot slot;
    private Date startTime;
    private Date endTime;
    private Double amount;

    public Bill() {
    }

    public Bill(Long ticketId, Vehicle vehicle, Slot slot, Date startTime, Date endTime, Double amount) {
        this.ticketId = ticketId;
        this.vehicle = vehicle;
        this.slot = slot;
        this.startTime = startTime;
        this.endTime = endTime;
        this.amount = amount;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Slot getSlot() {
        return slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

}
