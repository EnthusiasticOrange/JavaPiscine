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

        UsersArrayList arrayList = new UsersArrayList();

        printUser(bob);
        printUser(mike);

        System.out.println("===== Adding users to UsersArrayList (Bob, Mike) =====");
        arrayList.add(bob);
        arrayList.add(mike);
        System.out.printf("UsersArrayList size() = %d\n", arrayList.getSize());
        System.out.printf("UsersArrayList capacity() = %d\n", arrayList.getCapacity());
        System.out.println();

        System.out.println("===== Print Mike by index (= 1) =====");
        printUser(arrayList.getByIndex(1));

        System.out.println("===== Print Bob by id (= 1) =====");
        printUser(arrayList.getById(1));

        System.out.println("===== Skip 3 Users =====");
        for (int i = 0; i < 3; ++i) {
            new User("Name " + i, i * 1000);
        }

        System.out.println("===== Adding more users to UsersArrayList (+24) =====");
        for (int i = 0; i < 24; ++i) {
            arrayList.add(new User("Name " + i, i * 1000));
        }
        System.out.printf("UsersArrayList size() = %d\n", arrayList.getSize());
        System.out.printf("UsersArrayList capacity() = %d (10 + 5 + 7 + 11)\n", arrayList.getCapacity());
        System.out.println();

        System.out.println("===== Print User by index = 13 (ID = 13 + 3 + 1) =====");
        printUser(arrayList.getByIndex(13));

        System.out.println("===== Print User by id = 29 (2 + 3 + 24) =====");
        printUser(arrayList.getById(29));

        System.out.println("===== Print User by id = 30 (2 + 3 + 24 + 1) =====");
        try {
            printUser(arrayList.getById(30));
        } catch (UserNotFoundException e) {
            System.out.printf("Caught UserNotFoundException: %s\n", e.getMessage());
            System.out.println();
        }
        System.out.println("END");
    }
}