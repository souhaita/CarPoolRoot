package rode1lift.ashwin.uomtrust.mu.rod1lift.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

import rode1lift.ashwin.uomtrust.mu.rod1lift.R;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;

/**
 * Created by Ashwin on 16-Jul-17.
 */

public class PassengerHistoryGridAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    private List<byte []> carImages;

    public PassengerHistoryGridAdapter(Context context, List<byte []> carImages) {
        this.carImages = carImages;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (carImages != null && carImages.size() > 0)
            return carImages.size();
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.activity_passenger_view_history_content_sub_content, null);

        byte [] carImage = carImages.get(i);

        ImageView imageView = (ImageView) view.findViewById(R.id.imgCar);

        if(carImage != null)
            imageView.setImageBitmap(Utils.convertBlobToBitmap(carImage));

        return view;
    }
}