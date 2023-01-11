package com.parkinglot.manage.ParkingLot.Entities.ParkingHistory;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.parkinglot.manage.ParkingLot.Entities.ParkingTicket.ParkingTicket;

@Entity
public class ParkingHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ticket_id")
    private ParkingTicket ticket;

    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "amount")
    private Double amount;

    public ParkingHistory() {
    }

    public ParkingHistory(ParkingTicket ticket, Date endTime, Double amount) {
        this.ticket = ticket;
        this.endTime = endTime;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ParkingTicket getTicket() {
        return ticket;
    }

    public void setTicket(ParkingTicket ticket) {
        this.ticket = ticket;
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

    @Override
    public String toString() {
        return "ParkingHistory [id=" + id + ", ticket=" + ticket + ", endTime=" + endTime + ", amount=" + amount + "]";
    }

}
