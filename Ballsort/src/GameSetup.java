import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import javax.swing.SwingConstants;
import Difficulty.EasyDifficulty;
import Difficulty.MediumDifficulty;
import Difficulty.HardDifficulty;

public class GameSetup extends JFrame {
    private String difficulty;

    public GameSetup(String difficulty) {
        this.difficulty = difficulty;
        
        // Set up JFrame properties
        setTitle("Ball Sort Puzzle");
        setSize(1280, 720);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new WelcomeScreen();
            }
        });
        setLocationRelativeTo(null);
        
        // Create main panel with background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon("Ballsort/lib/BackgroundAssets/BackgroundHeadquaters.jpeg");
                Image image = backgroundImage.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                
                // Add dark overlay
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(new Color(0, 0, 0, 100));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(null);
        
        // Add game rules label
        JLabel rulesLabel = new JLabel("<html><center>You can only move a ball on top of another ball if both of them have the same color<br>and the tube you want to move into has enough space.</center></html>");
        rulesLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        rulesLabel.setForeground(Color.WHITE);
        rulesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Position the label below the grid
        int labelWidth = 1000;
        int labelX = (getWidth() - labelWidth) / 2;
        int labelY = ((getHeight() + 500) / 2) + 20;
        rulesLabel.setBounds(labelX, labelY, labelWidth, 50);
        
        mainPanel.add(rulesLabel);
        
        // Create tube panel
        JPanel tubePanel = new JPanel();
        tubePanel.setOpaque(false);
        tubePanel.setBackground(new Color(128, 128, 128, 192));
        tubePanel.setLayout(null);
        // TODO: Remove development borders when finished
        tubePanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        
        // Position the tube panel in the center
        int tubePanelX = (getWidth() - 1300) / 2;
        int tubePanelY = (getHeight() - 540) / 2;
        tubePanel.setBounds(tubePanelX, tubePanelY, 1300, 540);
        mainPanel.add(tubePanel);
        
        // Add panel to frame
        add(mainPanel);
        
        // Make the frame visible
        setVisible(true);
        
        // Create difficulty after tubePanel is set up
        if (this.difficulty.equals("easy")) {
            EasyDifficulty easyMode = new EasyDifficulty(tubePanel);
        } else if (this.difficulty.equals("medium")) {
            MediumDifficulty mediumMode = new MediumDifficulty(tubePanel);
        } else if (this.difficulty.equals("hard")) {
            HardDifficulty hardMode = new HardDifficulty(tubePanel);
        }
    }
}