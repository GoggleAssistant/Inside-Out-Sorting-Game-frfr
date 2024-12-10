package Difficulty;

import javax.swing.*;
import java.awt.*;

public class EasyDifficulty {
    private JPanel[] tubes;
    
    public EasyDifficulty(JPanel tubePanel) {
        tubes = new JPanel[4];
        
        // Calculate starting X position to center 4 tubes
        int totalWidth = 4 * 105 + 3 * 10; // 4 tubes and 3 gaps
        int startX = (tubePanel.getWidth() - totalWidth) / 2;
        
        // Create 4 tubes
        for (int i = 0; i < 4; i++) {
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
            
            // Position in columns 4,5,6,7 (index 3-6)
            tubes[i].setBounds(startX + i * (105 + 10), 10, 105, 500);
            tubePanel.add(tubes[i]);
        }
    }
}