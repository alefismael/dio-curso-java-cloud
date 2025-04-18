/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sudoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.text.*;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class SudokuGUI extends JFrame {
    private static final int SIZE = 9;
    private JTextField[][] cells = new JTextField[SIZE][SIZE];
    private boolean[][] isFixed = new boolean[SIZE][SIZE];
    private JButton btnIniciar, btnCancelar;
    private JLabel timerLabel;
    private Timer gameTimer;
    private int elapsedSeconds = 0;

    public SudokuGUI() {
        setTitle("Sudoku Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 650);
        setLayout(new BorderLayout());

        // Painel do tabuleiro
        JPanel gridPanel = new JPanel(new GridLayout(SIZE, SIZE));
        Font cellFont = new Font("SansSerif", Font.BOLD, 20);

        // Filtro para apenas dígitos de 1 a 9 e tamanho máximo 1
        DocumentFilter digitFilter = new DigitFilter();

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                JTextField cell = new JTextField();
                cell.setHorizontalAlignment(JTextField.CENTER);
                cell.setFont(cellFont);
                ((AbstractDocument) cell.getDocument()).setDocumentFilter(digitFilter);
                final int rr = row, cc = col;
                cell.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyReleased(KeyEvent e) {
                        validateCell(rr, cc);
                        SwingUtilities.invokeLater(() -> checkWin());
                    }
                });
                int top = (row % 3 == 0) ? 2 : 1;
                int left = (col % 3 == 0) ? 2 : 1;
                int bottom = (row == SIZE - 1) ? 2 : 1;
                int right = (col == SIZE - 1) ? 2 : 1;
                cell.setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK));
                cell.setBackground(Color.WHITE);
                cells[row][col] = cell;
                gridPanel.add(cell);
            }
        }
        add(gridPanel, BorderLayout.CENTER);

        // Painel de controle (botões e cronômetro)
        JPanel controlPanel = new JPanel();
        btnIniciar = new JButton("Iniciar");
        btnCancelar = new JButton("Cancelar");
        timerLabel = new JLabel("Tempo: 00:00");
        controlPanel.add(btnIniciar);
        controlPanel.add(btnCancelar);
        controlPanel.add(Box.createHorizontalStrut(20));
        controlPanel.add(timerLabel);
        add(controlPanel, BorderLayout.SOUTH);

        // Ações dos botões
        btnIniciar.addActionListener(e -> startGame());
        btnCancelar.addActionListener(e -> cancelGame());

        // Timer do jogo (atualiza a cada segundo)
        gameTimer = new Timer(1000, e -> updateTimer());

        setVisible(true);
    }

    private void startGame() {
        cancelGame();
        int[][] puzzle = generatePuzzle(12);
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (puzzle[r][c] != 0) {
                    cells[r][c].setText(String.valueOf(puzzle[r][c]));
                    cells[r][c].setEditable(false);
                    cells[r][c].setBackground(Color.LIGHT_GRAY);
                    isFixed[r][c] = true;
                } else {
                    cells[r][c].setText("");
                    cells[r][c].setEditable(true);
                    cells[r][c].setBackground(Color.WHITE);
                    isFixed[r][c] = false;
                }
            }
        }
        elapsedSeconds = 0;
        timerLabel.setText("Tempo: 00:00");
        gameTimer.start();
    }

    private void cancelGame() {
        gameTimer.stop();
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                cells[r][c].setText("");
                cells[r][c].setEditable(true);
                cells[r][c].setBackground(Color.WHITE);
                isFixed[r][c] = false;
            }
        }
    }

    private void updateTimer() {
        elapsedSeconds++;
        int minutes = elapsedSeconds / 60;
        int seconds = elapsedSeconds % 60;
        timerLabel.setText(String.format("Tempo: %02d:%02d", minutes, seconds));
    }

    private void validateCell(int r, int c) {
        JTextField cell = cells[r][c];
        String text = cell.getText();
        if (text.isEmpty() || isFixed[r][c]) {
            cell.setBackground(isFixed[r][c] ? Color.LIGHT_GRAY : Color.WHITE);
            return;
        }
        boolean valid = true;
        // Checa linha
        for (int col = 0; col < SIZE; col++) {
            if (col != c && text.equals(cells[r][col].getText())) {
                valid = false;
                break;
            }
        }
        // Checa coluna
        if (valid) {
            for (int row = 0; row < SIZE; row++) {
                if (row != r && text.equals(cells[row][c].getText())) {
                    valid = false;
                    break;
                }
            }
        }
        // Checa bloco 3x3
        if (valid) {
            int br = (r / 3) * 3;
            int bc = (c / 3) * 3;
            for (int i = br; i < br + 3; i++) {
                for (int j = bc; j < bc + 3; j++) {
                    if ((i != r || j != c) && text.equals(cells[i][j].getText())) {
                        valid = false;
                        break;
                    }
                }
                if (!valid) break;
            }
        }
        // Define cor de fundo: vermelho se errado, azul claro se certo
        cell.setBackground(valid ? Color.CYAN : Color.RED);
    }

    private void checkWin() {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (cells[r][c].getText().isEmpty()) return;
            }
        }
        if (isValidSolution()) {
            gameTimer.stop();
            JOptionPane.showMessageDialog(this, "Parabéns! Você venceu em " + timerLabel.getText().substring(7));
        }
    }

    private boolean isValidSolution() {
        for (int r = 0; r < SIZE; r++) if (!checkUnique(getRow(r))) return false;
        for (int c = 0; c < SIZE; c++) if (!checkUnique(getCol(c))) return false;
        for (int br = 0; br < 3; br++) {
            for (int bc = 0; bc < 3; bc++) {
                if (!checkUnique(getBlock(br, bc))) return false;
            }
        }
        return true;
    }

    private int[] getRow(int r) {
        int[] vals = new int[SIZE];
        for (int c = 0; c < SIZE; c++) vals[c] = Integer.parseInt(cells[r][c].getText());
        return vals;
    }

    private int[] getCol(int c) {
        int[] vals = new int[SIZE];
        for (int r = 0; r < SIZE; r++) vals[r] = Integer.parseInt(cells[r][c].getText());
        return vals;
    }

    private int[] getBlock(int br, int bc) {
        int[] vals = new int[SIZE];
        int idx = 0;
        for (int r = br * 3; r < br * 3 + 3; r++) {
            for (int c = bc * 3; c < bc * 3 + 3; c++) {
                vals[idx++] = Integer.parseInt(cells[r][c].getText());
            }
        }
        return vals;
    }

    private boolean checkUnique(int[] nums) {
        boolean[] seen = new boolean[SIZE + 1];
        for (int n : nums) {
            if (n < 1 || n > 9 || seen[n]) return false;
            seen[n] = true;
        }
        return true;
    }

    private int[][] generatePuzzle(int clues) {
        int[][] board = new int[SIZE][SIZE];
        fillSolution(board, 0, 0);
        Random rand = new Random();
        int removed = SIZE*SIZE - clues;
        while (removed > 0) {
            int r = rand.nextInt(SIZE);
            int c = rand.nextInt(SIZE);
            if (board[r][c] != 0) {
                board[r][c] = 0;
                removed--;
            }
        }
        return board;
    }

    private boolean fillSolution(int[][] b, int row, int col) {
        if (row == SIZE) return true;
        int nextRow = (col == SIZE-1) ? row+1 : row;
        int nextCol = (col+1) % SIZE;
        List<Integer> nums = new ArrayList<>();
        for (int i = 1; i <= SIZE; i++) nums.add(i);
        Collections.shuffle(nums);
        for (int num : nums) {
            if (canPlace(b, row, col, num)) {
                b[row][col] = num;
                if (fillSolution(b, nextRow, nextCol)) return true;
            }
        }
        b[row][col] = 0;
        return false;
    }

    private boolean canPlace(int[][] b, int r, int c, int num) {
        for (int i = 0; i < SIZE; i++) {
            if (b[r][i] == num || b[i][c] == num) return false;
        }
        int br = (r / 3) * 3, bc = (c / 3) * 3;
        for (int i = br; i < br + 3; i++) {
            for (int j = bc; j < bc + 3; j++) {
                if (b[i][j] == num) return false;
            }
        }
        return true;
    }

    static class DigitFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (string != null && string.matches("[1-9]") && fb.getDocument().getLength() + string.length() <= 1) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if ((text != null && text.matches("[1-9]") && fb.getDocument().getLength() - length + text.length() <= 1)
                    || text.isEmpty()) {
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SudokuGUI::new);
    }
}
