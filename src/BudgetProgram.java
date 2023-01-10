import data.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class BudgetProgram {
    Scanner sc = new Scanner(System.in);

    void doTheAction(Budget budget) {
        String action;
        do {
            printMenu();
            action = sc.nextLine();

            selectUserAction(action, sc, budget);
        } while (!action.equals("0"));
    }

    private void printMenu() {
        String info = """
                                
                1 - Add record
                2 - Print all records
                3 - Print all incomes
                4 - Print all outcomes
                5 - Print income by category and date
                6 - Print outcome by category and date
                7 - Modify record by ID
                8 - Delete record by ID
                9 - Get balance
                0 - END
                                """;
        System.out.println(info);
    }

    private void selectUserAction(String action, Scanner sc, Budget budget) {
        switch (action) {
            case "1" -> inputRecord(sc, budget);
            case "2" -> printAllRecords(budget);
            case "3" -> printIncomes(budget);
            case "4" -> printOutcomes(budget);
            case "5" -> printIncomeByConditions(budget, sc);
            case "6" -> printOutcomeByConditions(budget, sc);
            case "7" -> {
                if (budget.getAllRecords().size() != 0) {
                    selectRecordToModify(sc, budget);
                } else {
                    System.out.println("There is no any record at all...");
                }
            }
            case "8" -> deleteByID(budget);
            case "9" -> System.out.println("Balance is: " + budget.getBalance());
            case "0" -> System.out.println("END");
            default -> System.out.println("There is no such action");
        }
    }

    private void printAllRecords(Budget budget) {
        List<GeneralRecord> gen = budget.getAllRecords();
        if (gen.size() != 0) {
            for (GeneralRecord gr : gen) {
                System.out.println(gr);
            }

        } else {
            System.out.println("There is no any record at all...");
        }
    }

    private void printIncomes(Budget budget) {
        List<IncomeRecord> in = budget.getAllIncomes();
        if (in.size() != 0) {
            for (IncomeRecord i : in) {
                System.out.println(i);
            }
        } else {
            System.out.println("There is no any incomes record...");
        }
    }

    private void printOutcomes(Budget budget) {
        List<OutcomeRecord> out = budget.getAllOutcomes();
        if (out.size() != 0) {
            for (OutcomeRecord o : out) {
                System.out.println(o);
            }
        } else {
            System.out.println("There is no any outcomes record...");
        }
    }

    private void printIncomeByConditions(Budget budget, Scanner sc) {
        if (budget.getAllIncomes().size() == 0) {
            System.out.println("There is no any income record...");
            return;
        }
        IncomeCategory cat = inputIncomeCategory(sc);
        LocalDate date = inputDate(sc);
        List<IncomeRecord> in = budget.getIncomesByCondition(cat, date);
        if (in.size() == 0) {
            System.out.println("There is no records by your conditions...");
            return;
        }
        for (IncomeRecord i : in) {
            System.out.println(i);
        }
    }

    private void printOutcomeByConditions(Budget budget, Scanner sc) {
        if (budget.getAllOutcomes().size() == 0) {
            System.out.println("There is no any outcome record...");
            return;
        }
        OutcomeCategory cat = inputOutcomeCategory(sc);
        LocalDate date = inputDate(sc);
        List<OutcomeRecord> out = budget.getOutcomesByCondition(cat, date);
        if (out.size() == 0) {
            System.out.println("There is no records by your conditions...");
            return;
        }
        for (OutcomeRecord o : out) {
            System.out.println(o);
        }
    }

    private void inputRecord(Scanner sc, Budget budget) {
        IncomeCategory incomeCategory;
        OutcomeCategory outcomeCategory;
        LocalDate date = null;
        TransferStatus transferStatus = null;
        OutcomeType outcomeType;
        Person person = null;
        boolean isBankTransfer;

        double sum = inputSum(sc);

        int mode = incomeOrOutcome(sc);

        if (mode == 1) {

            isBankTransfer = inputIsBankTransfer(sc);

            if (isBankTransfer) {
                person = inputPerson(sc);
                date = inputDate(sc);
                transferStatus = inputTransferStatus(sc);
            }
            incomeCategory = inputIncomeCategory(sc);
            budget.addGeneralRecord(new IncomeRecord(++GeneralRecord.recordCount, sum, date, person, transferStatus, incomeCategory, isBankTransfer));
            System.out.println("Income record ID:" + GeneralRecord.recordCount + " added successfully...\n");
        } else {
            outcomeType = inputOutcomeType(sc);
            if (outcomeType == OutcomeType.TRANSFER) {
                person = inputPerson(sc);
                date = inputDate(sc);
                transferStatus = inputTransferStatus(sc);
            }
            outcomeCategory = inputOutcomeCategory(sc);
            budget.addGeneralRecord(new OutcomeRecord(++GeneralRecord.recordCount, sum, date, person, transferStatus, outcomeCategory, outcomeType));
            System.out.println("Outcome record ID:" + GeneralRecord.recordCount + " added successfully...\n");
        }

    }

    private void selectRecordToModify(Scanner sc, Budget budget) {
        printAllRecords(budget);

        int id = inputInt(sc, "Input id: ");
        for (GeneralRecord rec : budget.getAllRecords()) {
            if (rec.getRecordID() == id) {
                if (rec instanceof IncomeRecord) {
                    modifyIncomeRecord((IncomeRecord) rec, sc);
                } else if (rec instanceof OutcomeRecord) {
                    modifyOutcomeRecord((OutcomeRecord) rec, sc);
                }
            } else {
                System.out.println("There is no record with such ID...");
            }
        }
    }

    private void modifyIncomeRecord(IncomeRecord rec, Scanner sc) {
        String action;
        double sum;
        LocalDate date;
        Person person;
        TransferStatus ts;
        IncomeCategory inCat;
        boolean isBankTransfer;
        while (true) {
            System.out.println("Income modify. Select what to modify: ");
            System.out.println("1 -sum- " + rec.getSum());
            System.out.println("2 -date- " + rec.getDate());
            System.out.println("3 -person- " + rec.getPerson());
            System.out.println("4 -transfer status- " + rec.getTransferStatus());
            System.out.println("5 -income category- " + rec.getIncomeCategory());
            System.out.println("6 -is bank transfer- " + rec.isBankTransfer());
            System.out.println("0 - end modification");
            action = sc.nextLine();
            switch (action) {
                case "1" -> {
                    sum = inputSum(sc);
                    rec.setSum(sum);
                }
                case "2" -> {
                    date = inputDate(sc);
                    rec.setDate(date);
                }
                case "3" -> {
                    person = inputPerson(sc);
                    rec.setPerson(person);
                }
                case "4" -> {
                    ts = inputTransferStatus(sc);
                    rec.setTransferStatus(ts);
                }
                case "5" -> {
                    inCat = inputIncomeCategory(sc);
                    rec.setIncomeCategory(inCat);
                }
                case "6" -> {
                    isBankTransfer = inputIsBankTransfer(sc);
                    rec.setBankTransfer(isBankTransfer);
                }
                case "0" -> {
                    System.out.println("Modification done... Returning...");
                    return;
                }
                default -> System.out.println("There is no such modification...");
            }
        }


    }

    private void modifyOutcomeRecord(OutcomeRecord rec, Scanner sc) {
        String action;
        double sum;
        LocalDate date;
        Person person;
        TransferStatus ts;
        OutcomeCategory oc;
        OutcomeType ot;

        while (true) {
            System.out.println("Outcome modify. Select what to modify: ");
            System.out.println("1 -sum- " + rec.getSum());
            System.out.println("2 -date- " + rec.getDate());
            System.out.println("3 -person- " + rec.getPerson());
            System.out.println("4 -transfer status- " + rec.getTransferStatus());
            System.out.println("5 -outcome category- " + rec.getOutcomeCategory());
            System.out.println("6 -outcome type- " + rec.getOutcomeType());
            System.out.println("0 - end modification");
            action = sc.nextLine();
            switch (action) {
                case "1" -> {
                    sum = inputSum(sc);
                    rec.setSum(sum);
                }
                case "2" -> {
                    date = inputDate(sc);
                    rec.setDate(date);
                }
                case "3" -> {
                    person = inputPerson(sc);
                    rec.setPerson(person);
                }
                case "4" -> {
                    ts = inputTransferStatus(sc);
                    rec.setTransferStatus(ts);
                }
                case "5" -> {
                    oc = inputOutcomeCategory(sc);
                    rec.setOutcomeCategory(oc);
                }
                case "6" -> {
                    ot = inputOutcomeType(sc);
                    rec.setOutcomeType(ot);
                }
                case "0" -> {
                    System.out.println("Modification done... Returning...");
                    return;
                }
                default -> System.out.println("There is no such modification...");
            }
        }
    }

    private boolean inputIsBankTransfer(Scanner sc) {
        while (true) {
            try {
                System.out.println("Is income bank transfer?. ");
                System.out.print("1 - true, 2 - false: ");
                String inp = sc.nextLine();
                int input = Integer.parseInt(inp);
                if (input < 1 || input > 2) {
                    System.out.print("1 or 2 please: ");
                    continue;
                }

                return input == 1;
            } catch (NumberFormatException nfe) {
                System.out.println("Wrong format, 1 - true, 2 - false: ");
            }
        }
    }

    private IncomeCategory inputIncomeCategory(Scanner sc) {
        while (true) {
            try {
                System.out.println("Input income category. ");
                System.out.print("1 - SALARY, 2 - EXTRA_MONEY: ");
                String inCat = sc.nextLine();
                int input = Integer.parseInt(inCat);
                if (input < 1 || input > 2) {
                    System.out.print("1 or 2 please: ");
                    continue;
                }
                if (input == 1) {

                    return IncomeCategory.SALARY;
                } else {

                    return IncomeCategory.EXTRA_MONEY;
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Wrong format, 1 - SALARY, 2 - EXTRA_MONEY: ");
            }
        }
    }

    private OutcomeCategory inputOutcomeCategory(Scanner sc) {
        while (true) {
            try {
                System.out.println("Input outcome category. ");
                System.out.print("1 - FOOD, 2 - GAS, 3 - TAXES, 4 - ENTERTAINMENT, 5 - RENT, 6 - EXTRA: ");
                String inOut = sc.nextLine();
                int input = Integer.parseInt(inOut);
                if (input < 1 || input > 6) {
                    System.out.print("1 to 6 please: ");
                    continue;
                }
                switch (input) {
                    case 1 -> {
                        return OutcomeCategory.FOOD;
                    }
                    case 2 -> {
                        return OutcomeCategory.GAS;
                    }
                    case 3 -> {
                        return OutcomeCategory.TAXES;
                    }
                    case 4 -> {
                        return OutcomeCategory.ENTERTAINMENT;
                    }
                    case 5 -> {
                        return OutcomeCategory.RENT;
                    }
                    case 6 -> {
                        return OutcomeCategory.EXTRA;
                    }
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Wrong format, 1 - FOOD, 2 - GAS, 3 - TAXES, 4 - ENTERTAINMENT, 5 - RENT, 6 - EXTRA: ");
            }
        }
    }

    private OutcomeType inputOutcomeType(Scanner sc) {
        while (true) {
            try {
                System.out.println("Input outcome type. ");
                System.out.print("1 - CACHE, 2 - CARD, 3 - TRANSFER: ");
                String outType = sc.nextLine();
                int input = Integer.parseInt(outType);
                if (input < 1 || input > 3) {
                    System.out.print("1, 2 or 3 please: ");
                    continue;
                }
                if (input == 1) {

                    return OutcomeType.CACHE;
                } else if (input == 2) {

                    return OutcomeType.CARD;

                } else return OutcomeType.TRANSFER;

            } catch (NumberFormatException nfe) {
                System.out.println("Wrong format, 1 - CACHE, 2 - CARD, 3 - TRANSFER:  ");
            }
        }
    }

    private Person inputPerson(Scanner sc) {
        System.out.print("Input persons name: ");

        String name = sc.nextLine();

        System.out.print("Input persons surname: ");
        String surname = sc.nextLine();

        int age = inputInt(sc, "Input age: ");

        return new Person(name, surname, age);
    }

    private double inputSum(Scanner sc) {
        String s;
        while (true) {
            try {
                System.out.print("Input sum: ");
                s = sc.nextLine();

                return Double.parseDouble(s);
            } catch (NumberFormatException nfe) {
                System.out.print("Wrong format, numbers only please... ");
            }
        }
    }

    private int inputInt(Scanner sc, String text) {
        String age;
        while (true) {
            try {
                System.out.print(text);
                age = sc.nextLine();

                return Integer.parseInt(age);
            } catch (NumberFormatException nfe) {
                System.out.print("Wrong format, numbers only please... ");
            }
        }
    }

    private LocalDate inputDate(Scanner sc) {
        String d;
        while (true) {
            try {
                System.out.print("Input date: ");
                d = sc.nextLine();

                return LocalDate.parse(d);
            } catch (DateTimeParseException e) {
                System.out.print("Wrong format, yyyy-MM-dd only please... ");
            }
        }
    }

    private int incomeOrOutcome(Scanner sc) {
        while (true) {
            try {
                System.out.print("1 - Income, 2 - Outcome: ");
                String inp = sc.nextLine();
                int input = Integer.parseInt(inp);
                if (input < 1 || input > 2) {
                    System.out.print("1 or 2 please: ");
                    continue;
                }

                return input;
            } catch (NumberFormatException nfe) {
                System.out.println("Wrong format, 1 - Income or 2 - Outcome: ");
            }
        }
    }

    private TransferStatus inputTransferStatus(Scanner sc) {
        while (true) {
            try {
                System.out.println("Input transfer status. ");
                System.out.print("1 - IN_PROGRESS, 2 - COMPLETED, 3 - REJECTED: ");
                String inp = sc.nextLine();
                int input = Integer.parseInt(inp);
                if (input < 1 || input > 3) {
                    System.out.print("1, 2 or 3 please: ");
                    continue;
                }
                if (input == 1) {

                    return TransferStatus.IN_PROGRESS;
                } else if (input == 2) {

                    return TransferStatus.COMPLETED;

                } else return TransferStatus.REJECTED;

            } catch (NumberFormatException nfe) {
                System.out.println("Wrong format, 1 - IN_PROGRESS, 2 - COMPLETED, 3 - REJECTED: ");
            }
        }
    }

    private void deleteByID(Budget budget) {
        if (budget.getAllRecords().size() != 0) {
            int id = inputInt(sc, "Input id: ");
            budget.deleteByID(id);
        } else {
            System.out.println("There is no any record at all...");
        }

    }

}

