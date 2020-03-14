import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class QuizCardBuilder {
    private JTextArea question;
    private JTextArea answer;
    private ArrayList<QuizCard> cardList;
    private JFrame frame;

    public static void main(String[] args) {
        QuizCardBuilder builder = new QuizCardBuilder();
        builder.go();
    }

    public void go() {
        // build gui

        // Main frame of app
        frame = new JFrame("Quiz Card Builder");
        // main panel of the app
        JPanel mainPanel = new JPanel();
        // create a font for the app
        Font bigFont = new Font("sanserif", Font.BOLD, 24);
        // create new question obj and add styles to question text
        question = new JTextArea(6, 20);
        question.setLineWrap(true);
        question.setWrapStyleWord(true);
        question.setFont(bigFont);

        // add scroll vertically but not horizontally to question
        JScrollPane qScroller = new JScrollPane(question);
        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        // Create new answer obj and set same styles as question obj
        answer = new JTextArea(6, 20);
        answer.setLineWrap(true);
        answer.setWrapStyleWord(true);
        answer.setFont(bigFont);

        // add scroll vertically but not horizontally to answer
        JScrollPane aScroller = new JScrollPane(answer);
        aScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        aScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        // next card button
        JButton nextButton = new JButton("Next Card");

        // create new obj for array of cards;
        cardList = new ArrayList<QuizCard>();

        // Create a label for question and answer
        JLabel qLabel = new JLabel("Question:");
        JLabel aLabel = new JLabel("Answer:");

        // add label and scrollers to main panel
        mainPanel.add(qLabel);
        mainPanel.add(qScroller);
        mainPanel.add(aLabel);
        mainPanel.add(aScroller);
        mainPanel.add(nextButton);

        // add action listener to the button
        nextButton.addActionListener(new NextCardListener());

        // Create a menu bar and add new, save
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        // Test
        JMenuItem closeMenuItem = new JMenuItem("Close");
        // Menu listener for buttons in the menu bar
        newMenuItem.addActionListener(new NewMenuListener());
        // save button listener
        saveMenuItem.addActionListener(new SaveMenuListener());
        // close button listener test
        closeMenuItem.addActionListener(new CloseMenuListener());

        // add add sub buttons to main menu
        fileMenu.add(newMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(closeMenuItem);
        // add main menu to the main bar
        menuBar.add(fileMenu);

        // set the main frame
        frame.setJMenuBar(menuBar);
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setSize(500, 600);
        frame.setVisible(true);
    }

    private class NextCardListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            QuizCard card = new QuizCard(question.getText(), answer.getText());
            cardList.add(card);
            clearCard();
        }
    }

    private class SaveMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            QuizCard card = new QuizCard(question.getText(), answer.getText());
            cardList.add(card);

            JFileChooser fileSave = new JFileChooser();
            fileSave.showSaveDialog(frame);
            saveFile(fileSave.getSelectedFile());
        }
    }

    private class NewMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            cardList.clear();
            clearCard();
        }
    }

    // Test
    private class CloseMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {

        }
    }

    private void clearCard() {
        question.setText("");
        answer.setText("");
        question.requestFocus();
    }

    private void saveFile(File file) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            for(QuizCard card : cardList) {
                writer.write(card.getQuestion() + "/");
                writer.write(card.getAnswer() + "\n");
            }
            writer.close();
        } catch (IOException ex) {
            System.out.println("Couldn't write the cardList out");
            ex.printStackTrace();
        }
    }
}
