package textproc;

import javafx.collections.transformation.SortedList;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Border;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.DigestException;
import java.util.*;
import java.util.List;
import java.util.concurrent.Flow;
import java.util.stream.Collectors;

public class BookReaderController {

    private GeneralWordCounter wordCounter;
    private SortedListModel wordList;

    public BookReaderController(GeneralWordCounter wordCounter) {
        this.wordCounter = wordCounter;
        this.wordList = new SortedListModel<>(wordCounter.getWordList());
        SwingUtilities.invokeLater(() -> createWindow(wordCounter, "BookReader", 100, 300));
    }

    private class FilePanel {

        private SortedListModel files;
        private JPanel outerFrame;

        FilePanel() {
            outerFrame = new JPanel(new GridLayout(2, 0));
            JLabel label = new JLabel("Files");

            files = new SortedListModel<>(getFiles());
            JList<SortedListModel> list = new JList(files);
            JScrollPane pane = new JScrollPane(list);
            pane.setPreferredSize(new Dimension(200, 450));
            outerFrame.add(label);
            outerFrame.add(pane);

        }

        private List<File> getFiles() {
            try {
                java.util.List<File> files = Files.walk(Paths.get(""))
                        .filter(Files::isRegularFile)
                        .map(Path::toFile)
                        .collect(Collectors.toList());

                return files;
            } catch (IOException e) {
                return null;
            }
        }

    }

    private List<File> getFiles() {
        try {
            java.util.List<File> files = Files.walk(Paths.get(""))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList());

            return files;
        } catch (IOException e) {
            return null;
        }
    }


    private void createWindow(GeneralWordCounter counter, String title, int width, int height) {
        JFrame root = new JFrame(title);
        JPanel frame = new JPanel(new BorderLayout());

        JPanel listFrame = new JPanel(new BorderLayout());
        JPanel listFrameInner = new JPanel(new GridLayout(0, 2));
        JPanel listLabelFrame = new JPanel(new GridLayout(0, 2));

        // List displays
        JList<SortedListModel<Map.Entry<String, Integer>>> list = new JList<>(wordList);
        JScrollPane scrollPaneList = new JScrollPane(list);
        scrollPaneList.setPreferredSize(new Dimension(500, 500));

        SortedListModel files = new SortedListModel<>(getFiles());
        JPanel outerFrame = new JPanel(new GridLayout(2, 0));
        JList<SortedListModel> fileList = new JList(files);
        JScrollPane scrollPaneFiles = new JScrollPane(fileList);
        scrollPaneFiles.setPreferredSize(new Dimension(300, 500));

        JLabel labelFiles = new JLabel("Files");
        JLabel labelWords = new JLabel("words");
        listLabelFrame.add(labelFiles);
        listLabelFrame.add(labelWords);
        listFrameInner.add(scrollPaneFiles);
        listFrameInner.add(scrollPaneList);

        listFrame.add(listLabelFrame, BorderLayout.NORTH);
        listFrame.add(listFrameInner, BorderLayout.SOUTH);

        // Buttons

        Font font = new Font("Courier", Font.BOLD, 15);

        JPanel buttonPanel = new JPanel(new GridLayout(0,4));
        JTextField textInput = new JTextField();
        JButton btnFind = new JButton("Find");
        JButton btnAlphabetic = new JButton("Alphabetic");
        JButton btnFrequency = new JButton("Frequency");
        buttonPanel.add(textInput);
        buttonPanel.add(btnFind);
        buttonPanel.add(btnAlphabetic);
        buttonPanel.add(btnFrequency);

        for (JPanel panel: new JPanel[] {listLabelFrame, listFrameInner, buttonPanel}) {
            for (Component comp: panel.getComponents()) {
                comp.setFont(font);
            }
        }
        for (JButton button: new JButton[] {btnFind, btnAlphabetic, btnFrequency}) {
            button.setBackground(Color.GRAY);
        }




        // -- CALLBACKS --

        fileList.addListSelectionListener((event) -> {
            // When an item in the filetree is clicked, the
            // file is opened, read and given to the wordCounter.
            Object obj = fileList.getModel().getElementAt(event.getFirstIndex());
            try {
                Scanner scan = new Scanner((File) obj);
                scan.useDelimiter("(\\s|,|\\.|:|;|!|\\?|'|\\\")+");
                wordCounter.clear();
                while (scan.hasNext()) {
                    String word = scan.next().toLowerCase();
                    if (word.matches("^[a-z].*")) {
                        wordCounter.process(word);
                    }
                }
                scan.close();
                wordList.setNewList(wordCounter.getWordList());
            } catch (FileNotFoundException e) {

            }

        });

        Action find = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String input = textInput.getText().toLowerCase();
                if (input.length() == 0) {
                    JOptionPane.showMessageDialog(frame, "You must enter something!");
                    return;
                }
                for (int word = 0; word < wordList.getSize(); word++) {
                    String wordInList = wordList.getElementAt(word).toString().
                            split("=")[0].strip();
                    if (wordInList.equals(input)) {
                        list.clearSelection();
                        list.setSelectedIndex(word);
                        list.ensureIndexIsVisible(word);
                        return;
                    }
                }
                JOptionPane.showMessageDialog(frame, String.format("\"%s\" can't be found in the list", input));
            }
        };

        btnFind.addActionListener(find);
        textInput.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "findAction");
        textInput.getActionMap().put("findAction", find);

        btnAlphabetic.addActionListener((event) -> {
            wordList.sort((e1, e2) -> {
                String val1 = ((Map.Entry<String, Integer>) e1).getKey();
                String val2 = ((Map.Entry<String, Integer>) e2).getKey();
                return val1.compareTo(val2);
            });
        });
        btnFrequency.addActionListener((event) -> {
            wordList.sort((e1, e2) -> {
                int val1 = ((Map.Entry<String, Integer>) e1).getValue();
                int val2 = ((Map.Entry<String, Integer>) e2).getValue();
                if (val1 < val2) {
                    return -1;
                } else if (val1 > val2) {
                    return 1;
                }
                return 0;
            });
        });


        frame.add(listFrame, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        root.add(frame);

        Container pane = root.getContentPane();
        root.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        root.setMinimumSize(new Dimension(300, 300));
        root.setVisible(true);
        root.pack();
    }
}

