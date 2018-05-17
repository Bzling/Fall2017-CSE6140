import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.text.DecimalFormat;

public class DivideConquer {
	static class SubArray{
		
		private int start;
		private int end;
		private double sum;
		public SubArray(int start, int end, double sum) {
			super();
			this.start = start;
			this.end = end;
			this.sum = sum;
		}
		
		public int getStart() {
			return start;
		}		
		public void setStart(int start) {
			this.start = start;
		}		
		public int getEnd() {
			return end;
		}		
		public void setEnd(int end) {
			this.end = end;
		}		
		public double getSum() {
			return sum;
		}		
		public void setSum(double sum) {
			this.sum = sum;
		}
		public double[] getResult(){
			double[] result = new double [3];
			result[0] = sum;
			result[1] = start+1;
			result[2] = end+1;			
			return result;
		}
		
	}
	public static SubArray maxSubarray(double[] list, int left, int right) {
		if(left == right) {
			return new SubArray(left, right, list[left]);			
		}else {
			int middle = (int)Math.floor((left + right)/2);
			SubArray leftArray = maxSubarray(list, left, middle);
			SubArray rightArray = maxSubarray(list, middle+1, right);
			SubArray middleArray = maxMidarray(list, left, middle, right);
			if(leftArray.getSum() >= rightArray.getSum() && leftArray.getSum() >= middleArray.getSum()) {
				return leftArray;
			}else if(rightArray.getSum() >= leftArray.getSum() && rightArray.getSum() >= middleArray.getSum()){
				return rightArray;
			}else {
				return middleArray;
			}
		}
		}
	
	public static SubArray maxMidarray(double[] list, int left, int middle, int right) {
		double sum = 0;
		int startPoint = left;
		int endPoint = right;
		double leftSum = Double.MIN_VALUE;
		for(int i = middle; i >= left; i--) {
			sum = sum + list[i];
			if(sum > leftSum) {
				leftSum = sum;
				startPoint = i;
			}
		}
		 sum = 0;
		 double rightSum = Double.MIN_VALUE;
		 for(int j = middle+1; j <= right; j++) {
			 sum  = sum + list[j];
			 if(sum > rightSum) {
				 rightSum = sum;
				 endPoint = j;
			 }
		 }
		 SubArray midResult = new SubArray(startPoint, endPoint, (leftSum+rightSum));
		 return midResult;
	}
	
	public static final void main(String[] args) throws NumberFormatException, IOException {
		if (args.length < 2) {
			System.err.println("Unexpected number of command line arguments");
			System.exit(1);
		}

		String data_file = args[0];
		String output_file = args[1];
		PrintWriter output;
		output = new PrintWriter(output_file, "UTF-8");

		DecimalFormat df = new DecimalFormat("0.00");
		
		File file = new File(data_file);
        Reader r = new FileReader(file);
        BufferedReader br = new BufferedReader(r);
        String line = null;
        String line1 = br.readLine();
		String[] split = line1.split(",");
		int num_instances = Integer.parseInt(split[0]);
        double[] inputLine = new double[num_instances];
        double[] result = new double[3];
        double totalTimeSum = 0;
        while((line = br.readLine()) != null) {
        	String[] str = line.split(",");
        	for(int i = 0; i<str.length; i++) {				
				inputLine[i] = Double.valueOf(str[i]);						
			}
        	for(int m=0;m<1000;m++) {
        	long start = System.nanoTime();
        	result = maxSubarray(inputLine, 0, str.length-1).getResult();
        	long finish = System.nanoTime();
        	totalTimeSum = totalTimeSum + (finish - start)/1000.0;
        	}
        	String totalSum = df.format(result[0]);
            double totalTime = totalTimeSum/1000;
        	String time = df.format(totalTime);
        	
        	output.println(totalSum+","+"\t"+(int)result[1]+","+"\t"+(int)result[2]+","+"\t"+time);

	}
        br.close();
        output.close();
}
}
