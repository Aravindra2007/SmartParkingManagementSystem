// ParkInPage.java
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class ParkInPage extends JFrame implements ActionListener {
    JLabel carTypeLabel, carNoLabel;
    JComboBox<String> carTypeComboBox;
    JTextField carNoField;
    JPanel slotsPanel;
    JButton confirmButton;
    JButton selectedSlotButton = null;
    ParkingSystem parkingSystem = ParkingSystem.getInstance();
    String selectedVehicleType = null; 

    public ParkInPage() {
        setTitle("Park In");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        ImageIcon parkingIcon = new ImageIcon("Parking.png");
        this.setIconImage(parkingIcon.getImage());
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        

        carTypeLabel = new JLabel("vehicle Type:");
        carTypeLabel.setFont(new Font("segoe UI", Font.BOLD, 18));
        String[] carTypes = {"2 Wheeler", "4 Wheeler", "Heavy Vehicle"};
        carTypeComboBox = new JComboBox<>(carTypes);
        carTypeComboBox.setFont(new Font("segoe UI", Font.PLAIN, 16));
        carTypeComboBox.addActionListener(this);

        carNoLabel = new JLabel("vehicle Number:");
        carNoLabel.setFont(new Font("segoe UI", Font.BOLD, 18));
        carNoField = new JTextField(30);

        inputPanel.add(carTypeLabel);
        inputPanel.add(carTypeComboBox);
        inputPanel.add(carNoLabel);
        inputPanel.add(carNoField);
        inputPanel.setPreferredSize(new Dimension(getWidth(), 100));
        add(inputPanel, BorderLayout.NORTH);

        slotsPanel = new JPanel(new GridLayout(3, 4, 20, 20));
        for (String slotNumber : parkingSystem.getAllSlotNumbers()) {
            JButton slotButton = new JButton(slotNumber);
            
            slotButton.setFocusable(false);
            slotButton.setBackground(parkingSystem.isSlotAvailable(slotNumber) ? new Color(255,255,255) : new Color(144,238,144)); // Available slots are white, occupied slots are light green
            slotButton.addActionListener(e -> {
                if (parkingSystem.isSlotAvailable(slotButton.getText())) {
                    if (selectedSlotButton != null) {
                        selectedSlotButton.setBackground(parkingSystem.isSlotAvailable(selectedSlotButton.getText()) ? new Color(255,255,255) : new Color(144,238,144)); // Reset previous selection
                    }
                    selectedSlotButton = slotButton;
                    selectedSlotButton.setBackground(new Color(144,238,144)); // Indicate selection
                } else {
                    JOptionPane.showMessageDialog(ParkInPage.this, "Slot " + slotButton.getText() + " is occupied.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            slotsPanel.add(slotButton);
        }
        add(slotsPanel, BorderLayout.CENTER);

        confirmButton = new JButton("Confirm Parking");
        confirmButton.addActionListener(this);
        confirmButton.setPreferredSize(new Dimension(getWidth(), 80));
        confirmButton.setBackground(new Color(169, 169, 169));
        confirmButton.setForeground(Color.BLACK);
        confirmButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
        confirmButton.setFocusable(false);
        add(confirmButton, BorderLayout.SOUTH);
        inputPanel.setBackground(new Color(169, 169, 169));
        slotsPanel.setBackground(new Color(211, 211, 211));
        setSize(400, 350);
        setLocationRelativeTo(null);
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getRootPane().getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enterAction");
        actionMap.put("enterAction", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmButton.doClick(); 
            }
        });
        setVisible(true);
    }


 

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirmButton) {
            String carType = (String) carTypeComboBox.getSelectedItem();
            String carNo = carNoField.getText();
            selectedVehicleType = carType; // Update the selected vehicle type

            if (carType == null || carNo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select vehicle type and enter vehicle number.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (selectedSlotButton == null) {
                JOptionPane.showMessageDialog(this, "Please select a parking slot.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String selectedSlot = selectedSlotButton.getText();
            boolean parked = parkingSystem.parkCar(selectedSlot, carType, carNo); 

            if (parked) {
                selectedSlotButton.setBackground(Color.RED);
                JOptionPane.showMessageDialog(this, carType + " with number " + carNo + " parked successfully in slot " + selectedSlot, "Success", JOptionPane.INFORMATION_MESSAGE);
                ParkingOptionsPage optionsPage = new ParkingOptionsPage();
                updateFrameProperties(optionsPage);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error parking car.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == carTypeComboBox) {
            selectedVehicleType = (String) carTypeComboBox.getSelectedItem();
        }
        
    }

    private void updateFrameProperties(JFrame frame) {
        frame.setSize(getSize());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}