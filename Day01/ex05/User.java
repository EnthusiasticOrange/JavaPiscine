class User {
    private final int id;
    private String name;
    private int balance;
    TransactionsList tList;

    public User(String name, int balance) {
        this.id = UserIdsGenerator.getInstance().generateId();
        this.name = name;
        this.balance = (balance > 0) ? balance : 0;
        this.tList = new TransactionsLinkedList();
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public void setBalance(int newBalance) {
        if (newBalance >= 0) {
            this.balance = newBalance;
        }
    }

    public Transaction[] getTransactionArray() {
        return this.tList.toArray();
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

    public void addTransaction(Transaction t) {
        this.tList.add(t);
    }

    public Transaction removeTransaction(String id) {
        return this.tList.remove(id);
    }

    public boolean containsPairedTransaction(String id) {
        return this.tList.contains(id);
    }
}