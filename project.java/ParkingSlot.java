public class ParkingSlot {
    private String slotNumber;
    private boolean isOccupied;
    private ParkedCar parkedCar;

    public ParkingSlot(String slotNumber) {
        this.slotNumber = slotNumber;
        this.isOccupied = false;
        this.parkedCar = null;
    }

    public String getSlotNumber() {
        return slotNumber;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public ParkedCar getParkedCar() {
        return parkedCar;
    }

    public void setParkedCar(ParkedCar parkedCar) {
        this.parkedCar = parkedCar;
    }
}