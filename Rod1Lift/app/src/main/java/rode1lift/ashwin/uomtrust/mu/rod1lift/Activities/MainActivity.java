package rode1lift.ashwin.uomtrust.mu.rod1lift.Activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.Const;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DAO.AccountDAO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.AccountDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        MapActivity mapActivity = new MapActivity();
        changeFragment(mapActivity);


        SharedPreferences prefs = getSharedPreferences(Const.appName, MODE_PRIVATE);
        Integer accountId = prefs.getInt(Const.currentAccountId, -1);

        AccountDTO accountDTO = new AccountDAO(this).getAccountById(accountId);

        setProfileDetails(navigationView, accountDTO);



    }

    private void setProfileDetails(NavigationView navigationView, AccountDTO accountDTO){
        View view =  navigationView.getHeaderView(0);

        ImageView imgProfilePic = (ImageView) view.findViewById(R.id.imgProfilePic);
        imgProfilePic.setImageBitmap(Utils.convertBlobToBitmap(accountDTO.getProfilePicture()));

        TextView txtFullName = (TextView) view.findViewById(R.id.txtFullName);
        txtFullName.setText(accountDTO.getFirstName() +" "+accountDTO.getLastName());

        LinearLayout llMainProfilePic = (LinearLayout)view.findViewById(R.id.llMainProfilePic);
        llMainProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivityForResult(intent, Const.MAIN_ACTIVITY);
            }
        });

        Utils.animateLayout(llMainProfilePic);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void changeFragment(Fragment fragment){
        Fragment newFragment = fragment;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        //transaction.setCustomAnimations(R.animator.newenter, R.animator.exit, R.animator.pop_enter, R.animator.pop_exit);

        transaction.replace(R.id.content_main, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
