import javax.swing.*;
import java.awt.*;

public class WelcomeScreen extends JFrame {

    private JPanel mainPanel;

    public WelcomeScreen() {
        // Set up JFrame properties
        setTitle("Title Screen");
        setSize(1500, 700);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on spawn

        // Set up main panel
        mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon("Ballsort/lib/BackgroundAssets/BackgroundBokeh.jpg");
                Image image = backgroundImage.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        mainPanel.setLayout(null); // Using absolute layout for precise positioning

        // Add logo
        ImageIcon logoIcon = new ImageIcon("Ballsort\\lib\\BackgroundAssets\\Icons\\TitleScreenLogo.png"); // Replace with the path to your logo
        Image logoImage = logoIcon.getImage();
        Image scaledLogoImage = logoImage.getScaledInstance(300, 300, Image.SCALE_SMOOTH); // Adjust size as needed
        ImageIcon scaledLogoIcon = new ImageIcon(scaledLogoImage);
        
        JLabel logoLabel = new JLabel(scaledLogoIcon);
        logoLabel.setBounds(600, 50, 300, 300); // Adjust x, y, width, height as needed
        mainPanel.add(logoLabel);

        // Add "Choose Difficulty" label
        JLabel difficultyLabel = new JLabel("Choose Difficulty");
        difficultyLabel.setFont(new Font("Arial", Font.BOLD, 24));
        difficultyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        difficultyLabel.setBounds(600, 400, 300, 50); // Adjust x, y, width, height as needed
        difficultyLabel.setForeground(Color.WHITE); // Color to stand out against the background
        mainPanel.add(difficultyLabel);

        // Add difficulty buttons
        JButton easyButton = createDifficultyButton("Easy", 440, 460);
        JButton mediumButton = createDifficultyButton("Medium", 650, 460);
        JButton hardButton = createDifficultyButton("Hard", 860, 460);

        // Add action listeners to buttons
        easyButton.addActionListener(e -> System.out.println("Easy selected"));
        mediumButton.addActionListener(e -> System.out.println("Medium selected"));
        hardButton.addActionListener(e -> System.out.println("Hard selected"));

        mainPanel.add(easyButton);
        mainPanel.add(mediumButton);
        mainPanel.add(hardButton);

        // Add panel to the frame
        add(mainPanel);

        // Make the frame visible
        setVisible(true);
    }

    // Helper method to create buttons
    private JButton createDifficultyButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setBounds(x, y, 200, 50); // Adjust width and height as needed
        button.setFocusPainted(false); // Remove the default focus rectangle
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setBackground(new Color(59, 89, 182));
        button.setForeground(Color.WHITE);
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WelcomeScreen());
    }
}
