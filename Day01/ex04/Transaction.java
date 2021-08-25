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

    private Transaction(Transaction other) {
        this.id = other.id;
        this.recipient = other.recipient;
        this.sender = other.sender;
        this.category = other.category;
        this.amount = other.amount;
    }

    void setAmount(int amount) {
        if ((this.category == Category.Debit && amount > 0)
                || (this.category == Category.Credit && amount < 0)) {
            this.amount = amount;
        }
    }

    public Transaction createPairedTransaction() {
        Transaction paired = new Transaction(this);
        if (this.category == Category.Debit) {
            paired.category = Category.Credit;
        } else {
            paired.category = Category.Debit;
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