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
    public static double[][] inverseMatrix(double[][] matrix){
        Double determinant = determinant(matrix);
        if (determinant == null || determinant == 0){
            System.out.println("There is no inverse matrix");
            return null;
        }
        else{
            return (MatrixManipulation.matrixMultiplication(adjointMatrix(matrix), 1 / determinant));
        }
    }
    public static double[][] cofactorMatrix(double[][] matrix){
        if(matrix.length != matrix[0].length){
            System.out.println("There is no cofactor matrix");
            return null;
        }
        double[][] determinantMatrix = new double[matrix.length - 1][matrix[0].length - 1];
        double[][] cofactorMatrix = new double[matrix.length][matrix[0].length];
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[0].length; j++){
                for(int k = 0; k < matrix.length - 1; k++){
                    for(int l = 0; l < matrix[0].length - 1; l++){
                        if(k < i && l < j){
                            determinantMatrix[k][l] = matrix[k][l];
                        }
                        else if(k >= i && l < j){
                            determinantMatrix[k][l] = matrix[k + 1][l];
                        }
                        else if(k < i && l >= j){
                            determinantMatrix[k][l] = matrix[k][l + 1];
                        }
                        else{
                            determinantMatrix[k][l] = matrix[k + 1][l + 1];
                        }
                    }
                }
                cofactorMatrix[i][j] = determinant(determinantMatrix);
                if((i + j) % 2 == 1){
                    cofactorMatrix[i][j] *= -1;
                }
            }
        }
        return cofactorMatrix;
    }
    public static double[][] transposeMatrix(double[][] matrix){
        double[][] transposeMatrix = new double[matrix[0].length][matrix.length];
        for(int i = 0; i < transposeMatrix.length; i++){
            for(int j = 0; j < transposeMatrix[0].length; j++){
                transposeMatrix[i][j] = matrix[j][i];
            }
        }
        return transposeMatrix;
    }
    public static double[][] adjointMatrix(double[][] matrix){
        if(matrix.length != matrix[0].length){
            System.out.println("There is no adjoint matrix");
            return null;
        }
        return transposeMatrix(cofactorMatrix(matrix));
    }
    public static Double determinant(double[][] matrix){
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
                    determinant += (matrix[0][i] * determinant(MatrixManipulation.RemoveRow(MatrixManipulation.RemoveColumn(matrix, i) , 0)));
                }
                else{
                    determinant -= (matrix[0][i] * determinant(MatrixManipulation.RemoveRow(MatrixManipulation.RemoveColumn(matrix, i) , 0)));
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
