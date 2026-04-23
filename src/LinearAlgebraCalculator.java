public class LinearAlgebraCalculator {
    public static void main(String[] args) {
        double[][] testMatrix = {
            {2, 2, 3, 4, 5, 6, 7},
            {1, 3, 3, 4, 5, 6, 7},
            {1, 2, 4, 4, 5, 6, 7},
            {1, 2, 3, 5, 5, 6, 7},
            {1, 2, 3, 4, 6, 6, 7},
            {1, 2, 3, 4, 5, 7, 7},
            {1, 2, 3, 4, 5, 6, 8}
        };
        printMatrix(MatrixEquations.inverseMatrix(testMatrix));
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

