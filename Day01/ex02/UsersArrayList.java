class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
    }

    public UserNotFoundException(String msg) {
        super(msg);
    }
}

class UsersArrayList implements UsersList {
    private int size;
    User[] array;

    public UsersArrayList() {
        this.size = 0;
        this.array = new User[10];
    }

    public void add(User user) {
        if (this.array.length == this.size) {
            User[] tmp = this.array;
            this.array = new User[this.array.length + this.array.length / 2];
            for (int i = 0; i < this.size; ++i) {
                this.array[i] = tmp[i];
            }
        }
        this.array[this.size++] = user;
    }

    public User getById(int id) {
        for (int i = 0; i < this.size; ++i) {
            if (this.array[i].getId() == id) {
                return this.array[i];
            }
        }
        throw new UserNotFoundException("User with ID = " + id + " not found");
    }

    public User getByIndex(int index) {
        return this.array[index];
    }

    public int getSize() {
        return this.size;
    }

    public int getCapacity() {
        return this.array.length;
    }
}