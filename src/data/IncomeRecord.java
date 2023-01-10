package data;

import java.time.LocalDate;

public class IncomeRecord extends GeneralRecord {
    private IncomeCategory incomeCategory;
    private boolean isBankTransfer;

    public IncomeRecord(int recordID, double sum, LocalDate date, Person person, TransferStatus transferStatus, IncomeCategory incomeCategory, boolean isBankTransfer) {
        super(recordID, sum, date, person, transferStatus);
        this.incomeCategory = incomeCategory;
        this.isBankTransfer = isBankTransfer;
    }

    public IncomeCategory getIncomeCategory() {
        return incomeCategory;
    }

    public void setIncomeCategory(IncomeCategory incomeCategory) {
        this.incomeCategory = incomeCategory;
    }

    public boolean isBankTransfer() {
        return isBankTransfer;
    }

    public void setBankTransfer(boolean bankTransfer) {
        isBankTransfer = bankTransfer;
    }

    @Override
    public String toString() {
        return "IncomeRecord ID=" + recordID+ ". " + super.toString() +  ", incomeCategory=" + incomeCategory + ", isBankTransfer=" + isBankTransfer + "\n";
    }
}
