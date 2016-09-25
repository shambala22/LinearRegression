import Jama.Matrix;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by shambala on 25/09/16.
 */
public class Main {

    public static double ALPHA = 0.0001;

    public static void main(String[] args) {
        new Main().run();
    }

    void run() {
        ArrayList<Flat> flats = new Reader().read("prices.txt");
        double[][] A = new double[3][3];
        double[] f = new double[3];

        A[0][0] = flats.size();
        double allRooms = 0;
        double allRooms2 = 0;
        double allSpace = 0;
        double allSpace2 = 0;
        double allSpaceRooms = 0;
        for (Flat flat : flats) {
            allRooms+=flat.roomsCount;
            allRooms2+=flat.roomsCount*flat.roomsCount;
            allSpace+=flat.area;
            allSpace2+=flat.area*flat.area;
            allSpaceRooms+=flat.area*flat.roomsCount;
        }

        A[0][1] = allRooms;
        A[1][0] = allRooms;
        A[0][2] = allSpace;
        A[2][0] = allSpace;
        A[1][1] = allRooms2;
        A[2][2] = allSpace2;
        A[1][2] = allSpaceRooms;
        A[2][1] = allSpaceRooms;

        for (int i = 0; i<3; i++) {
            if (i == 0) {
                for (Flat flat : flats) {
                    f[i] += flat.price;
                }
            } else if (i == 1) {
                for (Flat flat : flats) {
                    f[i] += flat.price*flat.roomsCount;
                }
            } else if (i == 2) {
                for (Flat flat : flats) {
                    f[i] += flat.price*flat.price;
                }
            }
        }


        Matrix AMatrix = new Matrix(A);
        Matrix fMatrix = new Matrix(f, 3);
        Matrix bMatrix = AMatrix.inverse().times(fMatrix);
        double b0 = bMatrix.get(0,0);
        double b1 = bMatrix.get(1,0);
        double b2 = bMatrix.get(2,0);


        ArrayList<Flat> line = new ArrayList<>();
        for (double a = 0; a < 5000; a ++ ) {
            line.add(new Flat(a, 3, (b0 + b1 * (new Random().nextInt(5)) + b2 * a) * ALPHA * 10));
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
}
