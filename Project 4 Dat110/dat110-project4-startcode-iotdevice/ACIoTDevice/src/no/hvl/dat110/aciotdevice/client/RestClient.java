package no.hvl.dat110.aciotdevice.client;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
public class RestClient {

	public RestClient() {
		// TODO Auto-generated constructor stub
	}

	private static String logpath = "/accessdevice/log";

	public void doPostAccessEntry(String message) {

		 
        String URL = "http://localhost:8080/" + logpath;
        OkHttpClient client = new OkHttpClient();

         RequestBody formBody = new FormBody.Builder().add(message, URL).build();
                   

        Request request = new Request.Builder()
                     .url(URL)
                     .post(formBody)
                     .build();

         Call call = client.newCall(request);
         try {
			Response response = call.execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
		
	
	
	private static String codepath = "/accessdevice/code";
	
	public AccessCode doGetAccessCode() {

		AccessCode code = new AccessCode();

        // TODO: implement a HTTP GET on the service to get current access code

        String URL = "http://localhost:8080/" + codepath;
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
          .url(URL)
          .get()
          .build();

        System.out.println(request.toString());

        try (Response response = client.newCall(request).execute()) {
              System.out.println (response.body().string());
              String[] string = response.body().toString().replaceAll("\\[", "")
                      .replaceAll("]", "")
                      .split(",");
              int[] codearray = new int[string.length];
              
              for (int i = 0; i < string.length; i++) {
            	  codearray[i] = Integer.valueOf(string[i]);
              }
              code.setAccesscode(codearray);

            }
        
       catch (IOException e) {
           e.printStackTrace();
       }


        return code;
	}
}
