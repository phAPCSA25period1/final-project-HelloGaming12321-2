public class LinearAlgebraCalculator {
    public static void main(String[] args) {
        double[][] testMatrix = {
            { 0,  0,  0,  2,  4, -2,  8},
            { 0,  0,  0,  0,  0,  0,  0},
            { 1,  3,  0,  2,  1,  4,  5},
            { 2,  6,  0,  4,  2,  8, 10},
            { 0,  2,  0,  4,  6, -2,  4},
            { 0,  0,  0,  0,  0,  5, 10},
            {-1, -3,  0, -2, -1, -4, -5}
        };
        printMatrix(MatrixEquations.rref(testMatrix));
    }
   public static void printMatrix(double[][] matrix) {
    //Written by AI
    for (int i = 0; i < matrix.length; i++) {
        for (int j = 0; j < matrix[i].length; j++) {
            double value = matrix[i][j];

            // Snap extremely small numbers and -0.0 to exactly 0.0
            if (Math.abs(value) < 0.00005) {
                value = 0.0;
                }

            System.out.printf("%10.4f ", value);
            }
        System.out.println();
        }
    }
}

