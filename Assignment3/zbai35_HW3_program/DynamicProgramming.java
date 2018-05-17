import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DynamicProgramming {
    
	public static double[] maxSubArray(double[] list) {
		double[] result = new double[3];
		double maxSum = list[0];
		double[] listSum = new double[list.length];
		listSum[0] = list[0];

		for(int i=1;i<list.length;i++) {
			if(listSum[i-1] <= 0) {
				listSum[i] = list[i];				
			}else {
				listSum[i] = listSum[i-1] + list[i];				
			}						
			if(listSum[i] >= maxSum) {
				maxSum = listSum[i];
				result[1] = i + 1;
			}			
		}
		int end = (int)result[1];

		result[0] = 1;
		for(int j=1; j<=end ; j++) {
			if(listSum[j-1]<=0) {
				result[0] = j+1;
			}
		}
		
		//int start = (int)result[0];
		//System.out.println("start: "+ start + "\t" + "end: "+ end );
		result[2] = maxSum;
		return result;	
	}
	
	public static void main(String[] args) throws IOException {
		
		
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
        	for(int m = 0; m<1000; m++) {
        	long start = System.nanoTime();
        	result = maxSubArray(inputLine);
        	long finish = System.nanoTime();
        	totalTimeSum = totalTimeSum + (finish - start)/1000.0;
        	}
        	double totalTime = totalTimeSum/1000;
        	String totalSum = df.format(result[2]);
        	String time = df.format(totalTime);
        	
        	output.println(totalSum +","+"\t"+(int)result[0]+","+"\t"+(int)result[1]+","+"\t"+time);
        	//output.println(Double.toString(result[2])+","+"\t"+(int)result[0]+","+"\t"+(int)result[1]+","+"\t"+Double.toString(total));
        	//System.out.println(result[2]+","+(int)result[0]+","+(int)result[1]+","+Double.toString(total));
        }
        br.close();
		output.close();
	}
	   
}


