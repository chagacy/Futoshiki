package View;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import progcw.FutoshikiGrid;

/**
 *
 * @author chayagacy
 */
public class FutoshikiGUI extends JFrame {

    public static void main(String[] args) {
        FutoshikiGUI puzzleWindow = new FutoshikiGUI();
        puzzleWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JFrame frame;
    int size;
    String filename = "Puzzle.ser";
    FutoshikiGrid puzzle;

    /**
     * Create an FutoshikiGUI and show it on screen.
     */
    public FutoshikiGUI() {
        super("Futoshiki");
        startup();
    }

    private void startup() {
        JFrame start = new JFrame();
        JPanel scp = (JPanel) start.getContentPane();
        start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        start.setResizable(false);
        //maybe set in center of screen
        start.setPreferredSize(new Dimension(600, 500));
        start.setLayout(new BorderLayout());
        JTextArea instructions = new JTextArea();
        instructions.setText("                                   INSTRUCTIONS\n\n"
                + "To solve the futoshiki puzzle you must have one of\n"
                + "every number between 1 and the size you pick. You must \n"
                + "also obey the constraints between squares for example if you\n"
                + "have a '>' this means the number on the left must be bigger\n"
                + "than the number on the right. Once you think you have\n"
                + "completed the puzzle you should press the 'Check' button\n"
                + "if you are correct a window will popup telling you it is\n"
                + "complete. If it is not correct the problems with the puzzle\n"
                + "will display in the bottom right corner. If you can't solve\n"
                + "the puzzle then pressing 'solve' will do it for you.\n");
        scp.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
        scp.setBackground(new Color(175, 238, 238));
        instructions.setBorder(new EmptyBorder(60, 30, 50, 50));
        instructions.setEditable(false);
        instructions.setBackground(new Color(175, 238, 238));
        instructions.setFont(new Font("ARIAL", Font.PLAIN, 20));

        start.add(instructions, BorderLayout.CENTER);

        JPanel size = new JPanel();
        size.setLayout(new BorderLayout());
        size.setPreferredSize(new Dimension(0, 75));

        JPanel question = new JPanel();
        question.setLayout(new GridLayout(2, 1));

        JLabel sizeQuestion = new JLabel();
        sizeQuestion.setText("What size would you like the grid?");
        sizeQuestion.setHorizontalAlignment(JLabel.CENTER);
        sizeQuestion.setFont(new Font("ARIAL", Font.PLAIN, 20));
        sizeQuestion.setBorder(new EmptyBorder(20, 20, 30, 20));
        sizeQuestion.setOpaque(true);
        sizeQuestion.setBackground(new Color(175, 238, 238));
        question.add(sizeQuestion);

        JLabel sizeIns = new JLabel();
        sizeIns.setText("please press enter once you have entered the size");
        sizeIns.setHorizontalAlignment(JLabel.CENTER);
        sizeIns.setFont(new Font("ARIAL", Font.PLAIN, 12));
        sizeIns.setPreferredSize(new Dimension(100, 0));
        sizeIns.setBorder(new EmptyBorder(20, 20, 30, 20));
        sizeIns.setOpaque(true);
        sizeIns.setBackground(new Color(175, 238, 238));
        sizeIns.setForeground(Color.gray);
        question.add(sizeIns);

        JTextField entry = new JTextField();
        entry.setHorizontalAlignment(JLabel.CENTER);
        entry.setFont(new Font("ARIAL", Font.PLAIN, 15));
        entry.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int input = Integer.parseInt(entry.getText());
                    if (input <= 0) {
                        JOptionPane.showMessageDialog(frame, "Please enter a number greater than 0");
                    }
                    puzzle = new FutoshikiGrid(input);
                    fill();
                    start.dispose();
                    createWindow();
                } catch (NumberFormatException ee) {
                    JOptionPane.showMessageDialog(frame, "Only numbers can be entered");
                }
            }
        });

        size.add(question, BorderLayout.CENTER);
        size.add(entry, BorderLayout.SOUTH);
        start.add(size, BorderLayout.SOUTH);

        start.pack();
        start.setVisible(true);
    }

    private void setup() {
        try {
            int input = Integer.parseInt(JOptionPane.showInputDialog("What size would you like the grid?\n"
                    + "(Numbers between 1 and 9 are recommended)"));
            if (input <= 0) {
                JOptionPane.showMessageDialog(frame, "Please enter a number greater than 0");
                setup();
            }
            size = input;
            puzzle = new FutoshikiGrid(size);
            fill();
            createWindow();
        } catch (NumberFormatException ee) {
            JOptionPane.showMessageDialog(frame, "Only numbers can be entered");
            setup();
        }
    }

    private void fill() {
        puzzle.fillPuzzle();
        while (!puzzle.isLegal() || !puzzle.solvable()) {
            puzzle.fillPuzzle();
        }
    }

    private void createWindow() {

        JPanel grid = new JPanel();

        JPanel cp = (JPanel) getContentPane();
        cp.removeAll();
        cp.setLayout(new BorderLayout());
        JPanel east = new JPanel();
        east.setLayout(new GridLayout(2, 1));
        east.setBackground(new Color(175, 238, 238));
        east.setBorder(new EmptyBorder(20, 20, 20, 20));
        cp.add(east, BorderLayout.EAST);
        cp.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));

