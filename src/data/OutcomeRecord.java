package data;

import java.time.LocalDate;

public class OutcomeRecord extends GeneralRecord {
    private OutcomeCategory outcomeCategory;
    private OutcomeType outcomeType;

    public OutcomeRecord(int recordID, double sum, LocalDate date, Person person, TransferStatus transferStatus, OutcomeCategory outcomeCategory, OutcomeType outcomeType) {
        super(recordID, sum, date, person, transferStatus);
        this.outcomeCategory = outcomeCategory;
        this.outcomeType = outcomeType;
    }

    public OutcomeCategory getOutcomeCategory() {
        return outcomeCategory;
    }

    public void setOutcomeCategory(OutcomeCategory outcomeCategory) {
        this.outcomeCategory = outcomeCategory;
    }

    public OutcomeType getOutcomeType() {
        return outcomeType;
    }

    public void setOutcomeType(OutcomeType outcomeType) {
        this.outcomeType = outcomeType;
    }

    @Override
    public String toString() {

        return "OutcomeRecord ID=" + recordID + ". " + super.toString() + ", outcomeCategory=" + outcomeCategory + ", outcomeType=" + outcomeType + "\n";
    }
}
