package com.parkinglot.manage.ParkingLot.Services.SlotManagerService.SlotStatusList;

import com.parkinglot.manage.ParkingLot.Entities.Slot.Slot;

public interface SlotStatusList {

    // Add a Node<SlotStatus> to any of the statuslist
    public void add(int row, int col, String status);

    /*
     * Slot Key format "{row}r{col}c"
     */

    // Remove a Node<SlotStatus>
    public Slot remove(String slotKey);

    // Remove last from any status list
    public Slot removeLast(String status);
}
