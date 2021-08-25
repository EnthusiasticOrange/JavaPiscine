import java.util.Scanner;
import java.util.NoSuchElementException;

class Menu {
    enum Mode {
        Production,
        Dev
    }

    TransactionsService service;
    Mode mode;

    public Menu(TransactionsService service) {
        this.service = service;
    }

    public void exec(Mode mode) {
        Scanner s = new Scanner(System.in);
        int lastCommand = (mode == Mode.Dev) ? 7 : 5;
        this.mode = mode;

        System.out.printf("Running in %s mode\n", this.mode);

        while (true) {
            printMenu(lastCommand);

            int command = 0;
            try {
                 command = Integer.parseInt(s.nextLine());
            } catch (NumberFormatException e) {}

            if ((command < 1) || (command > lastCommand)) {
                System.out.println("Unkown command. Try again");
                command = 0;
            }
            
            switch (command) {
                case 1:
                    cmdAddUser();
                    break;
                case 2:
                    cmdViewUserBalance();
                    break;
                case 3:
                    cmdPerformTransfer();
                    break;
                case 4:
                    cmdViewUserTransactions();
                    break;
                case 5:
                    if (this.mode == Mode.Dev) {
                        cmdRemoveTransaction();
                    } else {
                        return;
                    }
                    break;
                case 6:
                    cmdCheckValidity();
                    break;
                case 7:
                    return;
            }
            System.out.println("-----------------------------------------");
        }
    }

    private void printMenu(int lastCommand) {
        System.out.println("1. Add a user");
        System.out.println("2. View user balances");
        System.out.println("3. Perform a transfer");
        System.out.println("4. View all transactions for specific user");
        if (this.mode == Mode.Dev) {
            System.out.println("5. DEV - remove a transfer by ID");
            System.out.println("6. DEV - check transfer validity");
        }
        System.out.printf("%d. Finish execution\n", lastCommand);
    }

    private void cmdAddUser() {
        Scanner s = new Scanner(System.in);

        String input = "";
        String[] split;
        String name = "";
        int balance = 0;

        while (true) {
            System.out.println("Enter a user name and a balance");

            try {
                input = s.nextLine();
            } catch (NoSuchElementException e) {
                System.exit(0);
            }
            
            split = input.split("\\s+");
            if ((split.length == 0) || (split[0].length() == 0)) {
                System.out.print("Error: Name not specified. ");
                continue;
            }
            if (split.length == 1) {
                System.out.print("Error: Balance not specified. ");
                continue;
            }
            if (split.length > 2) {
                System.out.print("Error: Too many arguments. ");
                continue;
            }

            name = split[0];
            try {
                balance = Integer.parseInt(split[1]);
            } catch (NumberFormatException e) {
                System.out.print("Error: Balance is not a number. ");
                continue;
            }

            break;
        }

        int id = service.addUser(name, balance);
        System.out.printf("User with id = %d is added\n", id);
    }

    private void cmdViewUserBalance() {
        Scanner s = new Scanner(System.in);

        String input = "";
        int userId = 0;
        int balance = 0;

        while (true) {
            System.out.println("Enter a user ID");

            try {
                input = s.nextLine();
            } catch (NoSuchElementException e) {
                System.exit(0);
            }
            
            try {
                userId = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Error: User ID is not a number. ");
                continue;
            }

            try {
                balance = service.getUserBalance(userId);
            } catch (RuntimeException e) {
                System.out.printf("Error: %s. ", e.getMessage());
                continue;
            }
            
            break;
        }

        System.out.printf("%s - %d\n", service.getUserName(userId), balance);
    }

