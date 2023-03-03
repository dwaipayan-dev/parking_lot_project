package com.parkinglot.manage.ParkingLot.Services.SlotManagerService;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parkinglot.manage.CustomDS.Pair.Pair;
import com.parkinglot.manage.ParkingLot.Entities.ParkingTicket.ParkingTicket;
import com.parkinglot.manage.ParkingLot.Entities.ParkingTicket.ParkingTicketRepository;
import com.parkinglot.manage.ParkingLot.Entities.Slot.Slot;
import com.parkinglot.manage.ParkingLot.Entities.Slot.SlotRepository;
import com.parkinglot.manage.ParkingLot.Entities.Vehicle.Vehicle;
import com.parkinglot.manage.ParkingLot.Enums.SlotStatus;
import com.parkinglot.manage.ParkingLot.Services.SlotManagerService.SlotStatusList.SlotStatusList;
import com.parkinglot.manage.ParkingLot.Services.SlotManagerService.SlotStatusList.SlotStatusQueue;

@Service
public class SlotManagerService {

    private SlotStatusList slotMap;

    private static final Logger logger = LoggerFactory.getLogger(SlotManagerService.class);

    @Autowired
    private SlotRepository slotRepository;

    @Autowired
    private ParkingTicketRepository ticketRepository;

    @Value("${slots.nrow}")
    private int nrows;

    @Value("${slots.ncols}")
    private int ncols;

    public SlotManagerService() {
        slotMap = new SlotStatusQueue();
    }

