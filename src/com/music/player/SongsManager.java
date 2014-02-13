package com.music.player;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
 
public class SongsManager {
    // SDCard Path
    final String MEDIA_PATH = Environment.getExternalStorageDirectory().getPath();//+"/Music/";//new String("/sdcard/");
    private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
 
    Activity PlAct;
    // Constructor
    public SongsManager(Activity PlAct){
    	this.PlAct = PlAct;
    }
 
    /**
     * Function to read all mp3 files from sdcard
     * and store the details in ArrayList
     * */
    public ArrayList<HashMap<String, String>> getPlayList(){
//    	Log.d("TryToFix", MEDIA_PATH);
//        File home = new File(MEDIA_PATH);
//        Log.d("TryToFix", String.valueOf(home.isDirectory()));
//        if (home.listFiles(new FileExtensionFilter())!=null && home.listFiles(new FileExtensionFilter()).length > 0) {
//            for (File file : home.listFiles(new FileExtensionFilter())) {
//                HashMap<String, String> song = new HashMap<String, String>();
//                song.put("songTitle", file.getName().substring(0, (file.getName().length() - 4)));
//                song.put("songPath", file.getPath());
// 
//                // Adding each song to SongList
//                songsList.add(song);
//            }
//        }
    	
    	String[] STAR = { "*" };        
        Uri allsongsuri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

        Cursor cursor = PlAct.managedQuery(allsongsuri, STAR, selection, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String song_name = cursor
                            .getString(cursor
                                    .getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));

                    String fullpath = cursor.getString(cursor
                            .getColumnIndex(MediaStore.Audio.Media.DATA));



                    HashMap<String, String> song = new HashMap<String, String>();
                  song.put("songTitle", song_name);
                  song.put("songPath", fullpath);
                  songsList.add(song);

                } while (cursor.moveToNext());

            }
            //cursor.close();
        }
        
        // return songs list array
        return songsList;
    }
 
    /**
     * Class to filter files which are having .mp3 extension
     * */
    class FileExtensionFilter implements FilenameFilter {
        public boolean accept(File dir, String name) {
            return (name.endsWith(".mp3") || name.endsWith(".MP3"));
        }
    }
}