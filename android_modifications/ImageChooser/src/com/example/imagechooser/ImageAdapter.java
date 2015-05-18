package com.example.imagechooser;

 
import java.util.Random;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Checkable;
import android.widget.GridView;
import android.widget.ImageView;
 
public class ImageAdapter extends BaseAdapter{
    private Context mContext;
    
    private final int[] CHECKED_STATE_SET = { android.R.attr.state_checked};
    public boolean[] selected = new boolean [105];
 
    public Integer[] mThumbIds = {R.drawable.p_000, 
        R.drawable.p_001, R.drawable.p_002, R.drawable.p_003, R.drawable.p_004, R.drawable.p_005, 
        R.drawable.p_006, R.drawable.p_007, R.drawable.p_008, R.drawable.p_009, R.drawable.p_010, 
        R.drawable.p_011, R.drawable.p_012, R.drawable.p_013, R.drawable.p_014, R.drawable.p_015, 
        R.drawable.p_016, R.drawable.p_017, R.drawable.p_018, R.drawable.p_019, R.drawable.p_020, 
        R.drawable.p_021, R.drawable.p_022, R.drawable.p_023, R.drawable.p_024, R.drawable.p_025, 
        R.drawable.p_026, R.drawable.p_027, R.drawable.p_028, R.drawable.p_029, R.drawable.p_030, 
        R.drawable.p_031, R.drawable.p_032, R.drawable.p_033, R.drawable.p_034, R.drawable.p_035, 
        R.drawable.p_036, R.drawable.p_037, R.drawable.p_038, R.drawable.p_039, R.drawable.p_040, 
        R.drawable.p_041, R.drawable.p_042, R.drawable.p_043, R.drawable.p_044, R.drawable.p_045, 
        R.drawable.p_046, R.drawable.p_047, R.drawable.p_048, R.drawable.p_049, R.drawable.p_050, 
        R.drawable.p_051, R.drawable.p_052, R.drawable.p_053, R.drawable.p_054, R.drawable.p_055, 
        R.drawable.p_056, R.drawable.p_057, R.drawable.p_058, R.drawable.p_059, R.drawable.p_060, 
        R.drawable.p_061, R.drawable.p_062, R.drawable.p_063, R.drawable.p_064, R.drawable.p_065, 
        R.drawable.p_066, R.drawable.p_067, R.drawable.p_068, R.drawable.p_069, R.drawable.p_070, 
        R.drawable.p_071, R.drawable.p_072, R.drawable.p_073, R.drawable.p_074, R.drawable.p_075, 
        R.drawable.p_076, R.drawable.p_077, R.drawable.p_078, R.drawable.p_079, R.drawable.p_080, 
        R.drawable.p_081, R.drawable.p_082, R.drawable.p_083, R.drawable.p_084, R.drawable.p_085, 
        R.drawable.p_086, R.drawable.p_087, R.drawable.p_088, R.drawable.p_089, R.drawable.p_090, 
        R.drawable.p_091, R.drawable.p_092, R.drawable.p_093, R.drawable.p_094, R.drawable.p_095, 
        R.drawable.p_096, R.drawable.p_097, R.drawable.p_098, R.drawable.p_099, R.drawable.p_100, 
        R.drawable.p_101, R.drawable.p_102, R.drawable.p_103, R.drawable.p_104
    };

 
    // Constructor
    public ImageAdapter(Context c){
        mContext = c;
        shuffleArray(mThumbIds);
    }
 
    @Override
    public int getCount() {
        return 20;
    }
 
    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }
 
    @Override
    public long getItemId(int position) {
        return 0;
    }
 
    @SuppressLint("NewApi")
	@Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
    	ImageView imageView = new ImageView(mContext);
    	        imageView.setScaleType(ImageView.ScaleType.CENTER);
        imageView.setImageResource(mThumbIds[position]);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, 130));
        
        return imageView;
    } 
    
    
    static void shuffleArray(Integer[] ar)
    {
      Random rnd = new Random();
      for (int i = ar.length - 1; i > 0; i--)
      {
        int index = rnd.nextInt(i + 1);
        int a = ar[index];
        ar[index] = ar[i];
        ar[i] = a;
      }
    }
}


