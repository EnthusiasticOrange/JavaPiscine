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
        User from = this.usersList.getById(recipientId);
        User to = this.usersList.getById(senderId);

        if ((amount > 0 && from.getBalance() < amount)
                || (amount < 0 && to.getBalance() < -amount)) {
            throw new IllegalTransactionException();
        }

        Transaction.Category cat = (amount < 0) ? Transaction.Category.Credits
                                                : Transaction.Category.Debits;
        Transaction fromTr = new Transaction(from, to, cat, amount);
        Transaction toTr = fromTr.createPairedTransaction();

        from.addTransaction(fromTr);
        to.addTransaction(toTr);

        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);
    }

    public Transaction[] getUserTransactionArray(int id) {
        return this.usersList.getById(id).getTransactionArray();
    }
}