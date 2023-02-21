package com.parkinglot.manage.ParkingLot.Entities.Slot;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.parkinglot.manage.ParkingLot.Enums.SlotStatus;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "rowColumn", columnNames = { "row_no", "col_no" }))
public class Slot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "row_no")
    private int row;

    @Column(name = "col_no")
    private int col;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private SlotStatus status;

    public Slot() {
    }

    public Slot(int row, int col) {
        this.row = row;
        this.col = col;
        // default status
        this.status = SlotStatus.UNAVAILABLE;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        return "Slot [id=" + id + ", row=" + row + ", col=" + col + ", status=" + status + "]";
    }

}
