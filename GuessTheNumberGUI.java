import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GuessTheNumberGUI extends JFrame {
  
 private int numberG;
 private int attempts;
 private int score;
 private int totalR;
 private int currentR;

    private JLabel promptLabel, feedbackLabel, scoreLabel, roundLabel;
    private JTextField guessField;
    private JButton guessButton, newRoundButton;

    public GuessTheNumberGUI(int totalRounds) {
        this.totalR = totalRounds;
        this.currentR = 0;
        this.score = 0;

        setTitle("Guess The Number Game");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1));
        roundLabel = new JLabel("Round: 0/" + totalR, JLabel.CENTER);
        promptLabel = new JLabel("Enter your guess (1-100):", JLabel.CENTER);
        feedbackLabel = new JLabel(" ", JLabel.CENTER);
        scoreLabel = new JLabel("Score: 0", JLabel.CENTER);


        guessField = new JTextField();
        guessButton = new JButton("Submit Guess");
        newRoundButton = new JButton("Start New Round");
        add(roundLabel);
        add(promptLabel);
        add(guessField);
        add(guessButton);
        add(feedbackLabel);
        add(scoreLabel);
        
        guessButton.addActionListener(new GuessButtonListener());
        newRoundButton.addActionListener(new NewRoundButtonListener());

        startNewRound(); 
    }

    
    private void startNewRound() {
        Random random = new Random();
        numberG = random.nextInt(100) + 1;
        attempts = 10;
        currentR++;
        roundLabel.setText("Round: " + currentR + "/" + totalR);
        feedbackLabel.setText(" ");
        guessField.setText("");
        guessButton.setEnabled(true);
        promptLabel.setText("Enter your guess (1-100):");
    }

    private void endGame() {
        JOptionPane.showMessageDialog(this, "Game Over! Your total score is better luck next time: " + score);
        System.exit(0);
    }


    private class GuessButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                int playerGuess = Integer.parseInt(guessField.getText());
                
                if (playerGuess < 1 || playerGuess > 100) {
                    feedbackLabel.setText("Guess must be between 1 and 100!");
                    return;
                }

                attempts--;

                if (playerGuess == numberG) {
                    feedbackLabel.setText("Correct! You've guessed the number.");
                    score += attempts+ 1;
                    scoreLabel.setText("Score: " + score);
                    guessButton.setEnabled(false);
                    if (currentR == totalR) {
                        endGame();
                    } else {
                        add(newRoundButton); 
                        validate();
                        repaint();
                    }
                } else if (playerGuess < numberG) {
                    feedbackLabel.setText("Too low! Attempts left: " + attempts);
                } else {
                    feedbackLabel.setText("Too high! Attempts left: " + attempts);
                }

                if (attempts == 0 && playerGuess != numberG) {
                    feedbackLabel.setText("Out of attempts The number was: Better luck next time " + numberG);
                    guessButton.setEnabled(false);
                    if (currentR== totalR) {
                        endGame();
                    } else {
                        add(newRoundButton);
                        validate();
                        repaint();
                    }
                }

            } catch (NumberFormatException ex) {
                feedbackLabel.setText("Please enter a valid number.");
            }
        }
    }

    private class NewRoundButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            remove(newRoundButton);
            validate();
            repaint();
            startNewRound();
        }
    }

    public static void main(String[] args) {
        String inputRounds = JOptionPane.showInputDialog(null, "How many rounds would you like to play?");
        int rounds = Integer.parseInt(inputRounds);

        GuessTheNumberGUI game = new GuessTheNumberGUI(rounds);
        game.setVisible(true);
    }
}
