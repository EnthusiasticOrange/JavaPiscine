class UserNotFoundException extends RuntimeException {
}

class UsersArrayList implements UsersList {
    private int capacity;
    private int size;
    User[] array;

    public UsersArrayList() {
        this.capacity = 10;
        this.size = 0;
        this.array = new User[this.capacity];
    }

    public void addUser(User user) {
        if (this.capacity == this.size) {
            User[] tmp = this.array;
            this.capacity += this.capacity / 2;
            this.array = new User[this.capacity];
            for (int i = 0; i < this.size; ++i) {
                this.array[i] = tmp[i];
            }
        }
        this.array[this.size++] = user;
    }

    public User getUserById(int id) {
        for (int i = 0; i < this.size; ++i) {
            if (this.array[i].getId() == id) {
                return this.array[i];
            }
        }
        throw new UserNotFoundException();
    }

    public User getUserByIndex(int index) {
        return this.array[index];
    }

    public int getSize() {
        return this.size;
    }

    public int getCapacity() {
        return this.capacity;
    }
}