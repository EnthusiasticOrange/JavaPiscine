import java.util.UUID;

class Transaction {
    public enum Category {
        Debits,
        Credits
    }

    private final String id;
    private User recipient;
    private User sender;
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

    private Transaction(Transaction other) {
        this.id = other.id;
        this.recipient = other.recipient;
        this.sender = other.sender;
        this.category = other.category;
        this.amount = other.amount;
    }

    public Transaction createPairedTransaction() {
        Transaction paired = new Transaction(this);
        if (this.category == Category.Debits) {
            paired.category = Category.Credits;
        } else {
            paired.category = Category.Debits;
        }
        paired.amount = -this.amount;
        paired.sender = this.recipient;
        paired.recipient = this.sender;

        return paired;
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