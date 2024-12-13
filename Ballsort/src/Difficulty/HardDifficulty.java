package Difficulty;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class HardDifficulty {
    private JPanel[] tubes; // Array to hold the tube panels
    private int[] tube1, tube2, tube3, tube4, tube5, tube6, tube7, tube8, tube9, tube10, tube11, tube12; // Individual tubes

    private int heldBall; // Currently held ball value
    private int[] ballDispenser; // Array to hold the balls available for distribution
    private JPanel tubePanel; // Panel that contains all the tubes
    private int tubePopped; // Index of the tube from which a ball was popped
    private int ghostTubeIndex; // Index of the tube displaying the ghost ball
    private int ghostCellIndex; // Index of the cell displaying the ghost ball

    // Constructor to initialize the HardDifficulty game
    public HardDifficulty(JPanel tubePanel) {
        this.tubePanel = tubePanel; // Assign the provided tube panel to the instance variable
        tubes = new JPanel[12]; // Initialize the array for 12 tubes
        tube1 = new int[5]; // Each tube can hold 5 balls
        tube2 = new int[5];
        tube3 = new int[5];
        tube4 = new int[5];
        tube5 = new int[5];
        tube6 = new int[5];
        tube7 = new int[5];
        tube8 = new int[5];
        tube9 = new int[5];
        tube10 = new int[5];
        tube11 = new int[5];
        tube12 = new int[5];
        heldBall = -1; // No ball is held initially
        ballDispenser = new int[45]; // Array to hold 5 of each integer from 1 to 9

        // Fill the ballDispenser with 5 of each integer from 1 to 9
        int index = 0;
        for (int i = 1; i <= 9; i++) {
            for (int j = 0; j < 5; j++) { // Generate 5 of each integer
                ballDispenser[index++] = i; // Add 5 of each integer to the dispenser
            }
        }

        graphics(); // Set up the UI components
        gameStart(); // Shuffle and distribute the balls into the tubes
        displayBalls(); // Display the images of the balls in the tubes
        gameLogic(); // Placeholder for game logic
    }

    // Method to set up the graphics for the tubes and buttons
    public void graphics() {
        // Calculate starting X position to center 12 tubes
        int tubeWidth = 100; // Width for each tube
        int gapWidth = 5; // Gap between tubes
        int totalWidth = 12 * tubeWidth + 11 * gapWidth; // Total width for all tubes and gaps
        int startX = (tubePanel.getWidth() - totalWidth) / 2; // Centering the tubes

        // Create 12 tubes and their corresponding buttons
        for (int i = 0; i < 12; i++) {
            // Create tube panel
            tubes[i] = new JPanel();
            tubes[i].setOpaque(false); // Make the tube panel transparent
            tubes[i].setPreferredSize(new Dimension(tubeWidth, 500)); // Set preferred size for the tube
            tubes[i].setLayout(new GridLayout(5, 1, 3, 3)); // Layout for 5 cells in each tube

            // Set a border for the tube panel
            tubes[i].setBorder(BorderFactory.createMatteBorder(0, 5, 5, 5, Color.WHITE)); // No border on top, 10 pixels on other sides

            // Create 5 cells for each tube
            for (int j = 0; j < 5; j++) {
                JPanel cell = new JPanel();
                cell.setPreferredSize(new Dimension(100, 100)); // Size for each cell
                cell.setOpaque(false); // Set opaque to false for transparency
                cell.setBackground(new Color(0, 0, 0, 0)); // Fully transparent background
                cell.setBorder(null); // Remove border from the cell
                tubes[i].add(cell); // Add the cell to the tube
            }

            // Set bounds for the tube panel
            tubes[i].setBounds(startX + i * (tubeWidth + gapWidth), 10, tubeWidth, 500); // Positioning the tubes
            tubePanel.add(tubes[i]); // Add the tube panel to the main tube panel

            // Create a transparent button for user interaction
            JButton button = new JButton();
            button.setOpaque(false); // Make the button transparent
            button.setContentAreaFilled(false); // Remove the button's background
            button.setBorderPainted(false); // Remove the button's border
            button.setPreferredSize(new Dimension(tubeWidth, 500)); // Set the same size as the tube panel
            button.setBounds(startX + i * (tubeWidth + gapWidth), 10, tubeWidth, 500); // Positioning the button

            // Add action listener to the button
            final int tubeIndex = i + 1; // Store the tube index (1-based)
            button.addActionListener(e -> buttonActions(tubeIndex)); // Call buttonActions with the tube index

            // Add the button to the tubePanel
            tubePanel.add(button);
        }
    }

    // Method to handle button actions for tube interaction
    public void buttonActions(int selectedTubeIndex) {
        int[] selectedTube = getTube(selectedTubeIndex); // Get the selected tube array

        if (heldBall == -1) { // Case: No ball is currently held
            if (isEmpty(selectedTube)) {
                System.out.println("The selected tube is empty. Nothing to pop.");
            } else {
                heldBall = pop(selectedTube); // Pop a ball from the selected tube
                tubePopped = selectedTubeIndex; // Store the tube from which the ball was popped
                System.out.println("Ball held: " + heldBall);
            }
        } else { // Case: A ball is currently held
            if (isFull(selectedTube)) {
                System.out.println("The selected tube is full. Returning ball to tube " + tubePopped);
                push(getTube(tubePopped), heldBall); // Return ball to original tube
                heldBall = -1; // Clear held ball
            } else if (isEmpty(selectedTube)) {
                push(selectedTube, heldBall); // Push the held ball into the selected tube
                heldBall = -1; // Clear held ball
            } else {
                int topBall = peek(selectedTube); // Peek at the top ball in the selected tube
                if (topBall != heldBall) {
                    System.out.println("Top ball (" + topBall + ") does not match held ball (" + heldBall + "). Returning ball to tube " + tubePopped);
                    push(getTube(tubePopped), heldBall); // Return ball to original tube
                    heldBall = -1; // Clear held ball
                } else {
                    push(selectedTube, heldBall); // Push the held ball into the selected tube
                    heldBall = -1; // Clear held ball
                }
            }
        }

        displayBalls(); // Update the GUI to reflect changes
        printTubes(); // Print the current state of the tubes to the terminal
        System.out.println("Held number: " + (heldBall == -1 ? "none" : heldBall));
    }

    // Push method: Adds a value to the bottom-most empty slot in the tube
    public void push(int[] tube, int value) {
        for (int i = tube.length - 1; i >= 0; i--) { // Start from the bottom of the tube
            if (tube[i] == 0) { // Find the first empty slot from the bottom
                tube[i] = value; // Place the value in the empty slot
                return; // Exit the method after placing the value
            }
        }
        throw new IllegalStateException("Tube is full! Cannot push more balls."); // Throw an exception if the tube is full
    }

    // Pop method: Removes and returns the top ball from the tube
    public int pop(int[] tube) {
        for (int i = 0; i < tube.length; i++) {
            if (tube[i] != 0) { // Check for a non-empty slot
                int value = tube[i]; // Store the value of the ball
                tube[i] = 0; // Clear the slot
                return value; // Return the value of the popped ball
            }
        }
        return -1; // Return -1 if the tube is empty
    }

    // Peek method: Returns the top ball without removing it
    public int peek(int[] tube) {
        for (int i = 0; i < tube.length; i++) {
            if (tube[i] != 0) { // Check for a non-empty slot
                return tube[i]; // Return the value of the top ball
            }
        }
        return -1; // Return -1 if the tube is empty
    }

    // Check if a tube is full
    public boolean isFull(int[] tube) {
        for (int ball : tube) {
            if (ball == 0) { // If any slot is empty
                return false; // The tube is not full
            }
        }
        return true; // The tube is full
    }

    // Check if a tube is empty
    public boolean isEmpty(int[] tube) {
        for (int ball : tube) {
            if (ball != 0) { // If any slot is filled
                return false; // The tube is not empty
            }
        }
        return true; // The tube is empty
    }

    // Utility method to get a tube array by its index
    public int[] getTube(int index) {
        return switch (index) {
            case 1 -> tube1;
            case 2 -> tube2;
            case 3 -> tube3;
            case 4 -> tube4;
            case 5 -> tube5;
            case 6 -> tube6;
            case 7 -> tube7;
            case 8 -> tube8;
            case 9 -> tube9;
            case 10 -> tube10;
            case 11 -> tube11; // New tube 11
            case 12 -> tube12; // New tube 12
            default -> null; // Return null for invalid index
        };
    }

    // Prints the current state of all tubes
    public void printTubes() {
        for (int i = 0; i < 12; i++) { // Loop through all 12 tubes
            int[] tube = getTube(i + 1); // Get the tube array
            System.out.print("Tube " + (i + 1) + ": "); // Print tube number
            for (int ball : tube) {
                System.out.print("[" + (ball == 0 ? "-" : ball) + "] "); // Print ball values or "-" for empty slots
            }
            System.out.println(); // New line after each tube
        }
    }

    // Method to display the balls in the tubes
    public void displayBalls() {
        // Loop through each tube and update the cells with images
        for (int i = 0; i < 12; i++) { // Loop through all 12 tubes
            int[] currentTube = switch (i) {
                case 0 -> tube1;
                case 1 -> tube2;
                case 2 -> tube3;
                case 3 -> tube4;
                case 4 -> tube5;
                case 5 -> tube6;
                case 6 -> tube7;
                case 7 -> tube8;
                case 8 -> tube9;
                case 9 -> tube10;
                case 10 -> tube11; // New tube 11
                case 11 -> tube12; // New tube 12
                default -> new int[0]; // Fallback
            };

            for (int j = 0; j < 5; j++) { // Loop through each cell in the tube
                int value = currentTube[j]; // Get the value of the ball in the cell
                JPanel cell = (JPanel) tubes[i].getComponent(j); // Get the corresponding cell panel

                // Load the image based on the value
                if (value > 0) {
                    String imagePath = "Ballsort/lib/BackgroundAssets/Bubbles/" + value + ".png"; // Path to the ball image
                    ImageIcon icon = new ImageIcon(imagePath); // Create an ImageIcon from the image path
                    JLabel label = new JLabel(icon); // Create a JLabel to hold the image
                    cell.removeAll(); // Clear previous components
                    cell.add(label); // Add the image label to the cell
                } else {
                    cell.removeAll(); // Clear previous components
                    cell.setBackground(new Color(200, 200, 200)); // Reset to gray if empty
                }
                cell.revalidate(); // Refresh the cell
                cell.repaint(); // Repaint the cell
            }
        }
    }

    // Method to start the game and distribute balls into tubes
    public void gameStart() {
        // Shuffle the ballDispenser array
        Integer[] ballDispenserList = Arrays.stream(ballDispenser).boxed().toArray(Integer[]::new); // Convert to Integer array
        List<Integer> ballList = new ArrayList<>(Arrays.asList(ballDispenserList)); // Create a list from the array

        Collections.shuffle(ballList); // Shuffle the list

        // Print the shuffled ballDispenser
        System.out.println("Shuffled ballDispenser: " + ballList);

        // Distribute balls into tubes
        for (int tubeIndex = 0; tubeIndex < 12; tubeIndex++) {
            for (int j = 0; j < 5; j++) {
                if (!ballList.isEmpty()) {
                    // Pop a value, store it as heldBall, and push it into the corresponding tube
                    heldBall = ballList.remove(0); // Get the next ball
                    switch (tubeIndex) {
                        case 0: tube1[j] = heldBall; break;
                        case 1: tube2[j] = heldBall; break;
                        case 2: tube3[j] = heldBall; break;
                        case 3: tube4[j] = heldBall; break;
                        case 4: tube5[j] = heldBall; break;
                        case 5: tube6[j] = heldBall; break;
                        case 6: tube7[j] = heldBall; break;
                        case 7: tube8[j] = heldBall; break;
                        case 8: tube9[j] = heldBall; break;
                        case 9: tube10[j] = heldBall; break;
                        case 10: tube11[j] = heldBall; break;
                        case 11: tube12[j] = heldBall; break;
                    }
                    heldBall = -1; // Clear the heldBall value
                }
            }
        }

        // Print the values of the tubes
        printTubes();
    }

    // Placeholder for game logic
    public void gameLogic() {
        System.out.println("HardDifficulty game logic executed.");
    }
}