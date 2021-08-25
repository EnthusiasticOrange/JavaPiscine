import java.util.UUID;

class Transaction {
    public enum Category {
        Debit,
        Credit
    }

    private final String id;
    private User recipient;
    private User sender;
    private Category category;
    private int amount;

    public Transaction(User sender, User recipient, Category category, int amount) {
        this.id = UUID.randomUUID().toString();
        this.recipient = recipient;
        this.sender = sender;
        this.category = category;

        if ((this.category == Category.Debit && amount > 0)
                || (this.category == Category.Credit && amount < 0)) {
            this.amount = amount;
        } else {
            this.amount = 0;
        }
    }

    void setAmount(int amount) {
        if ((this.category == Category.Debit && amount > 0)
                || (this.category == Category.Credit && amount < 0)) {
            this.amount = amount;
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