    private void cmdPerformTransfer() {
        Scanner s = new Scanner(System.in);

        String input = "";
        String[] split;
        int senderId = 0;
        int recipientId = 0;
        int amount = 0;

        while (true) {
            System.out.println("Enter a sender ID, a recipient ID, and a transfer amount");

            try {
                input = s.nextLine();
            } catch (NoSuchElementException e) {
                return;
            }

            split = input.split("\\s+");
            if ((split.length == 0) || (split[0].length() == 0)) {
                System.out.print("Error: Sender ID not specified. ");
                continue;
            }
            if (split.length == 1) {
                System.out.print("Error: Recipient ID not specified. ");
                continue;
            }
            if (split.length == 2) {
                System.out.print("Error: Transfer amount not specified. ");
                continue;
            }
            if (split.length > 3) {
                System.out.print("Error: Too many arguments. ");
                continue;
            }

            try {
                senderId = Integer.parseInt(split[0]);
            } catch (NumberFormatException e) {
                System.out.print("Error: Sender ID is not a number. ");
                continue;
            }

            try {
                recipientId = Integer.parseInt(split[1]);
            } catch (NumberFormatException e) {
                System.out.print("Error: Recipient ID is not a number. ");
                continue;
            }

            try {
                amount = Integer.parseInt(split[2]);
            } catch (NumberFormatException e) {
                System.out.print("Error: Transfer amount is not a number. ");
                continue;
            }

            try {
                service.transfer(senderId, recipientId, amount);
            } catch (RuntimeException e) {
                System.out.printf("Error: %s. ", e.getMessage());
                continue;
            }

            break;
        }
        System.out.println("The transfer is complete");
    }

    private void cmdViewUserTransactions() {
        Scanner s = new Scanner(System.in);

        String input = "";
        int userId = 0;
        Transaction[] arr;

        while (true) {
            System.out.println("Enter a user ID");

            try {
                input = s.nextLine();
            } catch (NoSuchElementException e) {
                return;
            }

            try {
                userId = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Error: User ID is not a number. ");
                continue;
            }

            try {
                arr = service.getUserTransactionArray(userId);
            } catch (RuntimeException e) {
                System.out.printf("Error: %s. ", e.getMessage());
                continue;
            }

            break;
        }

        for (int i = 0; i < arr.length; ++i) {
            User u = arr[i].getRecipient();
            System.out.printf("To %s(id = %d) %d with id = %s\n",
                    u.getName(), u.getId(), arr[i].getAmount(), arr[i].getId());
        }
    }

    private void cmdRemoveTransaction() {
        Scanner s = new Scanner(System.in);

        String input = "";
        String[] split;
        int userId = 0;
        String transactionId = "";
        Transaction tr;

        while (true) {
            System.out.println("Enter a user ID and a transfer ID");

            try {
                input = s.nextLine();
            } catch (NoSuchElementException e) {
                return;
            }

            split = input.split("\\s+");
            if ((split.length == 0) || (split[0].length() == 0)) {
                System.out.print("Error: User ID not specified. ");
                continue;
            }
            if (split.length == 1) {
                System.out.print("Error: Transfer ID not specified. ");
                continue;
            }
            if (split.length > 2) {
                System.out.print("Error: Too many arguments. ");
                continue;
            }

            try {
                userId = Integer.parseInt(split[0]);
            } catch (NumberFormatException e) {
                System.out.print("Error: User ID is not a number. ");
                continue;
            }

            transactionId = split[1];

            try {
                tr = service.removeTransaction(transactionId, userId);
            } catch (RuntimeException e) {
                System.out.printf("Error: %s. ", e.getMessage());
                continue;
            }

            break;
        }
        User u = tr.getRecipient();
        System.out.printf("Transfer to %s(id = %d) %d removed\n",
                u.getName(), u.getId(), -tr.getAmount());
    }

    private void cmdCheckValidity() {
        System.out.println("Check results:");
        Transaction[] arr = service.checkTransactionValidity();
        User u;
        for (int i = 0; i < arr.length; ++i) {
            User sender = arr[i].getSender();
            User recipient = arr[i].getRecipient();
            if (sender.containsPairedTransaction(arr[i].getId())) {
                u = sender;
            } else {
                u = recipient;
            }

            System.out.printf("%s(id = %d) has an unacknowledged transfer id = %s for %d\n",
                    u.getName(), u.getId(), arr[i].getId(), arr[i].getAmount());
        }
    }
}