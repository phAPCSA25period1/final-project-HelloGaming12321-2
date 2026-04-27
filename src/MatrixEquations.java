public class MatrixEquations {
    private static double EPSILON = 1e-9;
    public static double[][] rref(double[][] matrix){
        double[][] rrefMatrix = new double[matrix.length][matrix[0].length];
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[0].length; j++){
                rrefMatrix[i][j] = matrix[i][j];
            }
        }
        int skippedPivotCounter = 0;
        int columnNonZeroCounter = 0;
        for(int i = 0; i < rrefMatrix[0].length && i - skippedPivotCounter < rrefMatrix.length; i++){
            for(int j = i; j - skippedPivotCounter < rrefMatrix.length; j++){
                if(!(Math.abs(rrefMatrix[j - skippedPivotCounter][i]) < EPSILON)){
                    MatrixManipulation.RowSwitching(rrefMatrix, j - skippedPivotCounter, i - skippedPivotCounter);
                    columnNonZeroCounter++;
                }
            }
            if(columnNonZeroCounter == 0){
                skippedPivotCounter++;
            }
            else{
                for(int j = 0; j < rrefMatrix.length; j++){
                    if(j != i - skippedPivotCounter && !(Math.abs(rrefMatrix[j][i]) < EPSILON) && !(Math.abs(rrefMatrix[i - skippedPivotCounter][i]) < EPSILON)){
                        MatrixManipulation.RowAddition(rrefMatrix, j, i - skippedPivotCounter, -rrefMatrix[j][i] / rrefMatrix[i - skippedPivotCounter][i]);
                    }
                }
                if(!(Math.abs(rrefMatrix[i - skippedPivotCounter][i]) < EPSILON)){
                    MatrixManipulation.RowMultiplication(rrefMatrix, i - skippedPivotCounter, 1.0 / rrefMatrix[i - skippedPivotCounter][i]);
                }
            }
            columnNonZeroCounter = 0;
        }
        return rrefMatrix;
    }
    public static double[][] inverseMatrix(double[][] matrix){
        Double determinant = determinant(matrix);
        if (determinant == null || Math.abs(determinant) < EPSILON){
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
