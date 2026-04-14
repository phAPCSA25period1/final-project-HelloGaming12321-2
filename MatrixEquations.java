public class MatrixEquations {
    public static double[][] rref(double[][] matrix){
        for(int i = 0; i < matrix.length; i++){
            for(int j = i; j < matrix.length; j++){
                if(matrix[j][i] != 0){
                    MatrixManipulation.RowSwitching(matrix, j, i);
                }
            }
            for(int j = 0; j < matrix.length; j++){
                if(j != i && matrix[i][i] != 0 && matrix[j][i] != 0){
                    MatrixManipulation.RowAddition(matrix, j, i, -matrix[j][i] / matrix[i][i]);
                }
            }
            if(matrix[i][i] != 0){
                MatrixManipulation.RowMultiplication(matrix, i, 1 / matrix[i][i]);
            }
        }
        for(int i = 0; i < matrix.length; i++){
            int nonZeroCounter = 0;
            for(int j = 0; j < matrix[0].length && nonZeroCounter == 0; j++){
                if(matrix[i][j] != 0){
                    nonZeroCounter++;
                }
            }
            if(nonZeroCounter == 0){
                MatrixManipulation.MoveToBack(matrix, i + 1);
            }
        }
        return matrix;
    }
    public static double[][] Transpose(double[][] matrix){
        MatrixManipulation.FlipColumns(matrix);
        MatrixManipulation.FlipRows(matrix);
        return matrix;
    }
    public static Double inverse(double[][] matrix){
        if(Determinant(matrix) == 0){
            return null;
        }
        return matrix;
    }
    public static Double Determinant(double[][] matrix){
        double determinant = 0;
        if(matrix.length != matrix[0].length){
            System.out.println("There is no determinant");
            return null;
        }
        else if(matrix.length == 1){
            determinant = matrix[0][0];
            return determinant;
        }
        else if(matrix.length == 2){
            determinant = (matrix[0][0] * matrix[1][1]) - (matrix[1][0] * matrix[0][1]);
            return determinant;
        }
        else{
            for(int i = 0; i < matrix.length; i++){
                if(i % 2 == 0){
                    determinant += (matrix[0][i] * Determinant(MatrixManipulation.RemoveRow(MatrixManipulation.RemoveColumn(matrix, i) , 0)));
                }
                else{
                    determinant -= (matrix[0][i] * Determinant(MatrixManipulation.RemoveRow(MatrixManipulation.RemoveColumn(matrix, i) , 0)));
                }
            }
            return determinant;
        }
    }
    public void Eigenvalues(){

    }
    public void Eigenvectors(){

    }
    public void Rank(){

    }
    public void Nullity(){

    }
}
