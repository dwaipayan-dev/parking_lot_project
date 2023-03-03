package com.parkinglot.manage.ParkingLot.Services.SlotManagerService.SlotStatusList;

import com.parkinglot.manage.ParkingLot.Entities.Slot.Slot;

public interface SlotStatusList {

    public static String createSlotKey(int row, int col) {
        String key = row + "r" + col + "c";
        return key;
    }

    public boolean checkIfEntryPresent(int row, int col);

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
