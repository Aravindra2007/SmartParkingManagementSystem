import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import javax.swing.JOptionPane;

public class ParkingSystem {
    private static ParkingSystem instance;
    private Map<String, ParkingSlot> parkingSlots;
    private LinkedList<ParkedCar> parkingHistory;
    private static final int MAX_HISTORY = 5;
    private Map<String, String> slotTypes; 

    private ParkingSystem() {
        parkingSlots = new HashMap<>();
        slotTypes = new HashMap<>();
        for (int i = 1; i <= 4; i++) {
            String slotNumber = "2W" + i;
            parkingSlots.put(slotNumber, new ParkingSlot(slotNumber));
            slotTypes.put(slotNumber, "2 Wheeler");
        }
        for (int i = 1; i <= 6; i++) {
            String slotNumber = "4W" + i;
            parkingSlots.put(slotNumber, new ParkingSlot(slotNumber));
            slotTypes.put(slotNumber, "4 Wheeler");
        }
        for (int i = 1; i <= 2; i++) {
            String slotNumber = "HW" + i;
            parkingSlots.put(slotNumber, new ParkingSlot(slotNumber));
            slotTypes.put(slotNumber, "Heavy Vehicle");
        }
        parkingHistory = new LinkedList<>();
    }

    public static ParkingSystem getInstance() {
        if (instance == null) {
            instance = new ParkingSystem();
        }
        return instance;
    }

    public ArrayList<String> getAvailableSlots() {
        ArrayList<String> available = new ArrayList<>();
        for (ParkingSlot slot : parkingSlots.values()) {
            if (!slot.isOccupied()) {
                available.add(slot.getSlotNumber());
            }
        }
        return available;
    }

    public String[] getAllSlotNumbers() {
        return parkingSlots.keySet().toArray(new String[0]);
    }

    public boolean isSlotAvailable(String slotNumber) {
        return parkingSlots.containsKey(slotNumber) && !parkingSlots.get(slotNumber).isOccupied();
    }

    public boolean parkCar(String slotNumber, String vehicleType, String carNo) {
        if (isCarParked(carNo)) { // Check if the car is already parked
            JOptionPane.showMessageDialog(null, "vehicle with number " + carNo + " is already parked.", "Parking Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (parkingSlots.containsKey(slotNumber) && !parkingSlots.get(slotNumber).isOccupied()) {
            String allowedType = slotTypes.get(slotNumber);
            if (allowedType != null && allowedType.equals(vehicleType)) {
                parkingSlots.get(slotNumber).setOccupied(true);
                parkingSlots.get(slotNumber).setParkedCar(new ParkedCar(null, carNo, LocalDateTime.now(), vehicleType)); // Owner name is null here
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Slot " + slotNumber + " is not suitable for " + vehicleType + ".", "Parking Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return false;
    }

    public String parkOutCar(String carNo) {
        for (ParkingSlot slot : parkingSlots.values()) {
            if (slot.isOccupied() && slot.getParkedCar().getCarNo().equals(carNo)) {
                slot.setOccupied(false);
                slot.getParkedCar().setExitTime(LocalDateTime.now());
                parkingHistory.addFirst(slot.getParkedCar());
                if (parkingHistory.size() > MAX_HISTORY) {
                    parkingHistory.removeLast();
                }
                String vacatedSlot = slot.getSlotNumber();
                slot.setParkedCar(null);
                return vacatedSlot;
            }
        }
        return null;
    }

    public ParkedCar getParkedCar(String carNo) {
        for (ParkingSlot slot : parkingSlots.values()) {
            if (slot.isOccupied() && slot.getParkedCar().getCarNo().equals(carNo)) {
                return slot.getParkedCar();
            }
        }
        return null;
    }

    public LinkedList<ParkedCar> getParkingHistory() {
        return parkingHistory;
    }

    public Map<String, ParkingSlot> getAllSlots() {
        return parkingSlots;
    }

    public ArrayList<String> getAvailableSlotsForType(String vehicleType) {
        ArrayList<String> availableSlotsForType = new ArrayList<>();
        for (Map.Entry<String, String> entry : slotTypes.entrySet()) {
            String slotNumber = entry.getKey();
            String allowedType = entry.getValue();
            if (allowedType.equals(vehicleType) && !parkingSlots.get(slotNumber).isOccupied()) {
                availableSlotsForType.add(slotNumber);
            }
        }
        return availableSlotsForType;
    }

    private boolean isCarParked(String carNo) {
        for (ParkingSlot slot : parkingSlots.values()) {
            if (slot.isOccupied() && slot.getParkedCar().getCarNo().equals(carNo)) {
                return true;
            }
        }
        return false; 
    }
}
