interface TransactionsList {
    void add(Transaction t);
    Transaction remove(String id);
    Transaction[] toArray();
    boolean contains(String id);
}