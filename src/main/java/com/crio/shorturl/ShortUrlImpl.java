package com.crio.shorturl;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.crio.shorturl.ShortUrl;

public class ShortUrlImpl implements ShortUrl{
    private HashMap<String,String> m;
    private HashMap<String,Integer> countMap;
    private String domain;
    
    public ShortUrlImpl() {
        m= new HashMap<String,String>();
        countMap=new HashMap<String,Integer>();
        domain="http://short.url/";
    }
    

    @Override
    public String registerNewUrl(String longUrl) {

        //longUrl=cleanup(longUrl);
        if(m.get(longUrl)==null){
            String kkey=getkey();
            String chotaUrl=domain+kkey;
            m.put(longUrl,chotaUrl);
            return chotaUrl;
        }
        else{
            return m.get(longUrl);
        }
        
    }

    //this function gives a unique of a particular longUrl
    private String getkey() {
        final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom rnd = new SecureRandom();


            StringBuilder sb = new StringBuilder(9);
            for(int i = 0; i < 9; i++)
                sb.append(AB.charAt(rnd.nextInt(AB.length())));
            return (sb.toString());
        
    }

    // cleanup method to filter out any thing like "http://","https://" and url
    // ending with a "/" for eg "https://www.google.com/"
    // private String cleanup(String url) {
    //     if(url.substring(0,7)=="http://") url=url.substring(7);
    //     if(url.substring(0,8)=="https://") url=url.substring(8);
    //     if(url.charAt(url.length()-1)=='/') url=url.substring(0,url.length()-1);   
    //     return url;
    // }

    @Override
    public String registerNewUrl(String longUrl, String shortUrl){
        if(m.containsValue(shortUrl)==true) return null;
        //longUrl=cleanup(longUrl);
        if(m.containsKey(longUrl)==true){
            return null;
        }
        else{
            m.put(longUrl, shortUrl);
            return shortUrl;
        }
    }

    @Override
    public String getUrl(String shortUrl) {
        if(m.containsValue(shortUrl)==true)
        for (Map.Entry e : m.entrySet()) {
            if (e.getValue() == shortUrl){
                String longUrl=(String) e.getKey();
                if(countMap.containsKey(longUrl)==false){
                    countMap.put(longUrl,0);
                }
                countMap.put(longUrl, countMap.get(longUrl)+1);

                return longUrl;
            }
        }
        return null;
    }

    @Override
    public Integer getHitCount(String longUrl){
        if(countMap.containsKey(longUrl)==true)
        return countMap.get(longUrl);
        else return 0;
    }

    @Override
    public String delete(String longUrl){
        if(m.containsKey(longUrl)==true){
            Iterator<Map.Entry<String,String>> itr= m.entrySet().iterator();

            while(itr.hasNext()){
                Map.Entry e=itr.next();
                if(e.getKey()==longUrl) {
                    m.remove(e.getKey());
                    return "deleted";
                }

            }
        }
        return null;
    }

    


    

}