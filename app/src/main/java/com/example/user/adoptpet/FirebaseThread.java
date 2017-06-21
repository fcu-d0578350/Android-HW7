package com.example.user.adoptpet;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/6/6.
 */

class FirebaseThread extends Thread{

    private DataSnapshot dataSnapshot;

    public FirebaseThread(DataSnapshot dataSnapshot){
        this.dataSnapshot = dataSnapshot;
    }

    public void ren(){
        List<Pet> lsPets = new ArrayList<>();
        for (DataSnapshot ds : dataSnapshot.getChildren()){

            DataSnapshot dsSName = ds.child("shelter_name");
            DataSnapshot dsAKind = ds.child("animal_kind");

            String shelterName = (String)dsSName.getValue();
            String kind = (String)dsAKind.getValue();

            DataSnapshot dsImg = ds.child("album_file");
            String imgUrl = (String) dsImg.getValue();
            Bitmap petImg = getImgBitmap(imgUrl);

            Pet aPet = new Pet();
            aPet.setShelter(shelterName);
            aPet.setKind(kind);
            aPet.setImgUrl(petImg);
            lsPets.add(aPet);

            Log.v("AdoptPet", shelterName + ";" + kind);
        }
    }

    private Bitmap getImgBitmap(String imgUrl){

        try{
            URL url = new URL(imgUrl);
            Bitmap bm = BitmapFactory.decodeStream(
                    url.openConnection()
                            .getInputStream());
            return bm;
        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
