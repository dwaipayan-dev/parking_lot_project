package com.parkinglot.manage.ParkingLot.Services.ExitManagerService;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parkinglot.manage.CustomDS.Pair.Pair;
import com.parkinglot.manage.ParkingLot.Entities.Slot.Slot;

public class ExitManagerService {
    private int nRows;
    private int nCols;
    private Set<Pair<Integer, Integer>> gates;
    private Set<Pair<Integer, Integer>> defuncts;
    private int[][] parkingMatrix;

    private static final Logger logger = LoggerFactory.getLogger(ExitManagerService.class);

    public ExitManagerService(int nRows, int nCols, Set<Pair<Integer, Integer>> gates, Set<Pair<Integer, Integer>> defuncts) {
        this.nRows = nRows;
        this.nCols = nCols;
        this.gates = gates;
        this.defuncts = defuncts;
        parkingMatrix = new int[nRows][nCols];
    }

    public void initializeParkingMatrix() {
        parkingMatrix = new int[nRows][nCols];
        for(Pair<Integer, Integer> gate: gates) {
            //Mark gates with -1 value
            markUnavailable(gate, -1);
        }
        for(Pair<Integer, Integer> defunct: defuncts) {
            //Mark defunct with Integer.MAX_VALUE
            markUnavailable(defunct, Integer.MAX_VALUE);
        }
    }

    private void markUnavailable(Pair<Integer, Integer> gate, int val) {
        int row = gate.getFirst();
        int col= gate.getSecond();
        if(row < 0 || row >= nRows || col < 0 || col >= nCols)
            return;
        parkingMatrix[row][col] = val;
    }

    private void calculateNearestExitAtAllPoints() {
        Queue<int[]> bfsQ = new LinkedList<>();
        for(int i = 0; i < nRows; i++) {
            for(int j = 0; j < nCols; j++) {
                if(parkingMatrix[i][j] == -1) {
                    bfsQ.offer(new int[]{i, j, i*nRows + j});
                } else {
                    if(parkingMatrix[i][j] == 0) {
                        parkingMatrix[i][j] = -2;
                    }
                }
            }
        }
        while(!bfsQ.isEmpty()) {
            int[] curr = bfsQ.poll();
            int[] dir = {-1, 0, 1, 0, -1};
            for(int k = 0; k < dir.length - 1; k++) {
                int i = curr[0] + dir[k];
                int j = curr[1] + dir[k+1];
                if(i < 0 || i >= nRows || j < 0 || j >= nCols) continue;
                if(parkingMatrix[i][j] == -2){
                    parkingMatrix[i][j] = curr[3];
                    bfsQ.offer(new int[]{i, j, curr[3]});
                }
            }
        }

    }

    /* 
    Code for adding gates and unavailable. Add in batch.. */

    public void add(Slot[] newSlots, boolean isGate) {
        try {
            for(Slot newSlot: newSlots) {
                int row = newSlot.getRow();
                int col = newSlot.getCol();
                if(row < 0 || row >= nRows || col < 0 || col >= nCols) {
                    logger.error("Given slot is invalid");
                    continue;
                }
                if(isGate == true) {
                    this.gates.add(new Pair<>(row, col));
                } else {
                    this.defuncts.add(new Pair<>(row, col));
                }
            }
            initializeParkingMatrix();
            calculateNearestExitAtAllPoints();
        } catch (Exception e) {
            logger.error("Could not update slots due to ERROR: " + e.getMessage());
        }
    }

    public void remove(Slot[] newSlots, boolean isGate) {
        try {
            for(Slot newSlot: newSlots) {
                int row = newSlot.getRow();
                int col = newSlot.getCol();
                if(row < 0 || row >= nRows || col < 0 || col >= nCols) {
                    logger.error("Given slot is invalid");
                    continue;
                }
                if(isGate == true) {
                    this.gates.remove(new Pair<>(row, col));
                } else {
                    this.defuncts.remove(new Pair<>(row, col));
                }
            }
            initializeParkingMatrix();
            calculateNearestExitAtAllPoints();
        } catch (Exception e) {
            logger.error("Could not update slots due to ERROR: " + e.getMessage());
        }
    }

    public Slot getNearestGate(Slot parkSlot) throws Exception{
        try {
            int row = parkSlot.getRow();
            int col = parkSlot.getCol();
            int nearestGateIdx = parkingMatrix[row][col];
            if(nearestGateIdx == -1 || nearestGateIdx == Integer.MAX_VALUE) {
                throw new IOException("Slot given is either a gate or unavailable");
            }
            return new Slot(nearestGateIdx/nRows, nearestGateIdx%nCols);
        } catch (Exception e) {
            // TODO: handle exception
            throw new IOException("Input slot is invalid");
        }
        
    }
}
