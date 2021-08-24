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
        TransactionsService service = new TransactionsService();

        service.addUser("Bob", 500);
        service.addUser("Mike", 100);

        System.out.printf("Bob's balance: %d\n", service.getUserBalance(0));
        System.out.printf("Mike's balance: %d\n", service.getUserBalance(1));

        service.transfer(0, 1, 100);

        Transaction[] trArr = service.getUserTransactionArray(0);
        for (int i = 0; i < trArr.length; ++i) {
            printTransaction(trArr[i]);
        }

        System.out.printf("Bob's balance: %d\n", service.getUserBalance(0));
        System.out.printf("Mike's balance: %d\n", service.getUserBalance(1));
    }
}