//Menu
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("File");

        JMenuItem menuNew = new JMenuItem("New");
        menuNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setup();
            }
        });

        JMenuItem menuSave = new JMenuItem("Save");
        menuSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // save the object to file
                FileOutputStream fos = null;
                ObjectOutputStream out = null;
                try {
                    fos = new FileOutputStream(filename);
                    out = new ObjectOutputStream(fos);
                    out.writeObject(puzzle);

                    out.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        JMenuItem menuOpen = new JMenuItem("Open");
        menuOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileInputStream f = null;
                ObjectInputStream in = null;
                try {
                    f = new FileInputStream(filename);
                    in = new ObjectInputStream(f);
                    puzzle = (FutoshikiGrid) in.readObject();
                    in.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                createWindow();
            }
        });

        menuFile.add(menuNew);
        menuFile.add(menuSave);
        menuFile.add(menuOpen);

        menuBar.add(menuFile);

        cp.add(menuBar);
        setJMenuBar(menuBar);

//Display 
        JTextArea results = new JTextArea();
        results.setLineWrap(true);
        results.setPreferredSize(new Dimension(300, 100));
        results.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
        results.setBackground(Color.white);
        results.setOpaque(true);
        results.setText("Results will show here...");
        results.setForeground(Color.gray);
        results.setEditable(false);

//Buttons
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JButton instructions = new JButton("Instructions");
        instructions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame,
                        "                            INSTRUCTIONS\n\n"
                        + "To solve the futoshiki puzzle you must have one of\n"
                        + "every number between 1 and the row length. You must also\n"
                        + "obey the onstraints between squares for example if you\n"
                        + "have a '>' this means the number on the left must be bigger\n"
                        + "than the number on the right. Once you think you have\n"
                        + "completed the puzzle you should press the 'Check' button\n"
                        + "if you are correct a window will popup telling you it is\n"
                        + "complete. If it is not correct the problems with the puzzle\n"
                        + "will display in the bottom right corner. If you can't solve\n"
                        + "the puzzle then pressing 'solve' will do it for you.\n");
            }
        });

        JButton solve = new JButton("Solve");
        solve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (puzzle.solvable()) {
                    puzzle.solution();
                    createWindow();
                    JOptionPane.showMessageDialog(frame, "The puzzle has been solved");
                } else {
                    JOptionPane.showMessageDialog(frame, "The puzzle can't be solved, press 'check' and change accordingly");
                }
            }
        });

        JButton check = new JButton("Check");

        check.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {
                if (!puzzle.isLegal()) {
                    results.setForeground(Color.black);
                    results.setText(puzzle.getProblems());
                } else if (puzzle.isFull() && puzzle.isLegal()) {
                    results.setForeground(Color.black);
                    results.setText("");
                    JOptionPane.showMessageDialog(frame, "Complete!");
                } else {
                    results.setText("Results will show here...");
                    results.setForeground(Color.gray);
                }
            }
        }
        );

        JButton newPuzzle = new JButton("Generate");

        newPuzzle.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                fill();
                createWindow();
            }
        }
        );

        c.gridx = 0;
        c.gridy = 0;
        buttons.add(instructions, c);
        c.gridx = 0;
        c.gridy = 1;
        buttons.add(solve, c);
        c.gridx = 0;
        c.gridy = 2;
        buttons.add(check, c);
        c.gridx = 0;
        c.gridy = 3;
        buttons.add(newPuzzle, c);
        buttons.setBackground(new Color(175, 238, 238));
        east.add(buttons);
        east.add(new JScrollPane(results));

