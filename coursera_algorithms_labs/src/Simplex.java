///*
//* https://www.andrew.cmu.edu/course/15-355/misc/Top%20Ten%20Algorithms.html
//
//The Simplex Method is a pivot-based algorithm developed by George Dantzig in 1947.
//It moves from one vertex (or “corner point”) of the feasible region (a convex polytope) to a neighboring vertex with a better objective value,
//continuing this process until it finds the optimal solution.
//
//* Standard form.
//- Add variable Z and equation corresponding to objective function.
//- Add slack variable to convert each inequality to an equality.
//*
//*  Steps of the Simplex Method
//Convert to standard form: All constraints as equalities (using slack variables), and all variables non-negative.
//Set up the initial simplex tableau.
//Iterate:
//- Choose entering variable (most negative coefficient in the objective row).
//- Choose leaving variable (smallest positive ratio of RHS to pivot column).
//- Pivot (perform Gaussian elimination on the pivot row).
//- Repeat until no negative coefficients remain in the objective row.
//Terminate: When optimality condition is met (no more improvement possible).
//*
//* */
//public class Simplex {
//    private double[][] a; // simplex tableaux
//    private int m, n; // M constraints, N variables
//
//    public Simplex(double[][] A, double[] b, double[] c) {
//        m = b.length;
//        n = c.length;
//        a = new double[m + 1][m + n + 1];
//
//        // put A[][] into tableau
//        for (int i = 0; i < m; i++)
//            for (int j = 0; j < n; j++)
//                a[i][j] = A[i][j];
//        for (int j = n; j < m + n; j++) a[j - n][j] = 1.0; // put I[][] into tableau
//        for (int j = 0; j < n; j++) a[m][j] = c[j];        // put c[] into tableau
//        for (int i = 0; i < m; i++) a[i][m + n] = b[i];    // put b[] into tableau
//    }
//
//    //index of first column whose objective function coefficient is positive
//    private int bland(){
//        //entering column q has positive
//        //objective function coefficient
//        for(int q=0;q<m+n; q++)
//            if(a[M][q]> 0) return q;
//        return -1;
//    }
//
//    private int minRatioRule(int q){
//        int p=-1; //leaving row
//        for(int i=0;i<m;i++){
//            if(a[i][q]<=0) continue; //consider only  positive entries
//            else if(p==-1) p=i;
//            else if(a[i][m+n]/a[i][q]< a[p][m+n]/a[p][q]) //row p has min ratio so far
//                p=i;
//        }
//        return p;
//    }
//}
