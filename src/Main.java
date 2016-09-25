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
        for (int k = 0; k<47; k++) {
            arg = arg.minus(derivative(xMatrix, yMatrix, arg).times(ALPHA));
        }
        ArrayList<Flat> line = new ArrayList<>();
        for (double a = 0; a < 5000; a ++ ) {
            line.add(new Flat(a, 3, 156*a));
        }
        ArrayList<Flat> oneroom = new ArrayList<>();
        ArrayList<Flat> tworoom = new ArrayList<>();
        ArrayList<Flat> threeroom = new ArrayList<>();
        ArrayList<Flat> fourroom = new ArrayList<>();
        ArrayList<Flat> fiveroom = new ArrayList<>();
        for (Flat flat : flats) {
            switch ((int) flat.roomsCount) {
                case 1 : oneroom.add(flat);
                    break;
                case 2 : tworoom.add(flat);
                    break;
                case 3 : threeroom.add(flat);
                    break;
                case 4 : fourroom.add(flat);
                    break;
                case 5 : fiveroom.add(flat);
                    break;
            }
        }



        new Plot("area", "price").addGraphic(line, "line").addGraphic(oneroom, "1flats").addGraphic(tworoom, "2flats").addGraphic(threeroom, "3flats").addGraphic(fourroom, "4flats").addGraphic(fiveroom, "5flats").show();
    }

    Matrix derivative(Matrix X, Matrix y, Matrix arg) {
        return X.transpose().times(X.times(arg).minus(y));
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
