import edu.princeton.cs.algs4.*;
import java.awt.Color;

public class SeamCarver {
    private Picture picture;
    private double[][] energyTo;
    private int[][] xTo;
    private double[][] picEnergy;
    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
    	if(picture==null){
    		System.out.println("picture is null");
    		return;
    	}
        this.picture = picture;
        picEnergy = new double[width()][height()];
        for(int i =0 ;i<width();i++){
            for(int j =0 ;j<height();j++){
                picEnergy[i][j]=calEnergy(i,j);
            }
        }
    }
    private double calEnergy(int x,int y){
    if(x==0 || y==0|| x==width()-1 || y==height()-1){
        return 1000;
    }
    Color left = picture.get(x-1,y);
    Color right = picture.get(x+1,y);
    Color top = picture.get(x,y-1);
    Color bottom = picture.get(x,y+1);
    double e1 = ((left.getRed()-right.getRed())*(left.getRed()-right.getRed()))+
                ((left.getBlue()-right.getBlue())*(left.getBlue()-right.getBlue()))+
                ((left.getGreen()-right.getGreen())*(left.getGreen()-right.getGreen()));
    double e2 = ((top.getRed()-bottom.getRed())*(top.getRed()-bottom.getRed()))+
                ((top.getBlue()-bottom.getBlue())*(top.getBlue()-bottom.getBlue()))+
                ((top.getGreen()-bottom.getGreen())*(top.getGreen()-bottom.getGreen()));

    return Math.sqrt(e1+e2);
}

    // current picture
    public Picture picture() {
        return picture;
    }

    // width of current picture
    public int width() {
        return picture.width();
    }

    // height of current picture
    public int height() {
        return picture.height();
    }

    /// energy of pixel at column x and row y
    public double energy(int x, int y) {
       return picEnergy[x][y];
    }


    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        // Transpose picture.
        Picture original = picture;
        Picture transpose = new Picture(original.height(), original.width());

        for (int w = 0; w < transpose.width(); w++) {
            for (int h = 0; h < transpose.height(); h++) {
                transpose.set(w, h, original.get(h, w));
            }
        }

        this.picture = transpose;

        // call findVerticalSeam
        int[] seam = findVerticalSeam();

        // Transpose back.
        this.picture = original;

        return seam;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        energyTo = new double[width()][height()];
        xTo = new int[width()][height()];

        for (int x = 0; x < width(); x++) {
            for (int y = 0; y < height(); y++) {
                energyTo[x][y] = Double.POSITIVE_INFINITY;
            }
        }

        for (int x = 0; x < width(); x++) {
            energyTo[x][0] = 1000;
        }

        for (int y = 0; y < height() - 1; y++) {
            for (int x = 0; x < width(); x++) {
                if (x > 0) {
                    relax(x, y, x - 1, y + 1);
                }

                relax(x, y, x, y + 1);

                if (x < width() - 1) {
                    relax(x, y, x + 1, y + 1);
                }
            }
        }
        //System.out.println(Arrays.deepToString(energyTo));

        // find minimum energy path
        double minEnergy = Double.POSITIVE_INFINITY;
        int minEnergyX = -1;
        for (int w = 0; w < width(); w++) {
            if (energyTo[w][height() - 1] < minEnergy) {
                minEnergyX = w;
                minEnergy = energyTo[w][height() - 1];
            }
        }
        assert minEnergyX != -1;

        int[] seam = new int[height()];
        seam[height() - 1] = minEnergyX;
        int prevX = xTo[minEnergyX][height() - 1];
		int h;
		int current=0;
        for (h = height() - 2; h >=0; h--) {
            seam[h] = prevX;
            current = prevX;
            prevX = xTo[prevX][h];

        }
        // seam[0] = current;

        //my
        // for(h = height()-2;h>=0;h--){
        //     seam[h] = prevX;
        //     if(prevX >0 && picEnergy[prevX-1][h]  <  picEnergy[prevX][h] ){
        //        seam[h] = prevX-1;
        //        prevX = prevX-1;
        //     }
        //     if(prevX <height()-2 && picEnergy[prevX+1][h]  <  picEnergy[prevX][h] ){
        //        seam[h] = prevX+1;
        //        prevX = prevX+1;
        //     }
        //      prevX = xTo[prevX][h];
        // }

        return seam;
    }

    private void relax(int x1, int y1, int x2, int y2) {
        if (energyTo[x2][y2] > energyTo[x1][y1] + energy(x2, y2)) {
            energyTo[x2][y2] = energyTo[x1][y1] + energy(x2, y2);
            xTo[x2][y2] = x1;
        }
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        // Transpose picture.
        Picture original = picture;
        Picture transpose = new Picture(original.height(), original.width());

        for (int w = 0; w < transpose.width(); w++) {
            for (int h = 0; h < transpose.height(); h++) {
                transpose.set(w, h, original.get(h, w));
            }
        }

        this.picture = transpose;
        transpose = null;
        original = null;

        // call removeVerticalSeam
        removeVerticalSeam(seam);

        // Transpose back.
        original = picture;
        transpose = new Picture(original.height(), original.width());

        for (int w = 0; w < transpose.width(); w++) {
            for (int h = 0; h < transpose.height(); h++) {
                transpose.set(w, h, original.get(h, w));
            }
        }

        this.picture = transpose;
        transpose = null;
        original = null;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null) {
            throw new NullPointerException();
        }

        if (seam.length != height()) {
            throw new IllegalArgumentException();
        }

        Picture original = this.picture;
        Picture carved = new Picture(original.width() - 1, original.height());

        for (int h = 0; h < carved.height(); h++) {
            for (int w = 0; w < seam[h]; w++) {
                carved.set(w, h, original.get(w, h));
            }
            for (int w = seam[h]; w < carved.width(); w++) {
                carved.set(w, h, original.get(w + 1, h));
            }
        }

        this.picture = carved;
    }


}
