class User {
    private static int nextId = 1;

    private final int id;
    private String name;
    private int balance;

    public User(String name, int balance) {
        this.id = nextId++;
        this.name = name;
        this.balance = (balance > 0) ? balance : 0;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public void setBalance(int newBalance) {
        if (newBalance >= 0) {
            this.balance = newBalance;
        }
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getBalance() {
        return this.balance;
    }
}