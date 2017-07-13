package rode1lift.ashwin.uomtrust.mu.rod1lift.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import rode1lift.ashwin.uomtrust.mu.rod1lift.R;

/**
 * Created by Ashwin on 12-Jul-17.
 */

public class PhotoViewPagerAdapter extends PagerAdapter {

    private Context context;

    public PhotoViewPagerAdapter(Context context) {
        this.context = context;
    }


    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.view_pager_photo, collection, false);

        ImageView imageView = (ImageView)layout.findViewById(R.id.imgCarPager);

        try {
            if (position == 0)
                imageView.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_bin_close_red));
            else if (position == 1)
                imageView.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_bin_open_red));
            else
                imageView.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_tick_blue));

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

}
