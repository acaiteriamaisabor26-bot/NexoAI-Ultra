package com.nexoai.ultra;

import android.content.Context;
import org.json.JSONObject;
import java.io.*;import java.net.*;import java.nio.charset.StandardCharsets;

public final class ApiClient {
    public interface Callback { void ok(String text); void fail(String error); }
    public static void generate(Context c,String mode,String input,String context,Callback cb){
        new Thread(()->{
            try{
                URL u=new URL(Prefs.backend(c)); HttpURLConnection h=(HttpURLConnection)u.openConnection(); h.setRequestMethod("POST"); h.setConnectTimeout(15000); h.setReadTimeout(45000); h.setDoOutput(true); h.setRequestProperty("Content-Type","application/json; charset=utf-8"); h.setRequestProperty("Authorization","Bearer "+Prefs.token(c));
                JSONObject j=new JSONObject(); j.put("mode",mode); j.put("text",input); j.put("context",context==null?"":context); j.put("style",Prefs.style(c));
                try(OutputStream os=h.getOutputStream()){ os.write(j.toString().getBytes(StandardCharsets.UTF_8)); }
                int code=h.getResponseCode(); InputStream is=code>=200&&code<300?h.getInputStream():h.getErrorStream(); String body=read(is); JSONObject r=new JSONObject(body);
                if(code>=200&&code<300 && r.optBoolean("ok")) cb.ok(r.optString("text")); else cb.fail(r.optString("error","Erro HTTP "+code));
            }catch(Exception e){ cb.fail(e.getMessage()==null?e.toString():e.getMessage()); }
        }).start();
    }
    private static String read(InputStream in)throws Exception{ BufferedReader br=new BufferedReader(new InputStreamReader(in,StandardCharsets.UTF_8)); StringBuilder s=new StringBuilder(); String l; while((l=br.readLine())!=null)s.append(l); return s.toString(); }
}
