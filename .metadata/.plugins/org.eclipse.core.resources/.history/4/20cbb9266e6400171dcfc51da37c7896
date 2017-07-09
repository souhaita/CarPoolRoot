package shashi.uomtrust.ac.mu.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.tomcat.util.codec.binary.Base64;

import shashi.uomtrust.ac.mu.dto.CarDetailsDTO;



public class Utils {

	private static final String imageDirectory= "D:/UOM/Dissertation/pic/";
	
	
	public static void saveImageToServer(CarDetailsDTO carDetailsDTO){
		try {
			
			String filePath = Utils.imageDirectory + String.valueOf(carDetailsDTO.getAccountId())+"/";
			new File(filePath).mkdirs();
			
			
			if(carDetailsDTO.getsPicture1() != null){
				byte [] pic1 = Base64.decodeBase64(carDetailsDTO.getsPicture1());
				InputStream in1 = new ByteArrayInputStream(pic1);					
				BufferedImage bImageFromConvert1 = ImageIO.read(in1);
				
				String imageName = "1.jpg";
				String output = filePath + imageName;
				ImageIO.write(bImageFromConvert1, "jpg", new File(output));
			}
			
			if(carDetailsDTO.getsPicture2() != null){
				byte [] pic2 = Base64.decodeBase64(carDetailsDTO.getsPicture2());
				InputStream in2 = new ByteArrayInputStream(pic2);					
				BufferedImage bImageFromConvert2 = ImageIO.read(in2);
				String imageName = "2.jpg";
				String output = filePath + imageName;
				ImageIO.write(bImageFromConvert2, "jpg", new File(output));
			}
			
			if(carDetailsDTO.getsPicture3() != null){
				byte [] pic3 = Base64.decodeBase64(carDetailsDTO.getsPicture3());
				InputStream in3 = new ByteArrayInputStream(pic3);					
				BufferedImage bImageFromConvert3 = ImageIO.read(in3);
				String imageName = "3.jpg";
				String output = filePath + imageName;
				ImageIO.write(bImageFromConvert3, "jpg", new File(output));
			}
			
			if(carDetailsDTO.getsPicture4() != null){
				byte [] pic4 = Base64.decodeBase64(carDetailsDTO.getsPicture4());
				InputStream in4 = new ByteArrayInputStream(pic4);					
				BufferedImage bImageFromConvert4 = ImageIO.read(in4);
				String imageName = "4.jpg";
				String output = filePath + imageName;
				ImageIO.write(bImageFromConvert4, "jpg", new File(output));
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static CarDetailsDTO getImage(CarDetailsDTO carDetailsDTO){
		
		String filePath = Utils.imageDirectory + String.valueOf(carDetailsDTO.getAccountId())+"/";
		String pic1 = "1.jpg";
		String pic2 = "2.jpg";
		String pic3 = "3.jpg";
		String pic4 = "4.jpg";

		
		try {
			File file1 = new File(filePath+pic1);
			if(file1.exists()){
				byte[] bytesArray = new byte[(int) file1.length()];
	
				FileInputStream fis = new FileInputStream(file1);
				fis.read(bytesArray);
				fis.close();
				carDetailsDTO.setsPicture1(Base64.encodeBase64String(bytesArray));
			}
			
			File file2 = new File(filePath+pic2);
			if(file2.exists()){
				byte[] bytesArray = new byte[(int) file2.length()];
	
				FileInputStream fis = new FileInputStream(file2);
				fis.read(bytesArray);
				fis.close();
				carDetailsDTO.setsPicture2(Base64.encodeBase64String(bytesArray));
			}
			
			File file3 = new File(filePath+pic3);
			if(file3.exists()){
				byte[] bytesArray = new byte[(int) file3.length()];
	
				FileInputStream fis = new FileInputStream(file3);
				fis.read(bytesArray);
				fis.close();
				carDetailsDTO.setsPicture3(Base64.encodeBase64String(bytesArray));
			}
			
			File file4 = new File(filePath+pic4);
			if(file4.exists()){
				byte[] bytesArray = new byte[(int) file4.length()];
	
				FileInputStream fis = new FileInputStream(file4);
				fis.read(bytesArray);
				fis.close();
				carDetailsDTO.setsPicture4(Base64.encodeBase64String(bytesArray));
			}
			
			
		} catch (Exception e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}

		return carDetailsDTO;
	}

}
