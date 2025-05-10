// ParkOutPage.java
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class ParkOutPage extends JFrame implements ActionListener {
    JLabel carNoLabel;
    JTextField carNoField;
    JButton vacateButton;
    JButton backButton;
    ParkingSystem parkingSystem = ParkingSystem.getInstance();

    // Define billing rates for different vehicle types
    private static final Map<String, Double> basicFees = new HashMap<>();
    private static final Map<String, Double> perMinuteCharges = new HashMap<>();

    static {
        basicFees.put("2 Wheeler", 2.0);
        basicFees.put("4 Wheeler", 5.0);
        basicFees.put("Heavy Vehicle", 10.0);

        perMinuteCharges.put("2 Wheeler", 0.05);
        perMinuteCharges.put("4 Wheeler", 0.10);
        perMinuteCharges.put("Heavy Vehicle", 0.20);
    }
    

    public ParkOutPage() {
        ImageIcon parkingIcon = new ImageIcon("ak2.png");
        this.setIconImage(parkingIcon.getImage());
        setTitle("Park Out");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel cpanel = new JPanel();
        cpanel.setBackground(new Color(169, 169, 169));
        /*JPanel cpanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g); // Ensure the default background is painted
                // Load and draw the image as the background
                Image img = new ImageIcon("ak2.png").getImage(); // Your image path here
                g.drawImage(img, 0, 0, this); // Draw image at (0,0)
            }
        };*/
        cpanel.setOpaque(false);
        
        JPanel panel = new JPanel();
        panel.setBackground(new Color(211, 211, 211));
        
        

        carNoLabel = new JLabel("Enter vehicle Number:");
        carNoLabel.setFont(new Font("segoe UI", Font.BOLD, 24));
        carNoLabel.setForeground(Color.BLACK);
        carNoField = new JTextField(15);
        carNoField.setPreferredSize(new Dimension(120, 30));
        cpanel.add(carNoLabel);
        cpanel.add(Box.createVerticalGlue());
        cpanel.add(carNoField);
        vacateButton = new JButton("Vacate Parking");
        vacateButton.setPreferredSize(new Dimension(190, 80));
        vacateButton.addActionListener(this);
        vacateButton.setFocusable(false);
        vacateButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
        vacateButton.setBackground(Color.WHITE);
        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(190, 80));
        backButton.setFocusable(false);
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
        backButton.setBackground(Color.WHITE);
        backButton.addActionListener(e -> {
            ParkingOptionsPage optionsPage = new ParkingOptionsPage();
            updateFrameProperties(optionsPage);
            dispose();
        });
        cpanel.setPreferredSize(new Dimension(getWidth(), 200));
        panel.add(vacateButton);
        panel.add(backButton);
        add(cpanel, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        setSize(300, 200);
        setLocationRelativeTo(null);
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getRootPane().getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enterAction");
        actionMap.put("enterAction", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vacateButton.doClick(); // Simulate a click on the button
            }
        });
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vacateButton) {
            String carNo = carNoField.getText();
            ParkedCar parkedCar = parkingSystem.getParkedCar(carNo);

            if (parkedCar != null) {
                String vehicleType = parkedCar.getVehicleType(); // Retrieve vehicle type
                LocalDateTime exitTime = LocalDateTime.now();
                long minutesParked = ChronoUnit.MINUTES.between(parkedCar.getEntryTime(), exitTime);

                double basicFee = basicFees.getOrDefault(vehicleType, 0.0);
                double perMinuteCharge = perMinuteCharges.getOrDefault(vehicleType, 0.0);
                double bill = basicFee + (minutesParked * perMinuteCharge);

                String vacatedSlot = parkingSystem.parkOutCar(carNo);

                if (vacatedSlot != null) {
                    for (Frame frame : JFrame.getFrames()) {
                        if (frame instanceof StatusPage) {
                            ((StatusPage) frame).updateSlotColor(vacatedSlot, Color.GREEN);
                            break;
                        }
                    }

                    JOptionPane.showMessageDialog(this, "vehicle with number " + carNo + " (Type: " + vehicleType + ") vacated slot " + vacatedSlot + ".\nParking duration: " + minutesParked + " minutes.\nTotal bill: $" + String.format("%.2f", bill), "Park Out Successful", JOptionPane.INFORMATION_MESSAGE);
                    ParkingOptionsPage optionsPage = new ParkingOptionsPage();
                    updateFrameProperties(optionsPage);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Error during park out.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "vehicle  with number " + carNo + " not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        
    }

    private void updateFrameProperties(JFrame frame) {
        frame.setSize(getSize());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}