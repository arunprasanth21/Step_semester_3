class BookingReceipt {

    private final String bookingId;
    private final String[] seatNumbers;

    public BookingReceipt(String bookingId,
                          String[] seatNumbers) {

        this.bookingId = bookingId;
        this.seatNumbers = seatNumbers.clone();
    }

    public String getBookingId() {
        return bookingId;
    }

    public String[] getSeatNumbers() {
        return seatNumbers.clone();
    }

    public BookingReceipt withUpdatedSeat(
            int index,
            String newSeat) {

        String[] updatedSeats = seatNumbers.clone();

        updatedSeats[index] = newSeat;

        return new BookingReceipt(
                bookingId,
                updatedSeats
        );
    }
}

class GroupBookingReceipt extends BookingReceipt {

    private final int groupSize;

    public GroupBookingReceipt(String bookingId,
                               String[] seatNumbers,
                               int groupSize) {

        super(bookingId, seatNumbers);

        this.groupSize = groupSize;
    }

    public int getGroupSize() {
        return groupSize;
    }
}

public class BookingReceiptSettlement {

    static String processNightlySettlement(
            BookingReceipt[] receipts) {

        int processed = 0;
        int nullSkipped = 0;
        int group = 0;
        int individual = 0;

        for (BookingReceipt receipt : receipts) {

            if (receipt == null) {
                nullSkipped++;
                continue;
            }

            processed++;

            if (receipt instanceof GroupBookingReceipt) {
                group++;
            } else {
                individual++;
            }
        }

        return processed + " processed | " +
               nullSkipped + " null skipped | " +
               group + " group | " +
               individual + " individual";
    }

    public static void main(String[] args) {

        BookingReceipt booking =
                new BookingReceipt(
                        "CH-1001",
                        new String[]{"A1", "A2"}
                );

        String[] seats = booking.getSeatNumbers();

        seats[0] = "X";

        System.out.println(
                booking.getSeatNumbers()[0]
        );

        BookingReceipt updated =
                booking.withUpdatedSeat(1, "A3");

        System.out.println(
                java.util.Arrays.toString(
                        booking.getSeatNumbers()
                )
        );

        System.out.println(
                java.util.Arrays.toString(
                        updated.getSeatNumbers()
                )
        );

        BookingReceipt[] receipts = {

                new GroupBookingReceipt(
                        "CH-2002",
                        new String[]{"B1", "B2"},
                        2
                ),

                null,

                new BookingReceipt(
                        "CH-3003",
                        new String[]{"C1"}
                )
        };

        System.out.println(
                processNightlySettlement(receipts)
        );
    }
}