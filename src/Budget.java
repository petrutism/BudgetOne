import data.GeneralRecord;
import data.IncomeRecord;
import data.OutcomeRecord;

import java.util.ArrayList;
import java.util.List;

public class Budget {
    private List<GeneralRecord> records;
    List<IncomeRecord> incomes = new ArrayList<>();
    List<OutcomeRecord> outcomes = new ArrayList<>();;

    void addGeneralRecord(GeneralRecord record) {
        if (records == null) {
            records = new ArrayList<>();
        }
        records.add(record);
    }

    List<GeneralRecord> getAllRecords() {
        return records;
    }

    double getBalance() {

        if (records == null) {
            return 0d;//TODO something if null
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
        if (records == null) {
            return null;//TODO something if null
        }
        for (GeneralRecord in : records) {
            if (in instanceof IncomeRecord) {
                incomes.add((IncomeRecord) in);
            }
        }
        return incomes;
    }

    List<OutcomeRecord> getAllOutcomes() {
        if (records == null) {
            return null;//TODO something if null
        }
        for (GeneralRecord out : records) {
            if (out instanceof OutcomeRecord) {
                outcomes.add((OutcomeRecord) out);
            }
        }
        return outcomes;
    }

}
