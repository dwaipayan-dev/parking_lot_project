package com.parkinglot.manage.ParkingLot.Entities.ParkingHistory;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingHistoryRepository extends JpaRepository<ParkingHistory, Long> {
    public List<ParkingHistory> findByTicketStartTimeBetween(Date fromTime, Date endTime);

    public List<ParkingHistory> findByEndTimeBetween(Date fromTime, Date endTime);
}
