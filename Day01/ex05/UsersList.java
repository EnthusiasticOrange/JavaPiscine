interface UsersList {
    void add(User user);
    User getById(int id);
    User getByIndex(int index);
    int getSize();
}