package com.parkinglot.manage.ParkingLot.Services.SlotManagerService.SlotStatusList;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.parkinglot.manage.CustomDS.DLListQueue.DLListQueue;
import com.parkinglot.manage.CustomDS.DLListQueue.Node;
import com.parkinglot.manage.ParkingLot.Entities.Slot.Slot;
import com.parkinglot.manage.ParkingLot.Enums.SlotStatus;
import com.parkinglot.manage.ParkingLot.Services.SlotManagerService.SlotCacheNode.SlotCacheNode;

public class SlotStatusQueue implements SlotStatusList {
    private Map<String, Node<SlotCacheNode>> nodeMap;
    private Map<String, DLListQueue<SlotCacheNode>> statusMap;

    public SlotStatusQueue() {
        nodeMap = new ConcurrentHashMap<>();
        statusMap = new ConcurrentHashMap<>();
        statusMap.put("EMPTY", new DLListQueue<SlotCacheNode>());
        statusMap.put("OCCUPIED", new DLListQueue<SlotCacheNode>());
        statusMap.put("GATE", new DLListQueue<SlotCacheNode>());
        statusMap.put("UNAVAILABLE", new DLListQueue<SlotCacheNode>());

    }

    @Override
    public boolean checkIfEntryPresent(int row, int col) {
        String key = SlotStatusList.createSlotKey(row, col);
        return nodeMap.containsKey(key);
    }

    @Override
    public void add(int row, int col, String status) {
        // This method creates a key out of status given, creates a node that will be
        // used to reference the node stored
        // in statusMap statusqueue during removal. The add() is made thread safe to
        // support concurrent operations.
        Node<SlotCacheNode> newNode = new Node<>(new SlotCacheNode(row, col, SlotStatus.valueOf(status)));
        String key = SlotStatusList.createSlotKey(row, col);
        DLListQueue<SlotCacheNode> newQueue = statusMap.get(status);
        synchronized (this) {
            newQueue.add(newNode);
            nodeMap.put(key, newNode);
        }
    }

    @Override
    public Slot remove(String slotKey) {
        // Returns a slot object but do not persist that in the database. It only serves
        // to encapsulate all relevant data to return
        String[] dims = slotKey.split("[rc]+");
        int row = Integer.parseInt(dims[0]);
        int col = Integer.parseInt(dims[1]);
        Node<SlotCacheNode> currNode = nodeMap.get(slotKey);
        SlotStatus currStatus = currNode.getVal().getStatus();
        DLListQueue<SlotCacheNode> oldQueue = statusMap.get(currStatus.toString());
        // Remove from nodemap as well to avoid stale read
        synchronized (this) {
            oldQueue.remove(currNode);
            nodeMap.remove(slotKey);
        }
        Slot result = new Slot(row, col);
        return result;
    }

    @Override
    public Slot removeLast(String status) {
        DLListQueue<SlotCacheNode> oldQueue = statusMap.get(status);
        SlotCacheNode topNode = null;
        int row = 0, col = 0;
        synchronized (this) {
            topNode = oldQueue.removeLast().getVal();
            row = topNode.getRow();
            col = topNode.getCol();
            String slotKey = SlotStatusList.createSlotKey(row, col);
            nodeMap.remove(slotKey);
        }
        Slot result = new Slot(row, col);
        return result;
    }

    // public Slot removeLastEmpty() {
    // return removeLast("EMPTY");
    // }

}
