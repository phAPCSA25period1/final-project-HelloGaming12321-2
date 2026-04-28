import java.util.Scanner;

public class LinearAlgebraCalculator {
    //Written by AI
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Define your matrix:");
        double[][] myMatrix = readMatrix(scanner);

        System.out.println("\nSelect Operation:");
        System.out.println("1. RREF");
        System.out.println("2. Determinant");
        System.out.println("3. Inverse");
        System.out.println("4. Rank & Nullity");
        System.out.print("Choice: ");
        int choice = scanner.nextInt();

        System.out.println("\nResult:");
        switch (choice) {
            case 1:
                printMatrix(MatrixEquations.rref(myMatrix));
                break;
            case 2:
                Double det = MatrixEquations.determinant(myMatrix);
                if (det != null) System.out.printf("%.4f\n", det);
                break;
            case 3:
                double[][] inv = MatrixEquations.inverseMatrix(myMatrix);
                if (inv != null) printMatrix(inv);
                break;
            case 4:
                System.out.println("Rank: " + MatrixEquations.rank(myMatrix));
                System.out.println("Nullity: " + MatrixEquations.nullity(myMatrix));
                break;
            default:
                System.out.println("Invalid selection.");
        }
        scanner.close();
    }

    public static double[][] readMatrix(Scanner scanner) {
        System.out.print("Number of rows: ");
        int rows = scanner.nextInt();
        System.out.print("Number of columns: ");
        int cols = scanner.nextInt();

        double[][] matrix = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.printf("Element [%d][%d]: ", i, j);
                matrix[i][j] = scanner.nextDouble();
            }
        }
        return matrix;
    }

    public static void printMatrix(double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                double value = matrix[i][j];
                if (Math.abs(value) < 0.00005) {
                    value = 0.0;
                }
                System.out.printf("%10.4f ", value);
            }
            System.out.println();
        }
    }
}
