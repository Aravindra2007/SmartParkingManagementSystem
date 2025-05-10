// ParkingOptionsPage.java
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

public class ParkingOptionsPage extends JFrame implements ActionListener,MouseListener{
    JButton parkInButton, parkOutButton, statusButton, historyButton, backButton;
    JPanel panel1;
    public ParkingOptionsPage() {
        setTitle("Parking Options");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setLayout(new GridLayout(5, 1, 10, 10));
        
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        ImageIcon parkingIcon = new ImageIcon("Parking.png"); 
        this.setIconImage(parkingIcon.getImage());
        
        parkInButton = new JButton("Park In");
        parkOutButton = new JButton("Park Out");
        statusButton = new JButton("Status");
        historyButton = new JButton("History");
        backButton = new JButton("Back");
        parkInButton.setFocusable(false);
        parkInButton.setForeground(Color.BLACK);
        parkInButton.setBackground(new Color(173,216,230));
        parkInButton.setFont(new Font("Segoe UI",Font.BOLD,20));
        parkInButton.addActionListener(this);
        parkOutButton.setFocusable(false);
        parkOutButton.setForeground(Color.BLACK);
        parkOutButton.setBackground(new Color(173,216,230));
        parkOutButton.setFont(new Font("Segoe UI",Font.BOLD,20));
        statusButton.setFocusable(false);
        statusButton.setForeground(Color.BLACK);
        statusButton.setBackground(new Color(173,216,230));
        statusButton.setFont(new Font("Segoe UI",Font.BOLD,20));
        historyButton.setFocusable(false);
        historyButton.setForeground(Color.BLACK);
        historyButton.setBackground(new Color(173,216,230));
        historyButton.setFont(new Font("Segoe UI",Font.BOLD,20));
        backButton.setFocusable(false);
        backButton.setForeground(Color.BLACK);
        backButton.setBackground(new Color(173,216,230));
        backButton.setFont(new Font("Segoe UI",Font.BOLD,20));
        parkOutButton.addActionListener(this);
        statusButton.addActionListener(this);
        historyButton.addActionListener(this);
        backButton.addActionListener(e -> {
            OpeningPage openingPage = new OpeningPage();
            updateFrameProperties(openingPage);
            dispose();
        });


        JPanel panel=new JPanel(new FlowLayout(FlowLayout.CENTER,0,50));
        panel.setBackground(new Color(000000));
        panel1=new JPanel();
        panel1.setBackground(new Color(0000000));
        panel1.setLayout((new GridLayout(0,1,0,30)));
        panel1.setPreferredSize(new Dimension(400,400));
        panel1.add(parkInButton);
        panel1.add(parkOutButton);
        panel1.add(statusButton);
        panel1.add(historyButton);
        panel1.add(backButton);
        panel.add(panel1);
        this.add(panel);
        parkInButton.addMouseListener(this);
        parkOutButton.addMouseListener(this);
        setSize(300, 250);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == parkInButton) {
            ParkInPage parkInPage = new ParkInPage();
            updateFrameProperties(parkInPage);
            dispose();
        } 
        else if (e.getSource() == parkOutButton) {
            ParkOutPage parkOutPage = new ParkOutPage();
            updateFrameProperties(parkOutPage);
            dispose();
        }
         else if (e.getSource() == statusButton) {
            StatusPage statusPage = new StatusPage();
            updateFrameProperties(statusPage);
            dispose();
        }
         else if (e.getSource() == historyButton) {
            HistoryPage historyPage = new HistoryPage();
            updateFrameProperties(historyPage);
            dispose();
        }
        // Back button handled in its ActionListener
    }

    private void updateFrameProperties(JFrame frame) {
        frame.setSize(getSize());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public void mouseEntered(MouseEvent e){
        //parkInButton.setText("Park Out");
        //parkOutButton.setText("Park In");
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        //parkInButton.setText("Park In");
        //parkOutButton.setText("Park Out");
        
    }
}