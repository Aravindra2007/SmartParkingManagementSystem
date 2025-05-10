// OpeningPage.java
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class OpeningPage extends JFrame implements ActionListener {
    JButton enterButton;

    public OpeningPage() {
        setTitle("Smart Parking System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        ImageIcon parkingIcon = new ImageIcon("ak2.png");
        this.setIconImage(parkingIcon.getImage());
        JLabel imageLabel = new JLabel(parkingIcon);

        JLabel welcomeLabel = new JLabel("Welcome to Smart Parking!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setForeground(Color.WHITE);
        JPanel cpanel = new JPanel();
        cpanel.setLayout(new BoxLayout(cpanel, BoxLayout.Y_AXIS));
        cpanel.setBackground(new Color(000000));
        cpanel.add(Box.createVerticalGlue());
        cpanel.add(imageLabel);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cpanel.add(Box.createVerticalStrut(10));
        cpanel.add(welcomeLabel);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cpanel.add(Box.createVerticalGlue());
        enterButton = new JButton("Enter");
        enterButton.addActionListener(this);
        enterButton.setFocusable(false);
        enterButton.setFont(new Font("Segoe UI", Font.BOLD, 34));
        enterButton.setBackground(new Color(173, 216, 230));
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getRootPane().getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enterAction");
        actionMap.put("enterAction", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enterButton.doClick(); // Simulate a click on the button
            }
        });

        add(enterButton, BorderLayout.SOUTH);
        add(cpanel, BorderLayout.CENTER);
        setSize(700, 700);
        setLocationRelativeTo(null);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == enterButton) {
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(OpeningPage::new);
    }
}
