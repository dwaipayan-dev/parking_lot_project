package com.parkinglot.manage.ParkingLot.Entities.ParkingTicket;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingTicketRepository extends JpaRepository<ParkingTicket, Long> {
    // To find vehicle (join class/ nested class) by its attribute
    public ParkingTicket findByVehicleId(Long vehicleId);

    public ParkingTicket findBySlotId(Long slotId);

    public ParkingTicket findByStartTimeBetween(Date fromTime, Date toTime);
}
