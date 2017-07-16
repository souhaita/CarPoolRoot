package rode1lift.ashwin.uomtrust.mu.rod1lift.Activities;

import android.app.Fragment;
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

import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DAO.AccountDAO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.AccountDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;

public class ActivityMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActivityMap mapActivity = new ActivityMap();
        changeFragment(mapActivity);

        Integer accountId = Utils.getCurrentAccount(getApplicationContext());

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
                Intent intent = new Intent(ActivityMain.this, ProfileActivity.class);
                startActivityForResult(intent, CONSTANT.MAIN_ACTIVITY);
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
        //getMenuInflater().inflate(R.menu.main, menu);
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

        if (id == R.id.nav_map) {
            ActivityMap activityMap = new ActivityMap();
            changeFragment(activityMap);

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (id == R.id.nav_create_trip) {
            Intent intent = new Intent(ActivityMain.this, ActivityCreateTrip.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_manage) {
            Intent intent = new Intent(ActivityMain.this, ActivityDriverManageRequest.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_history) {

        }

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

    @Override
    protected void onResume(){
        super.onResume();

        Integer accountId = Utils.getCurrentAccount(getApplicationContext());

        AccountDTO accountDTO = new AccountDAO(this).getAccountById(accountId);

        setProfileDetails(navigationView, accountDTO);
    }

}
