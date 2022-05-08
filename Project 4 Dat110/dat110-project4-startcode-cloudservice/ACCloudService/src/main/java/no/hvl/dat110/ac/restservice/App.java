package no.hvl.dat110.ac.restservice;

import static spark.Spark.after;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.put;

import java.util.Arrays;
import java.util.List;

import static spark.Spark.post;
import static spark.Spark.delete;

import com.google.gson.Gson;

import spark.Route;

/**
 * Hello world!
 *
 */
public class App {
	
	static AccessLog accesslog = null;
	static AccessCode accesscode = null;
	
	public static void main(String[] args) {

		if (args.length > 0) {
			port(Integer.parseInt(args[0]));
		} else {
			port(8080);
		}

		// objects for data stored in the service
		
		accesslog = new AccessLog();
		accesscode  = new AccessCode();
		
		after((req, res) -> {
  		  res.type("application/json");
  		});
		
		// for basic testing purposes
		get("/accessdevice/hello", (req, res) -> {
			
		 	Gson gson = new Gson();
		 	
		 	return gson.toJson("IoT Access Control Device");
		});
		

		
		
		
		// TODO: implement the routes required for the access control service
		// as per the HTTP/REST operations describined in the project description
		post("/accessdevice/log/", (req, res) -> {
			String request = req.body().toString() ;
			accesslog.add(request);
			Gson gson = new Gson();
			return gson.toJson(request);
			
		});
		
		get("/accessdevice/log/", (req,res) -> {
			String sres = res.body();

			sres = accesslog.toJson();
			Gson gson = new Gson();
			return gson.toJson(sres);
		});
		
		get("/accessdevice/log/{id}", (req,res) ->{
			String sreq = req.toString();
			Integer id = Integer.parseInt(sreq);
			String slog = accesslog.get(id).toString();
			Gson gson = new Gson();
			return gson.toJson(slog);
			
		});
		
		put("/accessdevice/code", (req,res) ->{
			Gson gson = new Gson();
			
			String sreq = req.body();
			sreq = sreq.replaceAll("[^0-9]+", " ");
			
			return  gson.toJson(Arrays.asList(sreq.trim().split(" ")));
		});
		
		delete("/accessdevice/log", (req,res) ->{
			accesslog.clear();
			
			String slog = accesslog.toJson();

			
			return   req.body().concat(slog);
		});
    }

    
}
