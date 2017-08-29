package com.druidzworks.geetobitan.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Arrays;
import java.util.Random;

import com.druidzworks.geetobitan.R;

/**
 * Created by Saibal Ghosh on 8/26/2017.
 */

public class DefaultFragment extends Fragment {
    ImageView ivMain;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.default_splash, null);
        ivMain = (ImageView) v.findViewById(R.id.imgMain);

        String fileName = getFileName(getRandomFileNumber());
        if(fileName.length() > 4)
        {
            fileName = "i108";
        }
        ivMain.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ivMain.setImageResource(getResourceID(fileName, "drawable", getContext()));


        ivMain.requestLayout();

        return v;
    }

    protected final static int getResourceID(final String resName, final String resType, final Context ctx)
    {
        final int ResourceID =
                ctx.getResources().getIdentifier(resName, resType,
                        ctx.getApplicationInfo().packageName);
        if (ResourceID == 0)
        {
            throw new IllegalArgumentException
                    (
                            "No resource string found with name " + resName
                    );
        }
        else
        {
            return ResourceID;
        }
    }

    private int getRandomFileNumber()
    {
        Random rand = new Random();
        int imgNum;
        int[] missing = {104, 105, 211, 232, 288, 289, 343, 378};
        do {
            imgNum = rand.nextInt(455) + 1;
        }while(isPresent(missing, imgNum));
        return imgNum;
    }

    private boolean isPresent(int[] haystack, final int needle)
    {
        int result = Arrays.binarySearch(haystack, needle);
        return (result > 0);
    }

    private String getFileName(int fileNumber)
    {
        int digits = numDigits(fileNumber);
        StringBuilder sbFileName = new StringBuilder();
        switch (digits)
        {
            case(1):
            {
                sbFileName.append("00" + String.valueOf(fileNumber));
            }
            case(2):
            {
                sbFileName.append("0" + String.valueOf(fileNumber));
            }
            case(3):
            {
                sbFileName.append(String.valueOf(fileNumber));
            }
        }

        return "i" + sbFileName.toString();
    }

    private int numDigits(int num)
    {
        if(num < 10)
            return 1;
        else if(num >= 10 && num < 100)
            return 2;
        else
            return 3;
    }
}