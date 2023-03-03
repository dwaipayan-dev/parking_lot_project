package com.parkinglot.manage.ParkingLot.Entities.Slot;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.parkinglot.manage.ParkingLot.Enums.SlotStatus;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Long> {
    public Slot findByRowAndCol(int row, int col);

    public List<Slot> findByStatusNot(SlotStatus status);
}
