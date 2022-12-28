import data.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
            case "2" -> System.out.println(budget.getAllRecords());
            case "3" -> System.out.println(budget.getAllIncomes());
            case "4" -> System.out.println(budget.getAllOutcomes());
            case "5" -> System.out.println("Print income by category and date");
            case "6" -> System.out.println("Print outcome by category and date");
            case "7" -> System.out.println("Modify record by ID");
            case "8" -> System.out.println("Delete record by ID");
            case "9" -> System.out.println(budget.getBalance());
            case "0" -> System.out.println("END");
            default -> System.out.println("There is no such action");
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

            if(isBankTransfer){
                person = inputPerson(sc);
                date = inputDate(sc);
                transferStatus = inputTransferStatus(sc);
            }
            incomeCategory = inputIncomeCategory(sc);
            budget.addGeneralRecord(new IncomeRecord(++GeneralRecord.recordCount, sum, date, person, transferStatus, incomeCategory, isBankTransfer));
            System.out.println("Income record ID:" + GeneralRecord.recordCount + " added successfully...\n");
        } else {
            outcomeType = inputOutcomeType(sc);
            if (outcomeType == OutcomeType.TRANSFER){
                person = inputPerson(sc);
                date = inputDate(sc);
                transferStatus = inputTransferStatus(sc);
            }
            outcomeCategory = inputOutcomeCategory(sc);
            budget.addGeneralRecord(new OutcomeRecord(++GeneralRecord.recordCount, sum, date, person, transferStatus, outcomeCategory, outcomeType));
            System.out.println("Outcome record ID:" + GeneralRecord.recordCount + " added successfully...\n");
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
                if (input == 1) {
                    return OutcomeCategory.FOOD;
                } else if (input == 2) {
                    return OutcomeCategory.GAS;
                } else if (input == 3) {
                    return OutcomeCategory.TAXES;
                } else if (input == 4) {
                    return OutcomeCategory.ENTERTAINMENT;
                } else if (input == 5) {
                    return OutcomeCategory.RENT;
                }else {
                    return OutcomeCategory.EXTRA;
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

        int age = inputAge(sc);

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

    private int inputAge(Scanner sc) {
        String age;
        while (true) {
            try {
                System.out.print("Input age: ");
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

}
