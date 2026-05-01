import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class LinearAlgebraGUI extends JFrame {

    private JTextArea displayArea;
    private JTextField inputField;
    private Map<String, double[][]> variables = new HashMap<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LinearAlgebraGUI().setVisible(true);
        });
    }

    public LinearAlgebraGUI() {
        setTitle("Linear Algebra Calculator");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Output Display Area
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        displayArea.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(displayArea);
        add(scrollPane, BorderLayout.CENTER);

        // Input Field (Thin cursor by default in JTextField)
        inputField = new JTextField();
        inputField.setFont(new Font("Monospaced", Font.PLAIN, 16));
        inputField.setMargin(new Insets(5, 5, 5, 5));
        inputField.addActionListener(e -> processInputAndClear()); // Triggers on Enter key
        add(inputField, BorderLayout.NORTH);

        // Button Panel
        JPanel buttonPanel = new JPanel(new GridLayout(6, 4, 5, 5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        String[] buttons = {
            "rref ", "inv ", "det ", "rank ",
            "nullity ", "eigval ", "eigvec ", "ans",
            "7", "8", "9", "[",
            "4", "5", "6", "]",
            "1", "2", "3", ";",
            "0", " ", "=", "Enter"
        };

        for (String text : buttons) {
            JButton button = new JButton(text.trim());
            button.setFont(new Font("Arial", Font.BOLD, 14));
            button.addActionListener(new ButtonClickListener(text));
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.SOUTH);

        display("System Initialized.\nFormat: <command> <matrix> OR <var> = <command> <matrix>");
        display("Example: A = [1 2; 3 4]\n---------------------------------------------------------");
    }

    private class ButtonClickListener implements ActionListener {
        private String textToAppend;

        public ButtonClickListener(String textToAppend) {
            this.textToAppend = textToAppend;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Enter")) {
                processInputAndClear();
            } else {
                inputField.setText(inputField.getText() + textToAppend);
                inputField.requestFocusInWindow();
            }
        }
    }

    private void processInputAndClear() {
        String input = inputField.getText().trim();
        if (input.isEmpty()) return;

        display("> " + input);
        inputField.setText("");
        processInput(input);
    }

    private void display(String text) {
        displayArea.append(text + "\n");
        displayArea.setCaretPosition(displayArea.getDocument().getLength());
    }

    private void processInput(String input) {
        try {
            String targetVar = null;

            if (input.contains("=")) {
                String[] parts = input.split("=", 2);
                targetVar = parts[0].trim();
                input = parts[1].trim();
            }

            int firstSpace = input.indexOf(' ');
            String command = "";
            String matrixString = input;

            if (firstSpace != -1) {
                String potentialCmd = input.substring(0, firstSpace).trim().toLowerCase();
                if (isCommand(potentialCmd)) {
                    command = potentialCmd;
                    matrixString = input.substring(firstSpace).trim();
                }
            } else if (isCommand(input.toLowerCase())) {
                throw new IllegalArgumentException("Command requires a matrix argument.");
            }

            double[][] matrix;
            if (variables.containsKey(matrixString)) {
                matrix = variables.get(matrixString);
            } else {
                matrix = parseMatrix(matrixString);
            }

            if (matrix == null || matrix.length == 0) {
                throw new IllegalArgumentException("Empty matrix provided.");
            }

            if (command.isEmpty()) {
                saveAndPrintResult(matrix, targetVar);
            } else {
                executeCommand(command, matrix, targetVar);
            }

        } catch (IllegalArgumentException e) {
            display("Error: " + e.getMessage() + "\n");
        } catch (Exception e) {
            display("Error: Invalid operation or syntax.\n");
        }
    }

    private boolean isCommand(String str) {
        return str.equals("rref") || str.equals("inv") || str.equals("det") ||
               str.equals("rank") || str.equals("nullity") || str.equals("eigval") || str.equals("eigvec");
    }

    private double[][] parseMatrix(String input) {
        input = input.replaceAll("\\[|\\]", "").trim();
        if (input.isEmpty()) return null;

        String[] rows = input.split(";");
        double[][] matrix = new double[rows.length][];

        for (int i = 0; i < rows.length; i++) {
            String[] cols = rows[i].trim().split("[,\\s]+");
            matrix[i] = new double[cols.length];
            for (int j = 0; j < cols.length; j++) {
                try {
                    matrix[i][j] = Double.parseDouble(cols[j]);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Non-numeric value found.");
                }
            }
        }

        int expectedCols = matrix[0].length;
        for (int i = 1; i < matrix.length; i++) {
            if (matrix[i].length != expectedCols) {
                throw new IllegalArgumentException("Rows must have consistent lengths.");
            }
        }
        return matrix;
    }

    private void executeCommand(String command, double[][] matrix, String targetVar) {
        double[][] resultMatrix = null;
        Double scalarResult = null;
        Double[] arrayResult = null;

        switch (command) {
            case "rref":
                resultMatrix = MatrixEquations.rref(matrix);
                break;
            case "inv":
                resultMatrix = MatrixEquations.inverseMatrix(matrix);
                if (resultMatrix == null) throw new IllegalArgumentException("Singular matrix.");
                break;
            case "det":
                scalarResult = MatrixEquations.determinant(matrix);
                if (scalarResult == null) throw new IllegalArgumentException("Matrix must be square.");
                display(String.format("%.4f", scalarResult));
                resultMatrix = new double[][]{{scalarResult}};
                break;
            case "rank":
                scalarResult = (double) MatrixEquations.rank(matrix);
                display(String.valueOf(scalarResult.intValue()));
                resultMatrix = new double[][]{{scalarResult}};
                break;
            case "nullity":
                scalarResult = (double) MatrixEquations.nullity(matrix);
                display(String.valueOf(scalarResult.intValue()));
                resultMatrix = new double[][]{{scalarResult}};
                break;
            case "eigval":
                arrayResult = MatrixEquations.eigenvalues();
                display(Arrays.toString(arrayResult));
                resultMatrix = new double[1][arrayResult.length];
                for(int i = 0; i < arrayResult.length; i++) resultMatrix[0][i] = arrayResult[i] != null ? arrayResult[i] : 0;
                break;
            case "eigvec":
                resultMatrix = MatrixEquations.eigenvectors();
                break;
        }

        if (resultMatrix != null && scalarResult == null && arrayResult == null) {
            saveAndPrintResult(resultMatrix, targetVar);
        } else if (resultMatrix != null) {
            saveResultOnly(resultMatrix, targetVar);
            display("");
        }
    }

    private void saveAndPrintResult(double[][] result, String targetVar) {
        StringBuilder sb = new StringBuilder();
        for (double[] row : result) {
            sb.append("[ ");
            for (double val : row) {
                if (Math.abs(val) < 1e-9) val = 0.0;
                sb.append(String.format("%8.4f ", val));
            }
            sb.append("]\n");
        }
        display(sb.toString());
        saveResultOnly(result, targetVar);
    }

    private void saveResultOnly(double[][] result, String targetVar) {
        variables.put("ans", result);
        if (targetVar != null) {
            variables.put(targetVar, result);
            display("Stored in variable: " + targetVar + "\n");
        }
    }
}
