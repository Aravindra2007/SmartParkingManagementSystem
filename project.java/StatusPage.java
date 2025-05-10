// StatusPage.java
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class StatusPage extends JFrame {
    JPanel slotsPanel;
    ParkingSystem parkingSystem = ParkingSystem.getInstance();
    Map<String, JButton> slotButtonMap = new HashMap<>();
    JButton backButton;

    public StatusPage() {
        setTitle("Parking Status");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        ImageIcon parkingIcon = new ImageIcon("Parking.png"); 
        this.setIconImage(parkingIcon.getImage());
        slotsPanel = new JPanel(new GridLayout(3, 4, 15, 20));
        for (String slotNumber : parkingSystem.getAllSlotNumbers()) {
            JButton slotButton = new JButton(slotNumber);
            slotButton.setFocusable(false);
            slotButton.setBackground(parkingSystem.isSlotAvailable(slotNumber) ? new Color(144,238,144) : Color.RED);
            slotsPanel.add(slotButton);
            slotButtonMap.put(slotNumber, slotButton);
        }
        add(slotsPanel, BorderLayout.CENTER);

        backButton = new JButton("Back");
        backButton.setFocusable(false);
        backButton.setFont(new Font("Segoe UI",Font.BOLD,34));
        backButton.setBackground(new Color(211, 211, 211));
        backButton.addActionListener(e -> {
            ParkingOptionsPage optionsPage = new ParkingOptionsPage();
            updateFrameProperties(optionsPage);
            dispose();
        });
        add(backButton, BorderLayout.SOUTH);

        setSize(400, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void updateSlotColor(String slotNumber, Color color) {
        if (slotButtonMap.containsKey(slotNumber)) {
            slotButtonMap.get(slotNumber).setBackground(color);
        }
    }

    private void updateFrameProperties(JFrame frame) {
        frame.setSize(getSize());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}