//Grid
        grid = new JPanel();
        grid.setBorder(new EmptyBorder(10, 10, 10, 10));
        grid.setBackground(new Color(175, 238, 238));

        grid.setLayout(new GridLayout((2 * (puzzle.getGridSize()) - 1), 2 * (puzzle.getGridSize()) - 1));
        for (int i = 0; i < (puzzle.getGridSize()); i++) {
            for (int j = 0; j < (puzzle.getGridSize()); j++) {
                int i1 = i;
                int j1 = j;
//Number 
                JPanel jP = new JPanel();
                jP.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
                jP.setBackground(Color.white);
                jP.setPreferredSize(new Dimension(300 / puzzle.getGridSize(), 300 / puzzle.getGridSize()));
                if (puzzle.getSquare(i, j).getInitial() == true) {
                    JLabel num = new JLabel();
                    num.setFont(new Font("Arial", Font.BOLD, 200 / (puzzle.getGridSize())));
                    num.setBackground(Color.white);
                    num.setForeground(Color.red);
                    num.setText(puzzle.getSquare(i, j).getNumber() + "");
                    jP.add(num);
                } else {
                    JTextField num = new JTextField(1);
                    num.setFont(new Font("Arial", Font.BOLD, 200 / (puzzle.getGridSize())));
                    num.setHorizontalAlignment(JTextField.CENTER);
                    num.setBorder(javax.swing.BorderFactory.createEmptyBorder());

                    if (puzzle.getSquare(i, j).getNumber() != 0) {
                        num.setText(puzzle.getSquare(i, j).getNumber() + "");
                    }
                    num.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyReleased(KeyEvent e) {
                            try {
                                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                                    num.setText("0");
                                    createWindow();
                                } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                                    num.setText("0");
                                    createWindow();
                                }
                                int current = Integer.parseInt(num.getText());
                                puzzle.setSquare(i1, j1, current);
                                if (num.getText().equals("0")) {
                                    createWindow();
                                }
                            } catch (NumberFormatException ee) {
                                JOptionPane.showMessageDialog(frame, "You can only enter numbers");
                                num.setText("0");
                                createWindow();
                            } catch (IllegalArgumentException ee) {
                                JOptionPane.showMessageDialog(frame, "You are not in the range of the grid");
                                num.setText("0");
                                createWindow();
                            }
                        }

                    });
                    jP.add(num);
                }
                grid.add(jP);
//Row Constraints
                if (j < (puzzle.getGridSize()) - 1) {
                    JPanel jP1 = new JPanel();
                    jP1.setBackground(new Color(175, 238, 238));
                    JLabel rCon = new JLabel();
                    rCon.setFont(new Font("Arial", Font.BOLD, 200 / (puzzle.getGridSize())));
                    rCon.setText(puzzle.getRowConstraint(i, j).constraint() + "");
                    rCon.setBackground(new Color(175, 238, 238));
                    jP1.add(rCon);
                    grid.add(jP1);
                }
            }
//Column Constraints

            if (i < (puzzle.getGridSize()) - 1) {
                for (int k = 0; k < (puzzle.getGridSize()); k++) {
                    JPanel jP2 = new JPanel();
                    jP2.setBackground(new Color(175, 238, 238));
                    JLabel cCon = new JLabel();
                    cCon.setFont(new Font("Arial", Font.BOLD, 200 / (puzzle.getGridSize())));
                    cCon.setBackground(new Color(175, 238, 238));
                    cCon.setText(puzzle.getColConstraint(i, k).constraint() + "");
                    jP2.add(cCon);
                    grid.add(jP2);
                    if (k < (puzzle.getGridSize()) - 1) {
                        JPanel empty = new JPanel();
                        empty.setBackground(new Color(175, 238, 238));
                        grid.add(empty);
                    }
                }
            }
        }

        cp.add(grid, BorderLayout.CENTER);

        pack();
        setVisible(true);
    }

}
