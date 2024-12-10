package Difficulty;

import javax.swing.*;
import java.awt.*;

public class MediumDifficulty {
    private JPanel[] tubes;
    
    public MediumDifficulty(JPanel tubePanel) {
        tubes = new JPanel[6];
        
        // Calculate starting X position to center 6 tubes
        int totalWidth = 6 * 105 + 5 * 10; // 6 tubes and 5 gaps
        int startX = (tubePanel.getWidth() - totalWidth) / 2;
        
        // Create 6 tubes
        for (int i = 0; i < 6; i++) {
            tubes[i] = new JPanel();
            tubes[i].setOpaque(false);
            tubes[i].setPreferredSize(new Dimension(105, 500));
            tubes[i].setLayout(new GridLayout(5, 1, 3, 3));
            
            // Create 5 cells (100x100 each)
            for (int j = 0; j < 5; j++) {
                JPanel cell = new JPanel();
                cell.setPreferredSize(new Dimension(100, 100));
                cell.setOpaque(false);
                cell.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
                tubes[i].add(cell);
            }
            
            // Position in columns 3-8 (index 2-7)
            tubes[i].setBounds(startX + i * (105 + 10), 10, 105, 500);
            tubePanel.add(tubes[i]);
        }
    }
    
    public JPanel[] getTubes() {
        return tubes;
    }
}
