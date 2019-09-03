package com.ashstudios.safana.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.ashstudios.safana.Fragments.BottomSheetSortFragment;
import com.ashstudios.safana.Fragments.BottomSheetSortLeaveFragment;
import com.ashstudios.safana.R;
import com.ashstudios.safana.ui.leave_management.LeaveManagementFragment;
import com.ashstudios.safana.ui.worker_details.WorkerDetailsFragment;
import com.google.android.material.navigation.NavigationView;

public class SupervisorDashboard extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private Bundle workerSortBundle;
    private Bundle leaveSortBundle;
    private MenuItem menuItem;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervisor_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_worker, R.id.nav_project_details, R.id.nav_allowance_management,
                R.id.nav_tasks, R.id.nav_leave_management)
                .setDrawerLayout(drawer)
                .build();
        final NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        workerSortBundle = new Bundle();
        leaveSortBundle = new Bundle();

        menuItem = navigationView.getCheckedItem();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.supervisor_dashboard, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                if(navigationView.getMenu().findItem(R.id.nav_worker).isChecked())
                {
                    BottomSheetSortFragment bottomSheetFragment = new BottomSheetSortFragment();
                    workerSortBundle = initWorkerBundle();  // for remembering the sorting. Otherwise default sorting is always displayed not the selected one
                    bottomSheetFragment.setArguments(workerSortBundle);
                    bottomSheetFragment.show(getSupportFragmentManager(), "hello");
                }
                else if (navigationView.getMenu().findItem(R.id.nav_leave_management).isChecked())
                {
                    BottomSheetSortLeaveFragment bottomSheetSortLeaveFragment = new BottomSheetSortLeaveFragment();
                    leaveSortBundle = initLeaveSortBundle();  // for remembering the sorting. Otherwise default sorting is always displayed not the selected one
                    bottomSheetSortLeaveFragment.setArguments(leaveSortBundle);
                    bottomSheetSortLeaveFragment.show(getSupportFragmentManager(), "hello");
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onWorkerDetailsSortingChanged(Bundle b)
    {
        workerSortBundle = (Bundle)b.clone();
        WorkerDetailsFragment.sort(getBaseContext(),workerSortBundle);
    }

    public void onLeaveSortingChanged(Bundle b)
    {
        leaveSortBundle = (Bundle)b.clone();
        LeaveManagementFragment.sort(getBaseContext(),leaveSortBundle);
    }

    private Bundle initWorkerBundle()
    {
        if(workerSortBundle.isEmpty())
        {
            workerSortBundle.putBoolean("nameChip",false);
            workerSortBundle.putBoolean("maleChip",true);
            workerSortBundle.putBoolean("femaleChip",true);
            workerSortBundle.putBoolean("otherChip",true);
//          workerSortBundle.putString("role","all");
//          workerSortBundle.putString("shift","all");
            return workerSortBundle;
        }
        else
        {
            return workerSortBundle;
        }
    }

    private Bundle initLeaveSortBundle()
    {
        if(leaveSortBundle.isEmpty())
        {
            leaveSortBundle.putBoolean("nameChip",false);
            leaveSortBundle.putBoolean("dateChip",false);
            leaveSortBundle.putBoolean("maleChip",true);
            leaveSortBundle.putBoolean("femaleChip",true);
            leaveSortBundle.putBoolean("otherChip",true);
//          workerSortBundle.putString("role","all");
//          workerSortBundle.putString("shift","all");
            return leaveSortBundle;
        }
        else
        {
            return leaveSortBundle;
        }
    }
}