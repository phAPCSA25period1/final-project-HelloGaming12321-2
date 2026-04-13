public class MatrixEquations {
    public rref(){
        int tracker;
        double[] temp;
        double[][] reducedMatrix = new double[matrix.length][matrix[0].length];
        for(int i = 0; i < matrix[0].length; i++){
            for(int j = 0; j < matrix.length; j++){
                if(matrix[i][j] != 0){
                    tracker = j;
                    temp = matrix[j];
                    for(int k = j; k > 0; k--){
                        matrix[k] = matrix[k-1];
                    }
                    matrix[0] = matrix[j];
                }
            }
    }
    public transpose(){

    }
    public inverse(){

    }
    public determinant(){

    }
    public Eigenvalues(){

    }
    public Eigenvectors(){

    }
    public Rank(){

    }
    public Nullity(){

    }
}
