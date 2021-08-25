class IllegalTransactionException extends RuntimeException {
}

class TransactionsService {
    private UsersList usersList;

    public TransactionsService() {
        this.usersList = new UsersArrayList();
    }

    public void addUser(String name, int balance) {
        usersList.add(new User(name, balance));
    }

    public int getUserBalance(int id) {
        return this.usersList.getById(id).getBalance();
    }

    public void transfer(int recipientId, int senderId, int amount) {
        User to = this.usersList.getById(recipientId);
        User from = this.usersList.getById(senderId);

        if ((amount > 0 && from.getBalance() < amount)
                || (amount < 0 && to.getBalance() < -amount)) {
            throw new IllegalTransactionException();
        }

        Transaction.Category cat = (amount < 0) ? Transaction.Category.Credits
                                                : Transaction.Category.Debits;
        Transaction tr1 = new Transaction(from, to, cat, amount);
        Transaction tr2 = tr1.createPairedTransaction();

        to.addTransaction(tr1);
        from.addTransaction(tr2);

        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);
    }

    public Transaction[] getUserTransactionArray(int id) {
        return this.usersList.getById(id).getTransactionArray();
    }

    public void removeTransaction(String id, int userId) {
        User u = this.usersList.getById(userId);
        u.removeTransaction(id);
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