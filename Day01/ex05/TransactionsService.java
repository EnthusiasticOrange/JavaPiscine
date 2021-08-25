class IllegalTransactionException extends RuntimeException {
    public IllegalTransactionException(String msg) {
        super(msg);
    }
}

class TransactionsService {
    private UsersList usersList;

    public TransactionsService() {
        this.usersList = new UsersArrayList();
    }

    public int addUser(String name, int balance) {
        User u = new User(name, balance);
        usersList.add(u);
        
        return u.getId();
    }

    public int getUserBalance(int id) {
        return this.usersList.getById(id).getBalance();
    }

    public String getUserName(int id) {
        return this.usersList.getById(id).getName();
    }

    public void transfer(int senderId, int recipientId, int amount) {
        User sender = this.usersList.getById(senderId);
        User recipient = this.usersList.getById(recipientId);

        if (amount > 0 && sender.getBalance() < amount) {
            String msg = "User with ID = " + senderId + " has insufficient funds";
            throw new IllegalTransactionException(msg);
        }
        if (amount < 0 && recipient.getBalance() < -amount) {
            String msg = "User with ID = " + recipientId + " has insufficient funds";
            throw new IllegalTransactionException(msg);
        }

        Transaction.Category cat = (amount < 0) ? Transaction.Category.Credits
                                                : Transaction.Category.Debits;
        Transaction tr1 = new Transaction(sender, recipient, cat, amount);
        Transaction tr2 = tr1.createPairedTransaction();

        recipient.addTransaction(tr1);
        sender.addTransaction(tr2);

        sender.setBalance(sender.getBalance() - amount);
        recipient.setBalance(recipient.getBalance() + amount);
    }

    public Transaction[] getUserTransactionArray(int id) {
        return this.usersList.getById(id).getTransactionArray();
    }

    public Transaction removeTransaction(String id, int userId) {
        User u = this.usersList.getById(userId);
        return u.removeTransaction(id);
    }

    public Transaction[] checkTransactionValidity() {
        TransactionsList lst = new TransactionsLinkedList();
        for (int i = 0; i < usersList.getSize(); ++i) {
            User u = usersList.getByIndex(i);

            Transaction[] arr = u.getTransactionArray();
            for (int j = 0; j < arr.length; ++j) {
                User other = (arr[j].getRecipient().getId() == u.getId())
                             ? arr[j].getSender() : arr[j].getRecipient();
                if (!other.containsPairedTransaction(arr[j].getId())) {
                    lst.add(arr[j]);
                }
            }
        }
        return lst.toArray();
    }
}