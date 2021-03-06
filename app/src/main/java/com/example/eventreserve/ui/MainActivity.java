package com.example.eventreserve.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TabHost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.eventreserve.R;
import com.example.eventreserve.adapters.ViewPagerAdapter;
import com.example.eventreserve.fragments.FriendsFragment;
import com.example.eventreserve.fragments.MessagesFragment;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;
import java.util.Vector;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener{

    private TabHost tabHost;
    private ViewPager viewPager;
    private ViewPagerAdapter myViewPagerAdapter;
    private int i = 0;
    private Bundle bundle;

    // fake content for tabhost
    class FakeContent implements TabHost.TabContentFactory {
        private final Context mContext;

        public FakeContent(Context context) {
            mContext = context;
        }

        @Override
        public View createTabContent(String tag) {
            View v = new View(mContext);
            v.setMinimumHeight(0);
            v.setMinimumWidth(0);
            return v;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        i++;

        // init tabhost
        this.initializeTabHost(savedInstanceState);

        // init ViewPager
        this.initializeViewPager();

        bundle = getIntent().getExtras();
        if(bundle != null) {
            int tab = bundle.getInt("ReturnTab");
            tabHost.setCurrentTab(tab);
        }
    }

    private void initializeViewPager() {
        List<Fragment> fragments = new Vector<Fragment>();

        // transfer data from MainActivity via PersonalFragment
        bundle = getIntent().getExtras();
//        String uid = bundle.getString("UID");

        Bundle info = new Bundle();
 //      info.putString("UID",uid);

        fragments.add(new MessagesFragment());
        fragments.add(new FriendsFragment());

        this.myViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        this.viewPager = (ViewPager) super.findViewById(R.id.viewPager);
        this.viewPager.setAdapter(this.myViewPagerAdapter);
        this.viewPager.setOnPageChangeListener(this);
        onRestart();

    }

    private void initializeTabHost(Bundle args) {

        tabHost = (TabHost) findViewById(android.R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec tabMessages = tabHost.newTabSpec("Message");
        tabMessages.setIndicator("",getResources().getDrawable(R.drawable.icon_messages_selector));
        tabMessages.setContent(new FakeContent(MainActivity.this));
        tabHost.addTab(tabMessages);

        TabHost.TabSpec tabFriends = tabHost.newTabSpec("Friend");
        tabFriends.setIndicator("",getResources().getDrawable(R.drawable.icon_friends_selector));
        tabFriends.setContent(new FakeContent(MainActivity.this));
        tabHost.addTab(tabFriends);

        tabHost.setOnTabChangedListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        this.tabHost.setCurrentTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabChanged(String tabId) {
        int pos = this.tabHost.getCurrentTab();
        this.viewPager.setCurrentItem(pos);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        else if(id==R.id.action_event){
            Intent intent=new Intent(MainActivity.this,FindEvents.class);
            startActivity(intent);
        }
        else if(id==R.id.action_saved_events){
            Intent intent=new Intent(MainActivity.this,SavedEventstListActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
}