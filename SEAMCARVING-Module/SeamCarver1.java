import java.awt.Color;
import java.util.*;
public class SeamCarver {
	// create a seam carver object based on the given picture
	Picture pic;
	double[][] picEnergy;
	public SeamCarver(Picture picture) {
		if(picture==null){
			System.out.println("picture is null");
			return;
		}
		pic = picture;
		picEnergy = new double[width()][height()];
		for(int i =0 ;i<width();i++){
			for(int j =0 ;j<height();j++){
				picEnergy[i][j]=calEnergy(i,j);
			}
		}

	}
	 double calEnergy(int x,int y){
   	if(x==0 || y==0|| x==width()-1 || y==height()-1){
   		return 1000;
   	}
   	Color left = pic.get(x-1,y);
   	Color right = pic.get(x+1,y);
   	Color top = pic.get(x,y-1);
   	Color bottom = pic.get(x,y+1);
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
		return pic;
	}
	// width of current picture
	public int width() {
		return pic.width();
	}

	// height of current picture
	public int height() {
		return pic.height();
	}

	// energy of pixel at column x and row y
	public double energy(int x, int y) {
		return picEnergy[x][y];
	}

	// sequence of indices for horizontal seam
	// public   int[] findHorizontalSeam()   { // sequence of indices for vertical seam
 //           int m = 0;
 //           int[] temparr = new int[width()];
 //           int[] arr = new int[width()];
 //          double sum = Double.POSITIVE_INFINITY;
 //           for(int n = 1; n < height()-1; n++){
 //               // System.out.println("for "+n);
 //               int i = m;
 //               int j = n;
 //               double tempsum = 0;
 //               while(i < width()-1){
 //                   // System.out.println("i "+i +"j "+j);
 //                   temparr[i] = j;
 //                   // System.out.println(Arrays.toString(temparr));
 //                   double left = picEnergy[i+1][j-1];
 //                   double middle = picEnergy[i+1][j];
 //                   double ri = picEnergy[i+1][j+1];
 //                   // System.out.println("left "+left+" middle "+middle+" right "+ri);
 //                   if(left <= middle && left <= ri){
 //                       j = j-1;
 //                       tempsum += left;
 //                   }else if(middle < ri){
 //                       // System.out.println("deepak1");
 //                       tempsum += middle;
 //                   }else{
 //                       // System.out.println("deepak2");
 //                       j = j+1;
 //                       tempsum += ri;
 //                   }
 //                   i++;
 //               }
 //               temparr[width()-1] = j;
 //               if(tempsum < sum){
 //                   sum = tempsum;
 //                   arr = Arrays.copyOf(temparr,temparr.length);
 //               }
 //           }
 //           // System.out.println(Arrays.toString(arr) + "   deepak");

 //           arr[0] = arr[1];
 //           if(width()-2 !=0)
 //               arr[width()-1] = arr[width()-2]-1;

 //           return arr;
 //       }
	//sequence of indices for horizontal seam
	 public   int[] findHorizontalSeam()   {


	}
	// sequence of indices for vertical seam
	public int[] findVerticalSeam() {
		return new int[0];
	}

	// remove horizontal seam from current picture
	public void removeHorizontalSeam(int[] seam) {

	}

	// remove vertical seam from current picture
	public void removeVerticalSeam(int[] seam) {

	}
}