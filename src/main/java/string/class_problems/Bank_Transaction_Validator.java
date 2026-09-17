public class Bank_Transaction_Validator {

    static String normalizeReference(String raw) {

        String reference = raw.trim();

        if (reference.length() < 3) {
            return reference;
        }

        String bankCode =
                reference.substring(0, 3).toUpperCase();

        return bankCode + reference.substring(3);
    }


    static String validateAndFormat(String reference) {

        // Check length
        if (reference.length() != 14) {
            return "Invalid: wrong length";
        }

        // Check first 3 characters
        for (int i = 0; i < 3; i++) {

            if (!Character.isLetter(reference.charAt(i))) {
                return "Invalid: bank code must be 3 letters";
            }
        }

        // Check remaining 11 characters
        for (int i = 3; i < reference.length(); i++) {

            if (!Character.isDigit(reference.charAt(i))) {
                return "Invalid: body must contain only digits";
            }
        }

        // Extract parts
        String bankCode =
                reference.substring(0, 3);

        String date =
                reference.substring(3, 9);

        String sequence =
                reference.substring(9);

        // Build output
        StringBuilder result =
                new StringBuilder();

        result.append("[")
                .append(bankCode)
                .append("] DATE: ")
                .append(date.substring(0, 2))
                .append("/")
                .append(date.substring(2, 4))
                .append("/")
                .append(date.substring(4, 6))
                .append(" | SEQ: ")
                .append(sequence);

        return result.toString();
    }


    public static void main(String[] args) {

        String validReference =
                normalizeReference(
                        " hdf03022600042 "
                );

        System.out.println(
                validateAndFormat(validReference)
        );


        String invalidReference =
                normalizeReference(
                        "12F03022600042"
                );

        System.out.println(
                validateAndFormat(invalidReference)
        );
    }
}
