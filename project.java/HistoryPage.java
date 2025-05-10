// HistoryPage.java
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.*;

public class HistoryPage extends JFrame implements ActionListener {
    JLabel passcodeLabel;
    JPasswordField passcodeField;
    JButton viewHistoryButton;
    JTextArea historyTextArea;
    JButton backButton; 
    ParkingSystem parkingSystem = ParkingSystem.getInstance();
    private final String SECRET_CODE = "123456"; 

    public HistoryPage() {
        setTitle("Parking History");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        ImageIcon parkingIcon = new ImageIcon("Parking.png");
        this.setIconImage(parkingIcon.getImage());
        JPanel passcodePanel = new JPanel(new FlowLayout());
        passcodeLabel = new JLabel("Enter Passcode:");
        passcodeLabel.setFont(new Font("segoe UI", Font.BOLD, 24));
        passcodeField = new JPasswordField(10);
        passcodeField.setPreferredSize(new Dimension(150, 40));
        viewHistoryButton = new JButton("View History");
        viewHistoryButton.setFocusable(false);
        viewHistoryButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        viewHistoryButton.setBackground(Color.WHITE);

        
        passcodePanel.setBackground(new Color(169, 169, 169));
        passcodePanel.setPreferredSize(new Dimension(getWidth(), 100));
        viewHistoryButton.addActionListener(this);
        passcodePanel.add(passcodeLabel);
        passcodePanel.add(passcodeField);
        passcodePanel.add(viewHistoryButton);
        add(passcodePanel, BorderLayout.NORTH);

        historyTextArea = new JTextArea(10, 30);
        historyTextArea.setEditable(false);
        add(new JScrollPane(historyTextArea), BorderLayout.CENTER);

        backButton = new JButton("Back");
        backButton.setBackground(new Color(211, 211, 211));
        backButton.setFocusable(false);
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
        backButton.addActionListener(this);
        add(backButton, BorderLayout.SOUTH);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == viewHistoryButton) {
            char[] enteredPasscode = passcodeField.getPassword();
            if (SECRET_CODE.equals(new String(enteredPasscode))) {
                LinkedList<ParkedCar> history = parkingSystem.getParkingHistory();
                historyTextArea.setText("");
                historyTextArea.setForeground(Color.BLACK);
                historyTextArea.setFont(new Font("segoe UI", Font.PLAIN, 16));

                if (history.isEmpty()) {
                    historyTextArea.append("No parking history available.\n");
                } else {
                    historyTextArea.append(String.format("%-15s %-15s %-25s %-25s %-15s%n",
                            "Owner Name", "vehicle No", "Entry Time", "Exit Time", "Vehicle Type"));
                    historyTextArea.append("----------------------------------------------------------------------------------------------------\n");
                    for (ParkedCar car : history) {
                        historyTextArea.append(String.format("%-15s %-15s %-25s %-25s %-15s%n",
                                car.getOwnerName(), car.getCarNo(), car.getEntryTime(),
                                car.getExitTime() == null ? "N/A" : car.getExitTime(), car.getVehicleType()));
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Incorrect passcode.", "Error", JOptionPane.ERROR_MESSAGE);
                passcodeField.setText("");
            }
        }
        if(e.getSource()==backButton){
            ParkingOptionsPage optionsPage = new ParkingOptionsPage();
            updateFrameProperties(optionsPage);
            dispose();
        }
    }

    private void updateFrameProperties(JFrame frame) {
        frame.setSize(getSize());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}