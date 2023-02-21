package com.parkinglot.manage.ParkingLot.Services.SlotManagerService.SlotStatusList;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.parkinglot.manage.CustomDS.DLListQueue.DLListQueue;
import com.parkinglot.manage.CustomDS.DLListQueue.Node;
import com.parkinglot.manage.ParkingLot.Entities.Slot.Slot;
import com.parkinglot.manage.ParkingLot.Enums.SlotStatus;

public class SlotStatusQueue implements SlotStatusList {
    Map<String, Node<SlotStatus>> nodeMap;
    Map<String, DLListQueue<SlotStatus>> statusMap;

    public SlotStatusQueue() {
        nodeMap = new ConcurrentHashMap<>();
        statusMap = new ConcurrentHashMap<>();
    }

    @Override
    public void add(int row, int col, String status) {
        // This method creates a key out of status given, creates a node that will be
        // used to reference the node stored
        // in statusMap statusqueue during removal. The add() is made thread safe to
        // support concurrent operations.
        Node<SlotStatus> newNode = new Node<>(SlotStatus.valueOf(status));
        String key = row + "r" + col + "c";
        nodeMap.put(key, newNode);
        DLListQueue<SlotStatus> newQueue = statusMap.get(status);
        synchronized (this) {
            newQueue.add(newNode);
        }
    }

    @Override
    public Slot remove(String slotKey) {
        // Returns a slot object but do not persist that in the database. It only serves
        // to encapsulate all relevant data to return
        String[] dims = slotKey.split("[rc]+");
        int row = Integer.parseInt(dims[0]);
        int col = Integer.parseInt(dims[1]);
        Node<SlotStatus> currNode = nodeMap.get(slotKey);
        SlotStatus currStatus = currNode.getVal();
        DLListQueue<SlotStatus> oldQueue = statusMap.get(currStatus.toString());
        synchronized (this) {
            oldQueue.remove(currNode);
        }
        Slot result = new Slot(row, col);
        return result;
    }

    @Override
    public Slot removeLast(String status) {
        // Problem is that DLListQueue would only return slotstatus which would provide
        // no information on row and column
        return null;
    }

    public Slot removeLastEmpty() {
        return removeLast("EMPTY");
    }

}
