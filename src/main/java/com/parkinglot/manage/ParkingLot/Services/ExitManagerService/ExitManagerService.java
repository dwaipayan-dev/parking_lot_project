package com.parkinglot.manage.ParkingLot.Services.ExitManagerService;

import java.util.Set;

import com.parkinglot.manage.CustomDS.Pair.Pair;
import com.parkinglot.manage.ParkingLot.Entities.Slot.Slot;

public class ExitManagerService {
    private int nRows;
    private int nCols;

    private int[][] parkingMatrix;

    public ExitManagerService() {
        parkingMatrix = new int[nRows][nCols];
    }

    public void initializeParkingMatrix(Set<Pair<Integer, Integer>> gates, Set<Pair<Integer, Integer>> defunct) {

    }

    /* Private Methods */

    private void BFSUtils(int sourceRow, int sourceCol, int[][] visited, int[][] parkingMatrix) {

    }

    private void calculateNearestExitAtAllPoints() {

    }

    /* ... */

    public void add(boolean isGate) {

    }

    public void remove(boolean isGate) {

    }

    public Slot getNearestGate() {
        return new Slot();
    }
}