    /* Helper Functions */
    private Slot checkIfSlotPresent(int row, int col) {
        Slot targetSlot = null;
        try {
            targetSlot = slotRepository.findByRowAndCol(row, col);
        } catch (EntityNotFoundException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
        return targetSlot;
    }

    private boolean updateCacheAndDB(Slot targetSlot, SlotStatus newStatus) {
        SlotStatus oldStatus = targetSlot.getStatus();
        try {
            String slotKey = SlotStatusList.createSlotKey(targetSlot.getRow(), targetSlot.getCol());
            Slot cacheKey = slotMap.remove(slotKey);
            targetSlot.setStatus(newStatus);
            slotRepository.save(targetSlot);
            slotMap.add(cacheKey.getRow(), cacheKey.getCol(), newStatus.toString());
            return true;
        } catch (PersistenceException e) {
            // Incase of DB error in saving the status of object add removed node back to
            // slotmap at old status
            slotMap.add(targetSlot.getRow(), targetSlot.getCol(), oldStatus.toString());
            return false;
        }
    }

    /* ... */

    public void initializeSlotsDB(Set<Pair<Integer, Integer>> gates, Set<Pair<Integer, Integer>> defunct) {
        List<Slot> allSlots = slotRepository.findAll();
        Set<Pair<Integer, Integer>> slotPairSet = new HashSet<>();
        for (Slot slot : allSlots) {
            Pair<Integer, Integer> slotPair = new Pair<Integer, Integer>(slot.getRow(), slot.getCol());
            slotPairSet.add(slotPair);
        }
        for (int i = 0; i < nrows; i++) {
            for (int j = 0; j < ncols; j++) {
                final Pair<Integer, Integer> pos = new Pair<>(i, j);
                if (slotPairSet.contains(pos))
                    continue;
                Slot newSlot = new Slot(i, j);
                if (gates.contains(pos)) {
                    newSlot.setStatus(SlotStatus.GATE);
                } else if (defunct.contains(pos)) {
                    newSlot.setStatus(SlotStatus.UNAVAILABLE);
                }
                slotRepository.save(newSlot);
            }
        }
    }

    public Boolean addGate(int row, int col) {
        // Check the status of slot (row, col), change it to gate if unfilled else
        // return false;
        // Remove the slot from unfilled key queue of slotmap,
        // Insert the slot to gate key queue of slotMap
        Slot targetSlot = checkIfSlotPresent(row, col);
        boolean result = false;
        if (targetSlot == null)
            return null;
        if (targetSlot.getStatus() == SlotStatus.OCCUPIED) {
            logger.info("Cannot use slot as gate as it is occupied");
            return false;
        }
        if (targetSlot.getStatus() == SlotStatus.EMPTY || targetSlot.getStatus() == SlotStatus.UNAVAILABLE) {
            result = updateCacheAndDB(targetSlot, SlotStatus.GATE);
            // TODO: add gate in exitManager stub(responsibility of driver class)
        }
        if (result == false)
            logger.info("Could not update Cache and DB");
        return result;

    }

    public Boolean removeGate(int row, int col, SlotStatus newStatus) {
        // TODO: add gate in exitManager stub(responsibility of driver class)
        if (newStatus == SlotStatus.OCCUPIED) {
            logger.info("Cannot change status to OCCUPIED");
            return false;
        }
        Slot targetSlot = checkIfSlotPresent(row, col);
        boolean result = false;
        if (targetSlot == null)
            return null;
        if (targetSlot.getStatus() != SlotStatus.GATE) {
            logger.info("SlotStatus mismatch: Looking for GATE but found" + targetSlot.getStatus());
            return false;
        } else {
            result = updateCacheAndDB(targetSlot, newStatus);
            if (result == false)
                logger.info("Could not update Cache and DB");
            return result;
        }
    }

    public Boolean addDefunct(int row, int col) {
        // TODO: Responsibility of driver class to remove gate if present in row, col of
        // exit manager matrix
        Slot targetSlot = checkIfSlotPresent(row, col);
        boolean result = false;
        if (targetSlot == null)
            return null;
        if (targetSlot.getStatus() == SlotStatus.OCCUPIED) {
            logger.info("Cannot use slot as defunct as it is occupied");
            return false;
        }
        if (targetSlot.getStatus() == SlotStatus.EMPTY || targetSlot.getStatus() == SlotStatus.GATE) {
            result = updateCacheAndDB(targetSlot, SlotStatus.UNAVAILABLE);
        }
        if (result == false)
            logger.info("Could not update Cache and DB");
        return result;
    }

    public Boolean removeDefunct(int row, int col, SlotStatus newStatus) {
        // TODO: add gate in exitManager stub(responsibility of driver class)
        if (newStatus == SlotStatus.OCCUPIED) {
            logger.info("Cannot change status to OCCUPIED");
            return false;
        }
        Slot targetSlot = checkIfSlotPresent(row, col);
        boolean result = false;
        if (targetSlot == null)
            return null;
        if (targetSlot.getStatus() != SlotStatus.UNAVAILABLE) {
            logger.info("SlotStatus mismatch: Looking for UNAVAILABLE but found" + targetSlot.getStatus());
            return false;
        } else {
            result = updateCacheAndDB(targetSlot, newStatus);
            if (result == false)
                logger.info("Could not update Cache and DB");
            return result;
        }
    }

    @Transactional
    public ParkingTicket confirmSlot(Vehicle vehicle) {
        Slot cacheKey = slotMap.removeLast(SlotStatus.EMPTY.toString());
        Slot targetSlot = checkIfSlotPresent(cacheKey.getRow(), cacheKey.getCol());
        // If cacheKey row and col is not found then resonse would be try again message.
        if (targetSlot == null)
            return null;
        try {
            targetSlot.setStatus(SlotStatus.OCCUPIED);
            slotRepository.save(targetSlot);
            slotMap.add(cacheKey.getRow(), cacheKey.getCol(), SlotStatus.OCCUPIED.toString());
            ParkingTicket ticket = new ParkingTicket(new Date(), targetSlot);
            ticket.setVehicle(vehicle);
            ticketRepository.save(ticket);
            return ticket;
        } catch (PersistenceException e) {
            slotMap.add(cacheKey.getRow(), cacheKey.getCol(), SlotStatus.EMPTY.toString());
            return null;
        }

    }

    public Boolean vacateSlot(Long ticketId) {
        Optional<ParkingTicket> queryResult = ticketRepository.findById(ticketId);
        if (!queryResult.isPresent()) {
            return null;
        } else {
            ParkingTicket ticket = queryResult.get();
            Slot targetSlot = ticket.getSlot();
            boolean result = updateCacheAndDB(targetSlot, SlotStatus.EMPTY);
            if (result == false)
                logger.info("Could not update Cache and DB");
            return result;
        }
    }

    public boolean slotRecovery() {
        // Used to populate slotMap from db during recovery after service failure.
        List<Slot> nonEmptySlots = null;
        try {
            nonEmptySlots = slotRepository.findByStatusNot(SlotStatus.EMPTY);
        } catch (PersistenceException e) {
            logger.error("Slot recovery failed due to database error", e);
            return false;
        }
        for (Slot nonEmptySlot : nonEmptySlots) {
            slotMap.add(nonEmptySlot.getRow(), nonEmptySlot.getCol(), nonEmptySlot.getStatus().toString());
        }
        for (int i = 0; i < nrows; i++) {
            for (int j = 0; j < ncols; j++) {
                if (slotMap.checkIfEntryPresent(i, j))
                    continue;
                slotMap.add(i, j, SlotStatus.EMPTY.toString());
            }
        }
        return true;
    }
}
