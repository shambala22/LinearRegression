import Jama.Matrix;

import java.util.ArrayList;

/**
 * Created by shambala on 25/09/16.
 */
public class Main {

    public static double ALPHA = 0.0001;
    void run() {
        ArrayList<Flat> flats = new Reader().read("prices.txt");
        double[][] X = new double[flats.size()][2];
        double[] y = new double[flats.size()];
        double[] initArg = {100, 100};
        for (int i = 0; i< flats.size(); i++) {
            Flat flat = flats.get(i);
            X[i][0] = flat.area;
            X[i][1] = flat.roomsCount;
            y[i] = flat.price;
        }
        Matrix xMatrix = new Matrix(X);
        Matrix yMatrix = new Matrix(y, flats.size());
        Matrix arg = new Matrix(initArg, 2);
        for (int k = 0; k<1000; k++) {
            arg = arg.minus(derivative(xMatrix, yMatrix, arg).times(ALPHA));
        }
        ArrayList<Flat> line = new ArrayList<>();
        for (double a = 0; a < 100000; a ++ ) {
            line.add(new Flat(arg.get(0, 0)*a, 3, a));
        }
        new Plot("x", "y").addGraphic(flats, "flats").addGraphic(line, "line").show();
    }

    Matrix derivative(Matrix X, Matrix y, Matrix arg) {
        return X.transpose().times(X.times(arg).minus(y));
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
