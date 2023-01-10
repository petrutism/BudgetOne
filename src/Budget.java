import data.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class Budget {
    private List<GeneralRecord> records = new ArrayList<>();
    private List<IncomeRecord> incomes = new ArrayList<>();
    private List<OutcomeRecord> outcomes = new ArrayList<>();
    private List<IncomeRecord> incomesResults = new ArrayList<>();
    private List<OutcomeRecord> outcomesResults = new ArrayList<>();

    void addGeneralRecord(GeneralRecord record) {
        records.add(record);
    }

    List<GeneralRecord> getAllRecords() {
        return records;
    }
        double getBalance() {

        if (records.size() == 0) {
            System.out.print("There is no any records... Balance is: ");
            return 0d;
        }

        double balance = 0d;

        for (GeneralRecord r : records) {
            if (r instanceof OutcomeRecord) {
                balance = balance - r.getSum();
            } else if (r instanceof IncomeRecord) {
                balance = balance + r.getSum();
            }
        }
        return balance;
    }

    List<IncomeRecord> getAllIncomes() {

        incomes.clear();
        for (GeneralRecord in : records) {
            if (in instanceof IncomeRecord) {
                incomes.add((IncomeRecord) in);
            }
        }
        return incomes;
    }

    List<OutcomeRecord> getAllOutcomes() {

        outcomes.clear();
        for (GeneralRecord out : records) {
            if (out instanceof OutcomeRecord) {
                outcomes.add((OutcomeRecord) out);
            }
        }
        return outcomes;
    }

    List<IncomeRecord> getIncomesByCondition(IncomeCategory category, LocalDate date) {

        incomesResults.clear();

        for (IncomeRecord inc : getAllIncomes()) {
            if (inc.getIncomeCategory().equals(category) && date.equals(inc.getDate())) {
                incomesResults.add(inc);
            }
        }
        return incomesResults;
    }

    List<OutcomeRecord> getOutcomesByCondition(OutcomeCategory category, LocalDate date) {

        outcomesResults.clear();
        for (OutcomeRecord out : getAllOutcomes()) {
            if (out.getOutcomeCategory().equals(category) && date.equals(out.getDate())) {
                outcomesResults.add(out);
            }
        }
        return outcomesResults;
    }

    void deleteByID(int id) {

        int index = -1;
        for (GeneralRecord rec : records) {
            if (id == rec.getRecordID()) {
                index = records.indexOf(rec);
            }
        }
        if (index == -1) {
            System.out.println("There is no such ID...");
        } else {
            records.remove(index);
            System.out.println("Record ID: " + id + " is removed...");
        }
    }
}
