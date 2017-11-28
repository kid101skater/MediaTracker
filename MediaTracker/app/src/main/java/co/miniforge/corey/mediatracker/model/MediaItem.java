package co.miniforge.corey.mediatracker.model;

import android.util.Log;

import org.json.JSONObject;

import java.security.MessageDigest;

import co.miniforge.corey.mediatracker.media_store.Md5IdHelper;

/**
 * Created by corey on 10/20/17.
 */

public class MediaItem {

    public String id;
    public String title;
    public String description;
    public String url;
    

    public MediaItem(JSONObject jsonObject){
        try{
            //Generate id based on the object instance (should work :D)
            this.id = jsonObject.getString("id");
            this.title = jsonObject.getString("title");
            this.description = jsonObject.getString("description");
            this.url = jsonObject.getString("url");
        } catch (Exception e){
            Log.e("toJSONError", String.format("There was an error: %s", e.getMessage()));
        }
    }

    public MediaItem(){
        this.id = Md5IdHelper.idForObject(this);
        this.title = "defaultTitle";
        this.description = "defaultDescription";
        this.url = "defaultUrl";
    }

    public JSONObject toJson(){
        JSONObject mediaItem = new JSONObject();

        try{
            mediaItem.put("id", this.id);
            mediaItem.put("title", this.title);
            mediaItem.put("description", this.description);
            mediaItem.put("url", this.url);
        } catch (Exception e){
            Log.e("toJSONError", String.format("There was an error: %s", e.getMessage()));
        }

        return mediaItem;
    }
}
