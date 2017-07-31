package rode1lift.ashwin.uomtrust.mu.rod1lift.Firebase;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;



public class PushNotifictionHelper {
	public final static String AUTH_KEY_FCM = "AIzaSyDGvGxPJAR2Hw1FQO_0kj8-7yNg2qJQKBQ";
	public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";

	public static void sendPushNotification() {

        String deviceToken ="feGEmenCusk:APA91bHZqnAppsf9M9HqGTBojN71JiQezJUTFfXqHlmqad1Qj7LRGNitm83QYPf430bOm1QNvECIwmCgTWIOhBnFRZABEziRYKWAbl5PzJenr3BovZsqTIonVUWYCKAmqua4xJ6bqtft";
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
	    info.put("title", "notification title"); // Notification title
	    info.put("body", "message body"); // Notification
	    json.put("notification", info);
	    
        OutputStreamWriter wr = new OutputStreamWriter(
                conn.getOutputStream());
        wr.write(json.toString());
        wr.flush();

        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));

        String output;
        System.out.println("Output from Server .... \n");
        while ((output = br.readLine()) != null) {
            System.out.println(output);
        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    System.out.println("GCM Notification is sent successfully");
	}
}