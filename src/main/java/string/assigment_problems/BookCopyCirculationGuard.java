class BookInventory {

    private int copiesTotal;
    private int copiesAvailable;

    public BookInventory(int copiesTotal) {

        if (copiesTotal <= 0) {
            throw new IllegalArgumentException(
                    "Copies must be greater than zero"
            );
        }

        this.copiesTotal = copiesTotal;
        this.copiesAvailable = copiesTotal;
    }

    public void checkOut() {

        if (copiesAvailable > 0) {
            copiesAvailable--;
        }
    }

    public void checkIn() {

        if (copiesAvailable < copiesTotal) {
            copiesAvailable++;
        }
    }

    public int getCopiesAvailable() {
        return copiesAvailable;
    }
}

public class BookCopyCirculationGuard {

    public static void main(String[] args) {

        BookInventory inventory =
                new BookInventory(3);

        inventory.checkOut();
        inventory.checkOut();
        inventory.checkOut();
        inventory.checkOut();

        System.out.println(
                inventory.getCopiesAvailable()
        );

        inventory.checkIn();
        inventory.checkIn();
        inventory.checkIn();
        inventory.checkIn();

        System.out.println(
                inventory.getCopiesAvailable()
        );
    }
}
