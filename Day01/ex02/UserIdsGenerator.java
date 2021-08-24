class UserIdsGenerator {
    private static UserIdsGenerator singleton = new UserIdsGenerator();
    private int lastId;

    private UserIdsGenerator() {
        this.lastId = 0;
    }

    public static UserIdsGenerator getInstance() {
        return singleton;
    }

    public int generateId() {
        return this.lastId++;
    }
}