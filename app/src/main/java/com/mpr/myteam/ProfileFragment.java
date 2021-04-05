package com.mpr.myteam;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


public class ProfileFragment extends Fragment {
    private TextView tvName;
    private TextView tvEmail;
    ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        imageView = view.findViewById(R.id.imageView);
        tvName = view.findViewById(R.id.tvName);
        tvEmail = view.findViewById(R.id.tvEmail);
        RestLoader task1 = new RestLoader();
        String url = getArguments().getString("PROFILE");
        task1.execute(url);

        String imageUrl = getArguments().getString("AVATAR");
        ImageLoader task2 = new ImageLoader();
        task2.execute(imageUrl);
        return view;
    }

    public class RestLoader extends AsyncTask<String, Void, String> {
        URL url;
        HttpURLConnection urlConnection;
        @Override
        protected String doInBackground(String... strings) {
            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                InputStream is = urlConnection.getInputStream();

                Scanner sc = new Scanner(is);
                StringBuilder result = new StringBuilder();
                String line;
                while(sc.hasNextLine()) {
                    line = sc.nextLine();
                    result.append(line);
                }

                return result.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(result == null){
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                JSONObject root = new JSONObject(result);
                String name = root.getString("name");
                String email = root.getString("email");
                tvName.setText(name);
                tvEmail.setText(email);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public class ImageLoader extends AsyncTask<String, Void, Bitmap> {
        URL url;
        HttpURLConnection urlConnection;
        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();

                InputStream is = urlConnection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                return bitmap;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }
    }
}