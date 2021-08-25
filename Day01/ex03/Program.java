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
        System.out.printf("\tAmiount:   %d\n", t.getAmount());
        System.out.println();
    }

    public static void main(String[] args) {
        User bob = new User("Bob", 200);

        User mike = new User("Mike", 300);

        User tom = new User("Tom", 12000);

        printUser(bob);
        printUser(mike);

        System.out.println("===== Adding Debit transaction Bob => Mike =====");
        Transaction t1 = new Transaction(bob, mike, Transaction.Category.Debit, 200);
        printTransaction(t1);
        bob.addTransaction(t1);
        mike.addTransaction(t1);

        System.out.println("===== Adding Credit transaction Tom => Mike =====");
        Transaction t2 = new Transaction(tom, mike, Transaction.Category.Credit, -800);
        printTransaction(t2);
        tom.addTransaction(t2);
        mike.addTransaction(t2);

        System.out.println("===== Printing Bob's transactions =====");
        Transaction[] bobTr = bob.getTransactionArray();
        for (int i = 0; i < bobTr.length; ++i) {
            printTransaction(bobTr[i]);
        }

        System.out.println("===== Printing Mikes's transactions =====");
        Transaction[] mikeTr = mike.getTransactionArray();
        for (int i = 0; i < mikeTr.length; ++i) {
            printTransaction(mikeTr[i]);
        }

        System.out.println("===== Printing Tom's transactions =====");
        Transaction[] tomTr = tom.getTransactionArray();
        for (int i = 0; i < tomTr.length; ++i) {
            printTransaction(tomTr[i]);
        }

        System.out.println("===== Removing Mike's transactions by ID =====");
        String id = mikeTr[1].getId();
        System.out.printf("ID: %s\n", id);
        mike.removeTransaction(id);

        System.out.println();
        System.out.println("===== Printing Mikes's transactions =====");
        mikeTr = mike.getTransactionArray();
        for (int i = 0; i < mikeTr.length; ++i) {
            printTransaction(mikeTr[i]);
        }

        System.out.println("===== Removing Mike's transactions by same ID again =====");
        System.out.printf("ID: %s\n", id);
        try {
            mike.removeTransaction(id);
        } catch (TransactionNotFoundException e) {
            System.out.printf("Caught TransactionNotFoundException: %s\n", e.getMessage());
        }

        System.out.println();
        System.out.println("END");
    }
}