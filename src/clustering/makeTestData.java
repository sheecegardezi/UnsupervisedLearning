package clustering;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class makeTestData {
	
	String catagoryClass="family";
	String[] catagoriesNames = { "sheece", "raiha", "tahir", "sajida" };
	int noOfDocumetsPerClass=5;

	public makeTestData() throws IOException {
		
		File dir = new File("data/"+catagoryClass);
		dir.mkdir();
		
		for (String catagoriesName : catagoriesNames) {

			dir = new File("data/"+catagoryClass+"/"+catagoriesName);
			dir.mkdir();
			
			for(int i=0;i<noOfDocumetsPerClass;i++){

				try (Writer writer = new BufferedWriter(
						new OutputStreamWriter(new FileOutputStream("data/"+catagoryClass+"/"+catagoriesName+"/"+catagoriesName+i+".txt"), "utf-8"))) {
					writer.write(catagoriesName);
				}	
			}

		}
	}

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		makeTestData swc = new makeTestData();
		System.out.println("Directories Made!");
}
}