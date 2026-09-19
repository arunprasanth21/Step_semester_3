class AccessChecker {

    static String classifyAccess(String fieldModifier,
                                 String accessorContext) {

        if (fieldModifier.equals("private")) {

            if (accessorContext.equals("SAME_CLASS")) {
                return "ALLOWED";
            }

            return "DENIED";
        }

        if (fieldModifier.equals("default")) {

            if (accessorContext.equals("SAME_CLASS")
                    || accessorContext.equals("SAME_PACKAGE")) {
                return "ALLOWED";
            }

            return "DENIED";
        }

        if (fieldModifier.equals("protected")) {

            if (accessorContext.equals("SAME_CLASS")
                    || accessorContext.equals("SAME_PACKAGE")
                    || accessorContext.equals(
                    "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE")) {
                return "ALLOWED";
            }

            return "DENIED";
        }

        if (fieldModifier.equals("public")) {
            return "ALLOWED";
        }

        return "DENIED";
    }
}

public class SubclassTicketAccess {

    public static void main(String[] args) {

        System.out.println(
                AccessChecker.classifyAccess(
                        "protected",
                        "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"
                )
        );

        System.out.println(
                AccessChecker.classifyAccess(
                        "protected",
                        "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"
                )
        );

        System.out.println(
                AccessChecker.classifyAccess(
                        "private",
                        "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"
                )
        );

        System.out.println(
                AccessChecker.classifyAccess(
                        "default",
                        "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"
                )
        );

        System.out.println(
                AccessChecker.classifyAccess(
                        "public",
                        "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"
                )
        );
    }
}