package com.parkinglot.manage.ParkingLot.Services.SlotManagerService.SlotCacheNode;

import com.parkinglot.manage.ParkingLot.Enums.SlotStatus;

public class SlotCacheNode {
    private int row;
    private int col;
    private SlotStatus status;

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public SlotStatus getStatus() {
        return status;
    }

    public void setStatus(SlotStatus status) {
        this.status = status;
    }

    public SlotCacheNode() {
    }

    public SlotCacheNode(int row, int col, SlotStatus status) {
        this.row = row;
        this.col = col;
        this.status = status;
    }

}
