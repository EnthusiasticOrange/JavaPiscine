public class Program {
    public static void printUser(User user) {
        System.out.println("User:");
        System.out.printf("\tID:      %d\n", user.getId());
        System.out.printf("\tName:    %s\n", user.getName());
        System.out.printf("\tBalance: %d\n", user.getBalance());
        System.out.println();
    }

    public static void printTransaction(Transaction t) {
        System.out.println("Transaction:");
        System.out.printf("\tID:        %s\n", t.getId());
        System.out.printf("\tRecipient: %s\n", t.getRecipient().getName());
        System.out.printf("\tSender:    %s\n", t.getSender().getName());
        System.out.printf("\tCategory:  %s\n", t.getCategory().name());
        System.out.printf("\tAmount:    %d\n", t.getAmount());
        System.out.println();
    }

    public static void main(String[] args) {
        TransactionsService service = new TransactionsService();

        service.addUser("Bob", 500);
        service.addUser("Mike", 100);

        System.out.println("===== Initial balance =====");
        System.out.printf("Bob's balance: %d\n", service.getUserBalance(0));
        System.out.printf("Mike's balance: %d\n", service.getUserBalance(1));
        System.out.println();

        System.out.println("===== Transfering 100 from Bob to Mike =====");
        service.transfer(1, 0, 100);
        System.out.println();

        System.out.println("===== Printing all Bob's transactions =====");
        Transaction[] trArr1 = service.getUserTransactionArray(0);
        for (int i = 0; i < trArr1.length; ++i) {
            printTransaction(trArr1[i]);
        }

        System.out.println("===== New balance =====");
        System.out.printf("Bob's balance: %d\n", service.getUserBalance(0));
        System.out.printf("Mike's balance: %d\n", service.getUserBalance(1));
        System.out.println();

        System.out.println("===== Transfering 200 from Mike to Bob =====");
        service.transfer(0, 1, 200);
        System.out.println();

        System.out.println("===== Printing all Mike's transactions =====");
        Transaction[] trArr2 = service.getUserTransactionArray(1);
        for (int i = 0; i < trArr2.length; ++i) {
            printTransaction(trArr2[i]);
        }

        System.out.println("===== New balance =====");
        System.out.printf("Bob's balance: %d\n", service.getUserBalance(0));
        System.out.printf("Mike's balance: %d\n", service.getUserBalance(1));
        System.out.println();

        System.out.println("===== Transfering 200 from Mike to Bob =====");
        try {
            service.transfer(0, 1, 200);
        } catch (IllegalTransactionException e) {
            System.out.println("Caught IllegalTransactionException");
        }

        System.out.println();

        System.out.println("===== Getting array of invalid transactions =====");
        Transaction[] invArr1 = service.checkTransactionValidity();
        System.out.printf("Invalid array length = %d\n", invArr1.length);
        System.out.println();

        System.out.println("===== Removing all Bob's transactions =====");
        Transaction[] trArr3 = service.getUserTransactionArray(0);
        for (int i = 0; i < trArr3.length; ++i) {
            service.removeTransaction(trArr3[i].getId(), 0);
        }

        System.out.println("===== Getting array of invalid transactions =====");
        Transaction[] invArr2 = service.checkTransactionValidity();
        System.out.printf("Invalid array length = %d\n", invArr2.length);
        System.out.println();
    }
}