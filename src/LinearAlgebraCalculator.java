import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LinearAlgebraCalculator {

    private static Map<String, double[][]> variables = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        printHelp();

        while (true) {
            System.out.print("\n> ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("quit")) {
                break;
            }
            if (input.equalsIgnoreCase("help")) {
                printHelp();
                continue;
            }
            if (input.equalsIgnoreCase("examples")) {
                printExamples();
                continue;
            }
            if (input.isEmpty()) {
                continue;
            }

            processInput(input);
        }
        scanner.close();
    }

    private static void printHelp() {
        System.out.println("\n--- Linear Algebra Calculator ---");
        System.out.println("Format: <command> <matrix> OR <variable> = <command> <matrix>");
        System.out.println("\nMatrix Syntax Rules (MATLAB style):");
        System.out.println("  1. Separate elements in a row using spaces or commas.");
        System.out.println("  2. Separate rows using semicolons (;).");
        System.out.println("  3. Brackets [] are optional but supported.");
        System.out.println("\nVariables:");
        System.out.println("  - Assign: A = [1 2; 3 4]");
        System.out.println("  - Recall: rref A");
        System.out.println("  - 'ans' stores the last output.");
        System.out.println("\nCommands:");
        System.out.println("  rref     - Calculate Reduced Row Echelon Form");
        System.out.println("  inv      - Calculate Inverse Matrix");
        System.out.println("  det      - Calculate Determinant");
        System.out.println("  rank     - Calculate Rank");
        System.out.println("  nullity  - Calculate Nullity");
        System.out.println("  eigval   - Calculate Eigenvalues");
        System.out.println("  eigvec   - Calculate Eigenvectors");
        System.out.println("  examples - Show interactive usage examples");
        System.out.println("  help     - Reprint this menu");
        System.out.println("  exit     - Quit program");
        System.out.println("---------------------------------");
    }

    private static void printExamples() {
        System.out.println("\n--- Usage Examples ---");
        System.out.println("Assignment: A = [1 2; 3 4]");
        System.out.println("Using Var:  inv A");
        System.out.println("Using Ans:  rref ans");
        System.out.println("Combined:   B = inv A");
        System.out.println("----------------------");
    }

    private static void processInput(String input) {
        try {
            String targetVar = null;

            // Handle Assignment
            if (input.contains("=")) {
                String[] parts = input.split("=", 2);
                targetVar = parts[0].trim();
                input = parts[1].trim();
            }

            // Parse Command vs Matrix
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

            // Retrieve variable or parse raw string
            double[][] matrix;
            if (variables.containsKey(matrixString)) {
                matrix = variables.get(matrixString);
            } else {
                matrix = parseMatrix(matrixString);
            }

            if (matrix == null || matrix.length == 0) {
                throw new IllegalArgumentException("Empty matrix provided.");
            }

            // Execute
            if (command.isEmpty()) {
                saveAndPrintResult(matrix, targetVar);
            } else {
                executeCommand(command, matrix, targetVar);
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: Invalid operation or syntax. Type 'examples' for formats.");
        }
    }

    private static boolean isCommand(String str) {
        return str.equals("rref") || str.equals("inv") || str.equals("det") ||
               str.equals("rank") || str.equals("nullity") || str.equals("eigval") || str.equals("eigvec");
    }

    private static double[][] parseMatrix(String input) {
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
                    throw new IllegalArgumentException("Non-numeric value found in matrix.");
                }
            }
        }

        int expectedCols = matrix[0].length;
        for (int i = 1; i < matrix.length; i++) {
            if (matrix[i].length != expectedCols) {
                throw new IllegalArgumentException("Matrix rows must have consistent lengths.");
            }
        }
        return matrix;
    }

    private static void executeCommand(String command, double[][] matrix, String targetVar) {
        double[][] resultMatrix = null;
        Double scalarResult = null;
        Double[] arrayResult = null;

        switch (command) {
            case "rref":
                resultMatrix = MatrixEquations.rref(matrix);
                break;
            case "inv":
                resultMatrix = MatrixEquations.inverseMatrix(matrix);
                if (resultMatrix == null) throw new IllegalArgumentException("Matrix is singular; inverse does not exist.");
                break;
            case "det":
                scalarResult = MatrixEquations.determinant(matrix);
                if (scalarResult == null) throw new IllegalArgumentException("Matrix must be square to calculate determinant.");
                System.out.printf("%.4f\n", scalarResult);
                resultMatrix = new double[][]{{scalarResult}}; // Store as 1x1 matrix
                break;
            case "rank":
                scalarResult = (double) MatrixEquations.rank(matrix);
                System.out.println(scalarResult.intValue());
                resultMatrix = new double[][]{{scalarResult}};
                break;
            case "nullity":
                scalarResult = (double) MatrixEquations.nullity(matrix);
                System.out.println(scalarResult.intValue());
                resultMatrix = new double[][]{{scalarResult}};
                break;
            case "eigval":
                arrayResult = MatrixEquations.eigenvalues();
                System.out.println(Arrays.toString(arrayResult));
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
            // Silently store scalar/array results into memory without re-printing as a matrix
            saveResultOnly(resultMatrix, targetVar);
        }
    }

    private static void saveAndPrintResult(double[][] result, String targetVar) {
        printMatrix(result);
        saveResultOnly(result, targetVar);
    }

    private static void saveResultOnly(double[][] result, String targetVar) {
        variables.put("ans", result);
        if (targetVar != null) {
            variables.put(targetVar, result);
            System.out.println("Stored in variable: " + targetVar);
        }
    }

    private static void printMatrix(double[][] matrix) {
        if (matrix == null) return;
        for (double[] row : matrix) {
            System.out.print("[ ");
            for (double val : row) {
                if (Math.abs(val) < 1e-9) val = 0.0;
                System.out.printf("%8.4f ", val);
            }
            System.out.println("]");
        }
    }
}
