package com.example.nasa_image_of_the_day_search;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileInputStream;

public class ImageHistoryFragment extends Fragment {
    private Bundle dataFromActivity;
    private AppCompatActivity parentActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dataFromActivity = getArguments();
        String fileName = dataFromActivity.getString("fileName");
        String fileDir = dataFromActivity.getString("fileDir");

        // Inflate the layout for this fragment
        View result =  inflater.inflate(R.layout.image_history_fragment, container, false);

        try {
            File file = new File(fileDir, fileName);
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            ImageView imageView = result.findViewById(R.id.imageView);
            imageView.setImageBitmap(bitmap);
            Log.i("got here", "");
        }
        catch(Exception e) {
            e.printStackTrace();
        }





        return result;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //context will either be FragmentExample for a tablet, or EmptyActivity for phone
        parentActivity = (AppCompatActivity)context;
    }
}
