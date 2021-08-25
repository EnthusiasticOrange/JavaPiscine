import java.util.UUID;

class Transaction {
    public enum Category {
        Debits,
        Credits
    }

    private final String id;
    private final User recipient;
    private final User sender;
    private Category category;
    private int amount;

    public Transaction(User to, User from, Category category, int amount) {
        this.id = UUID.randomUUID().toString();
        this.recipient = to;
        this.sender = from;
        this.category = category;

        if ((this.category == Category.Debits && amount > 0)
                || (this.category == Category.Credits && amount < 0)) {
            this.amount = amount;
        } else {
            this.amount = 0;
        }
    }
    
    public String getId() {
        return this.id;
    }

    public User getRecipient() {
        return this.recipient;
    }

    public User getSender() {
        return this.sender;
    }

    public Category getCategory() {
        return this.category;
    }

    public int getAmount() {
        return this.amount;
    }
}