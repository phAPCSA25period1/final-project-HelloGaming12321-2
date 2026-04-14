public class MatrixManipulation {
    public static void RowSwitching(double[][] matrix, int rowOne, int rowTwo){
        double[] temp = matrix[rowTwo];
        matrix[rowTwo] = matrix[rowOne];
        matrix[rowOne] = temp;
    }
    public static void MoveToFront(double[][] matrix, int row){
        double[] temp = matrix[row];
        for(int i = row; i > 0; i--){
            matrix[i] = matrix[i - 1];
        }
        matrix[0] = temp;
    }
    public static void MoveToBack(double[][] matrix, int row){
        double[] temp = matrix[row - 1];
        for(int i = row; i < matrix.length; i++){
            matrix[i - 1] = matrix[i];
        }
        matrix[matrix.length - 1] = temp;
    }
    public static void RowMultiplication(double[][] matrix, int row, double scalar){
        for(int i = 0; i < matrix[0].length; i++){
            matrix[row][i] *= scalar;
        }
    }
    public static void RowAddition(double[][] matrix, int rowOne, int rowTwo, double scalar){
        for(int i = 0; i < matrix[rowOne].length; i++){
            matrix[rowOne][i] += matrix[rowTwo][i] * scalar;
        }
    }
    public static void FlipRows(double[][] matrix){
        for(int i = 0; i < matrix.length / 2; i++){
            double[] temp = matrix[i];
            matrix[i] = matrix[matrix.length - i - 1];
            matrix[matrix.length - i - 1] = temp;
        }
    }
    public static void FlipColumns(double[][] matrix){
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[i].length/2; j++){
                double temp = matrix[i][j];
                matrix[i][j] = matrix[i][matrix[i].length - j - 1];
                matrix[i][matrix[i].length - j -1] = temp;
            }
        }
    }
    public static double[][] RemoveRow(double[][] matrix, int row){
        double[][] removedRow = new double[matrix.length - 1][matrix[0].length];
        for(int i = 0; i < matrix.length; i++){
            if(i != row){
                if(i > row){
                    removedRow[i - 1] = matrix[i];
                }
                else{
                    removedRow[i] = matrix[i];
                }
            }
        }
        return removedRow;
    }
    public static double[][] RemoveColumn(double[][] matrix, int column){
        double[][] removedColumn = new double[matrix.length][matrix[0].length - 1];
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[0].length; j++){
                if(j != column){
                    if(j > column){
                        removedColumn[i][j - 1] = matrix[i][j];
                    }
                    else{
                        removedColumn[i][j] = matrix[i][j];
                    }
                }
            }
        }
        return removedColumn;
    }
}
