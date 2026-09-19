public class LibraryMemberSecurityProperty {

    static class LibraryMember {

        private String membershipId;
        private String name;
        private boolean premiumMember;
        private String securityAnswerHash;

        public String getMembershipId() {
            return membershipId;
        }

        public void setMembershipId(String id) {
            if (membershipId == null) {
                membershipId = id;
            }
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isPremiumMember() {
            return premiumMember;
        }

        public void setPremiumMember(boolean premium) {
            this.premiumMember = premium;
        }

        public void setSecurityAnswer(String answer) {
            if (answer != null) {
                securityAnswerHash = Integer.toHexString(answer.hashCode());
            }
        }
    }

    public static void main(String[] args) {
        LibraryMember member = new LibraryMember();

        member.setMembershipId("LIB-8841");
        member.setName("Priya Nair");
        member.setPremiumMember(true);

        System.out.println(member.getMembershipId());

        member.setMembershipId("FAKE-0000");
        System.out.println(member.getMembershipId());

        System.out.println(member.isPremiumMember());

        member.setSecurityAnswer("BlueMountain");
        System.out.println("Security answer stored");
    }
}
