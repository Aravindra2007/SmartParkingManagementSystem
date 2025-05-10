import java.time.LocalDateTime;

public class ParkedCar {
    private String carNo;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private String vehicleType; 
    private String ownerName;

    public ParkedCar(String ownerName, String carNo, LocalDateTime entryTime, String vehicleType) {
        this.ownerName = ownerName;
        this.carNo = carNo;
        this.entryTime = entryTime;
        this.exitTime = null;
        this.vehicleType = vehicleType;
    }
    public String getOwnerName() {
        return ownerName;
    }
    public String getCarNo() {
        return carNo;
    }
    public LocalDateTime getEntryTime() {
        return entryTime;
    }
    public LocalDateTime getExitTime() {
        return exitTime;
    }
    public void setExitTime(LocalDateTime exitTime) {
        this.exitTime = exitTime;
    }
    public String getVehicleType() {
        return vehicleType;
    }
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }
}