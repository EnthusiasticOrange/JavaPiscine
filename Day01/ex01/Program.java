public class Program {
    public static void printUser(User user) {
        System.out.println("User:");
        System.out.printf("\tID:      %d\n", user.getId());
        System.out.printf("\tName:    %s\n", user.getName());
        System.out.printf("\tBalance: %d\n", user.getBalance());
        System.out.println();
    }

    public static void main(String[] args) {
        User bob = new User("Bob", 200);

        User mike = new User("Mike", 300);

        printUser(bob);
        printUser(mike);

        System.out.println("===== Generate Users in a loop =====");
        for (int i = 0; i < 3; ++i) {
            User u = new User("Name " + i, i * 1000);
            printUser(u);
        }
    }
}