package rode1lift.ashwin.uomtrust.mu.rod1lift.Adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;

import java.util.List;

import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.AccountDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;

/**
 * Created by Ashwin on 16-Jul-17.
 */

public class UserDetailsGridAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    private Context context;
    private List<AccountDTO> accountDTOList;

    public UserDetailsGridAdapter(Context context, List<AccountDTO> accountDTOList) {

        this.context = context;
        this.accountDTOList = accountDTOList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        if (accountDTOList != null && accountDTOList.size() > 0)
            return accountDTOList.size();
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
        view = inflater.inflate(R.layout.activity_driver_view_user_details_content, null);

        final AccountDTO a = accountDTOList.get(i);

        ImageView imageView = (ImageView) view.findViewById(R.id.imgProfile);
        if (a.getProfilePicture() != null)
            imageView.setImageBitmap(Utils.convertBlobToBitmap(accountDTOList.get(i).getProfilePicture()));

        TextView txtFullName = (TextView) view.findViewById(R.id.txtFullName);
        txtFullName.setText(a.getFirstName() + " " + a.getLastName());

        FloatingActionButton fabCall = (FloatingActionButton) view.findViewById(R.id.fabCall);
        fabCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (a.getPhoneNum() != null) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+a.getPhoneNum().toString()));
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(((Activity)context), new String[]{Manifest.permission.CALL_PHONE}, 0);
                        return;
                    }
                    context.startActivity(callIntent);
                }
            }
        });

        LinearLayout llMain = (LinearLayout)view.findViewById(R.id.llMain);
        Utils.animateGrid(llMain, context, i);
        return view;
    }
}
