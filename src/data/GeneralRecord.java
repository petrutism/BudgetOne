package data;

import java.time.LocalDate;
import java.util.Objects;

public class GeneralRecord {
    public static int recordCount = 0;
    public final int recordID;
    private double sum;
    private LocalDate date;
    private Person person;
    private TransferStatus transferStatus;

    public GeneralRecord(int recordID, double sum, LocalDate date, Person person, TransferStatus transferStatus) {
        this.recordID = recordID;
        this.sum = sum;
        this.date = date;
        this.person = person;
        this.transferStatus = transferStatus;
    }

    public int getRecordID() {
        return recordID;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public TransferStatus getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(TransferStatus transferStatus) {
        this.transferStatus = transferStatus;
    }

    @Override
    public String toString() {
        return "sum=" + sum +
                ", date=" + date +
                ", person=" + person +
                ", transferStatus=" + transferStatus +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeneralRecord that = (GeneralRecord) o;
        return recordID == that.recordID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(recordID);
    }
}
