import java.awt.Color;
import java.util.*;
public class SeamCarver {
	Picture pic;
	int height;
	int width;
	Color[][] matrx;
	double[][] energy ;
   public SeamCarver(Picture picture){
   	pic = picture;
   	height = pic.height();
   	width = pic.width();
   	energy = new double[width][height];


   	for(int i =0;i< width;i++){
   		for(int j =0 ;j<height;j++){
   			matrx[i][j]= pic.get(i,j);
   		}
   	}


   	for(int i =0;i< width;i++){
   		for(int j =0 ;j<height;j++){
   			energy[i][j]= calEnergy(i,j);
   		}
   	}
   	findVerticalSeam();

   }
   double calEnergy(int x,int y){
   	if(x==0 || y==0|| x==width-1 || y==height-1){
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

   }              // create a seam carver object based on the given picture

    public Picture picture() {
    	return this.pic;
   }                         // current picture

   public  int width() {
   	return this.pic.width();

   }                           // width of current picture
   public int height() {
   	return this.pic.height();
   }                        // height of current picture
    public  double energy(int x, int y)  {
    	return energy[x][y];

   }
               // energy of pixel at column x and row y

   //public   int[] findHorizontalSeam()               // sequence of indices for horizontal seam
    public   int[] findVerticalSeam() {
    	int arra[] = new int[height];
    	int temArr[] = new int[height];
    	double sum=Double.POSITIVE_INFINITY;
    	double tempSum=0.0;
    	for(int k=2;k<width-2;k++){
	    	int i =0 ;
	    	int j =k;
	    	while(i<height-1){
	    		temArr[i] = j;
	    		double left = energy[i+1][j-1];
				double middle =  energy[i+1][j];
				double right = energy[i+1][j+1];
				if(left < middle && left <right){
	    				tempSum=tempSum+left;
	    				j=j-1;
				}else if(middle < right){
	    				tempSum+=middle;
	    		}else{
	    				tempSum+=right;
	    				j=j+1;
	    		}
	    		i++;
	    	}
	    	temArr[height-1]=j;
	    	if(sum<tempSum){
	    			sum = tempSum;
	    			arra=Arrays.copyOf(temArr,temArr.length);
	    	}
    	}
    	System.out.println(Arrays.toString(arra));
    	return arra;

   }                // sequence of indices for vertical seam
   // public    void removeHorizontalSeam(int[] seam)   // remove horizontal seam from current picture
   // public    void removeVerticalSeam(int[] seam)     // remove vertical seam from current picture
   public static void main(String[] args) {
   	Scanner sc = new Scanner(System.in);
   	String inputFile = sc.nextLine();
   	Picture pic = new Picture(inputFile);
   	SeamCarver obj = new SeamCarver(pic);
   }
}
// for(int i =2 ;i<width-2;i++){
//        		for(int j=0;j<height;j++){

//     		//while(true){
//     			temArr[j] = i;
//     			int left = energy[i+1][j-1];
//     			int middle =  energy[i+1][j];
//     			int right = energy[i+1][j+1];
//     			//int res=Math.min(left,Math.min(middle,right));

//     			if(left < middle && left <right){
//     				sum=sum+left;

//     			}else if(middle < right){
//     				sum+=middle;
//     			}else{
//     				sum+=right;
//     			}


//     		}
//     	}