package rode1lift.ashwin.uomtrust.mu.rod1lift.Adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import rode1lift.ashwin.uomtrust.mu.rod1lift.R;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;

/**
 * Created by Ashwin on 12-Jul-17.
 */

public class PhotoViewPagerAdapter extends PagerAdapter {

    private Context context;
    private List<byte []> pictures;

    public PhotoViewPagerAdapter(Context context, List<byte []> pictures) {
        this.context = context;
        this.pictures = pictures;
    }


    @Override
    public int getCount() {
        if(pictures != null && pictures.size() >0) {
            return pictures.size();
        }

        return 1;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.view_pager_photo, collection, false);

        ImageView imageView = (ImageView)layout.findViewById(R.id.imgCarPager);

        try {

            if(pictures.size() == 0){
                imageView.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.logo_background));
            }
            else {
                imageView.setImageBitmap(Utils.convertBlobToBitmap(pictures.get((position))));
            }

            collection.addView(layout);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == object;
    }

    public List<byte[]> getPictures() {
        return pictures;
    }

    public void setPictures(List<byte[]> pictures) {
        this.pictures = pictures;
    }
}
