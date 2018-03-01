package com.fuze.takehome.jaxrs.json;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class JsonEncoder
{
	private static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
	
	public static void toJson(Object entity, Class<?> type, OutputStream entityStream) throws IOException 
	{
		JsonWriter writer = new JsonWriter(new OutputStreamWriter(entityStream, "UTF-8"));
		gson.toJson(entity, type, writer);
		writer.flush();
	}
	
	public static Object fromJson(Class<Object> type, InputStream entityStream) throws IOException
	{
		JsonReader reader = new JsonReader(new InputStreamReader(entityStream));
		return gson.fromJson(reader, type);
	}
}
