interface WashType {
    String getName();
    int getDuration();
    double getCharge();
}

class QuickWash implements WashType {
    public String getName() {
        return "Quick";
    }

    public int getDuration() {
        return 30;
    }

    public double getCharge() {
        return 20.00;
    }
}

class NormalWash implements WashType {
    public String getName() {
        return "Normal";
    }

    public int getDuration() {
        return 45;
    }

    public double getCharge() {
        return 30.00;
    }
}

class HeavyWash implements WashType {
    public String getName() {
        return "Heavy";
    }

    public int getDuration() {
        return 60;
    }

    public double getCharge() {
        return 45.00;
    }
}

class DelicateWash implements WashType {
    public String getName() {
        return "Delicate";
    }

    public int getDuration() {
        return 50;
    }

    public double getCharge() {
        return 35.00;
    }
}

class Student {
    private String name;

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class WashCycle {
    private Student student;
    private WashingMachine machine;
    private WashType washType;

    public WashCycle(Student student, WashingMachine machine, WashType washType) {
        this.student = student;
        this.machine = machine;
        this.washType = washType;
    }

    public Student getStudent() {
        return student;
    }

    public WashingMachine getMachine() {
        return machine;
    }

    public WashType getWashType() {
        return washType;
    }
}

class WashingMachine {
    private String machineId;
    private boolean busy;
    private WashCycle currentCycle;

    public WashingMachine(String machineId) {
        this.machineId = machineId;
        this.busy = false;
    }

    public String getMachineId() {
        return machineId;
    }

    public boolean isBusy() {
        return busy;
    }

    public void startWash(Student student, WashType washType) {

        if (busy) {
            System.out.println(
                "Machine " + machineId + " is currently busy."
            );
            return;
        }

        currentCycle = new WashCycle(student, this, washType);
        busy = true;

        System.out.printf(
            "%s wash started on %s for %s (%d min).%n",
            washType.getName(),
            machineId,
            student.getName(),
            washType.getDuration()
        );

        System.out.printf(
            "Charge: ₹%.2f.%n",
            washType.getCharge()
        );
    }

    public void completeCycle() {

        if (!busy) {
            System.out.println(
                "Machine " + machineId + " is already free."
            );
            return;
        }

        System.out.println(
            machineId + " cycle completed."
        );

        currentCycle = null;
        busy = false;

        System.out.println(
            machineId + " is now free."
        );
    }
}

public class HostelLaundryQueue {

    public static void main(String[] args) {

        Student asha = new Student("Asha");
        Student ravi = new Student("Ravi");
        Student neha = new Student("Neha");

        WashingMachine m1 = new WashingMachine("M1");
        WashingMachine m2 = new WashingMachine("M2");

        m1.startWash(asha, new QuickWash());

        m1.startWash(ravi, new HeavyWash());

        m2.startWash(ravi, new HeavyWash());

        m1.completeCycle();

        m1.startWash(neha, new NormalWash());
    }
}