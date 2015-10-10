package com.tdp2grupo9.modelo.publicacion;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.tdp2grupo9.utils.Connection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.JsonReader;
import android.util.Log;

public class Imagen {
	
	private static final String LOG_TAG = "BSH.Imagen";
	
	private int id;
	private byte[] img;
	private Integer publicacionId;
	
	public Imagen() {
		this.id = 0;
	}
	
    public static Bitmap bitmapFromBytes(byte[] imagen) {
        return BitmapFactory.decodeByteArray(imagen, 0, imagen.length);
    }

    public static String base64URL_SAFEFromBytes(byte[] imagen){
        return Base64.encodeToString(imagen, Base64.URL_SAFE);
    }
    
    public static String base64DEFAULTFromBytes(byte[] imagen){
        return Base64.encodeToString(imagen, Base64.DEFAULT);
    }

    public static byte[] bytesFromBase64DEFAULT(String base64){
        return Base64.decode(base64,Base64.DEFAULT);
    }
    
    public static byte[] bytesFromBase64URL_SAFE(String base64){
        return Base64.decode(base64,Base64.URL_SAFE);
    }
    
    public static byte[] bytesFromBitmap(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    public static Bitmap resizeDefault(Bitmap bmp) {
    	return Bitmap.createScaledBitmap(bmp, 200, 200, false);
    }
        
	public static List<Imagen> getImagenesfromJson(JsonReader reader) throws IOException, JSONException {
        List<Imagen> imagenes = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
        	Imagen img = new Imagen();
            img.jsonToImagen(reader);
            imagenes.add(img);
        }
        reader.endArray();
        return imagenes;
	}

	private void jsonToImagen(JsonReader reader) throws JSONException, IOException {
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "id":
                    this.setId(reader.nextInt());
                    break;
                case "base64":
                	//URL SAFE
                	String base64 = reader.nextString();
                    this.setImg(Imagen.bytesFromBase64URL_SAFE(base64));
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
    }
	
    public void guardarImagen(String token) {
        String METHOD = "guardarImagen";

        HttpURLConnection urlConnection = null;
        try {
        	String content = "token=" + token + "&publicacion=" + this.publicacionId + "&base64=" + Imagen.base64URL_SAFEFromBytes(this.img);

            System.out.println(content);
            
            urlConnection = Connection.getHttpUrlConnection("foto");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write(content);
            out.close();
            


            int HttpResult = urlConnection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_CREATED) {
                this.jsonToImagen(new JsonReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8")));
                Log.d(LOG_TAG, METHOD + " imagen guardada id " + this.id);
            } else {
            	System.out.println("RESPUESTA NO ESPERADA " + HttpResult);
            	System.out.println(urlConnection.getResponseMessage());
                Log.w(LOG_TAG, METHOD + " respuesta no esperada " + urlConnection.getResponseMessage());
            }
        } catch (IOException | JSONException e) {
            Log.e(LOG_TAG, METHOD + " ERROR ", e);
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
    }

    public void resizeDefault() {
    	Bitmap bmp = Imagen.bitmapFromBytes(this.img);
    	this.setImg(Imagen.bytesFromBitmap(Bitmap.createScaledBitmap(bmp, 200, 200, false)));
    }
    
	public void setId(int id) {
		this.id = id;
	}
	
	public void setPublicacionId(Integer publicacionId) {
		this.publicacionId = publicacionId;
	}
	
	public byte[] getImg() {
		return this.img;
	}
		
	public void setImg(byte[] img) {
		this.img = img;
	}
}
