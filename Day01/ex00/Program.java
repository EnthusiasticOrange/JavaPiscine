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

        Transaction t1 = new Transaction(mike, bob,
                Transaction.Category.Debits, 500);

        printUser(bob);
        printUser(mike);
        printTransaction(t1);

        System.out.println("===== Changing Bob's name =====");
        bob.setName("MegaBob");
        printUser(bob);

        System.out.println("===== Changing Mike's balance to 1000 =====");
        mike.setBalance(1000);
        printUser(mike);

        System.out.println("===== Changing (Mega)Bob's balance to -3 =====");
        bob.setBalance(-3);
        printUser(bob);

    }
}