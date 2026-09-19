class MovieBookingProfileData {

    private String name;
    private boolean confirmed;
    private String otp;

    public MovieBookingProfileData() {
    }

    public MovieBookingProfileData(String name) {
        this();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}

public class MovieBookingProfile {

    public static void main(String[] args) {

        MovieBookingProfileData profile =
                new MovieBookingProfileData("Rahul Dev");

        System.out.println(profile.getName());

        profile.setConfirmed(true);

        System.out.println(profile.isConfirmed());

        profile.setOtp("4471");

        System.out.println("OTP stored successfully");
    }
}
