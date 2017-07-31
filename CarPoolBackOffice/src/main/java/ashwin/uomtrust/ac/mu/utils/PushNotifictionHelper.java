package ashwin.uomtrust.ac.mu.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;



public class PushNotifictionHelper {
	public final static String AUTH_KEY_FCM = "AAAArzjy1tI:APA91bGJiRKqsqUQjlFhTq84FuRZP1BfH3X9WHS6j4Hu0MJQDwo8LcwVub-tx0fYJY-qVgGp499x8g4e6nfShnsPGmO-OoCaE9tilqwdloZma-k_aWFsQ_DW79apPDtu5c-vsK7Muj5D";
	public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";

	public static void sendPushNotification(String deviceToken, String title, String message) {
		try{
		    URL url = new URL(API_URL_FCM);
		    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	
		    conn.setUseCaches(false);
		    conn.setDoInput(true);
		    conn.setDoOutput(true);
	
		    conn.setRequestMethod("POST");
		    conn.setRequestProperty("Authorization", "key=" + AUTH_KEY_FCM);
		    conn.setRequestProperty("Content-Type", "application/json");
	
		    JSONObject json = new JSONObject();
	
		    json.put("to", deviceToken.trim());
		    JSONObject info = new JSONObject();
		    info.put("title", title);
		    info.put("body", message);
		    info.put("message", message);
		    json.put("notification", info);
		    
	        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	        wr.write(json.toString());
	        wr.flush();
	
	        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
	
	        String output;
	        while ((output = br.readLine()) != null) {
	            System.out.println(output);
	        }
	    } catch (Exception e) {
		    System.out.println("Error");
	        e.printStackTrace();
	    }
	}
}