interface TransactionsList {
    void add(Transaction t);
    void remove(String id);
    Transaction[] toArray();
    boolean contains(String id);
}