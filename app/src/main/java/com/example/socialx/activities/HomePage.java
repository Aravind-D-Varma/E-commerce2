package com.example.socialx.activities;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.socialx.R;
import com.example.socialx.adapters.HomeItems2Adapter;
import com.example.socialx.adapters.HomeItems3Adapter;
import com.example.socialx.adapters.HomeItemsAdapter;
import com.example.socialx.adapters.VPAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity {
    private ViewPager2 mViewPager2;
    private RecyclerView mRV1,mRV1_5,mRV2,mRV3,mRV4,mRV5;
    private HomeItemsAdapter mRVAdapter;
    private VPAdapter mVPAdapter;
    private DrawerLayout mDrawerLayout;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater mI = getMenuInflater();
        mI.inflate(R.menu.home_page_menu,menu);
        return true;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        mDrawerLayout = findViewById(R.id.drawerlayout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.NDopen, R.string.NDclose);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.setHomeAsUpIndicator(R.drawable.drawericon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toggle.syncState();

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }

        mRV1 = (RecyclerView) findViewById(R.id.RecyclerView1);
        mRV1_5 = (RecyclerView) findViewById(R.id.RecyclerView1_5);
        mRV2 = (RecyclerView) findViewById(R.id.RecyclerView2);
        mRV3 = (RecyclerView) findViewById(R.id.RecyclerView3);
        mRV4 = (RecyclerView) findViewById(R.id.RecyclerView4);
        mRV5 = (RecyclerView) findViewById(R.id.RecyclerView5);

        homeFirstItemViewPager();
        homeSecondItemRV();
        homeThirdItemRV();
        homeFourthItem(mRV2,true);
        homeFourthItem(mRV3,true);
        homeFourthItem(mRV4,true);
        homeFourthItem(mRV3,true);
        homeFourthItem(mRV5,false);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            return closeAndOpenDrawer();
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean closeAndOpenDrawer() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START))
            mDrawerLayout.closeDrawer(GravityCompat.START);
        else
            mDrawerLayout.openDrawer(GravityCompat.START);
        return true;
    }
    private void homeThirdItemRV() {
        mRV1_5.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false){
            @Override
            public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
                lp.width = getWidth()/2;
                return true;
            }
        });
        ArrayList<String> vpOfferText = new ArrayList<>();
        vpOfferText.add("UPTO 50% OFF");
        vpOfferText.add("BUY 1 GET 1 FREE");
        vpOfferText.add("UPTO 75% OFF");

        ArrayList<String> vpOfferText2 = new ArrayList<>();
        vpOfferText2.add("FOR ALL MENS COLLECTIONS");
        vpOfferText2.add("ON ALL BOOKS!!");
        vpOfferText2.add("ON ACCOUNT OF NEW YEAR!!");
        VPAdapter itemsAdapter1 = new VPAdapter(this,vpOfferText,vpOfferText2);
        mRV1_5.setAdapter(itemsAdapter1);
    }

    private void homeFourthItem(RecyclerView recyclerView, boolean linear) {
        if(linear)
            recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        else
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        ArrayList<String> sI2 = new ArrayList<String>();
        sI2.add("Up to 10% OFF");
        sI2.add("Up to 30% OFF");
        sI2.add("Up to 50% OFF");
        sI2.add("Up to 70% OFF");
        sI2.add("Up to 90% OFF");
        if(linear) {
            HomeItems2Adapter itemsAdapter2 = new HomeItems2Adapter(this, sI2);
            recyclerView.setAdapter(itemsAdapter2);
        }
        else{
            HomeItems3Adapter itemsAdapter3 = new HomeItems3Adapter(this, sI2);
            recyclerView.setAdapter(itemsAdapter3);
        }
    }

    private void homeSecondItemRV() {
        mRV1.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        ArrayList<String> sI = new ArrayList<String>();
        sI.add("Arts and Crafts");
        sI.add("Automobiles");
        sI.add("Baby Items");
        sI.add("Books");
        sI.add("Computer");
        sI.add("Cooking");
        HomeItemsAdapter itemsAdapter1 = new HomeItemsAdapter(this,sI);
        mRV1.setAdapter(itemsAdapter1);
    }

    private void homeFirstItemViewPager() {
        mViewPager2 = (ViewPager2) findViewById(R.id.homeitem1);
        ArrayList<String> vpOfferText = new ArrayList<>();
        vpOfferText.add("UPTO 50% OFF");
        vpOfferText.add("BUY 1 GET 1 FREE");
        vpOfferText.add("UPTO 75% OFF");

        ArrayList<String> vpOfferText2 = new ArrayList<>();
        vpOfferText2.add("FOR ALL MENS COLLECTIONS");
        vpOfferText2.add("ON ALL BOOKS!!");
        vpOfferText2.add("ON ACCOUNT OF NEW YEAR!!");
        mVPAdapter = new VPAdapter(this,vpOfferText,vpOfferText2);
        mViewPager2.setAdapter(mVPAdapter);
        mViewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
        TabLayout tabLayout = findViewById(R.id.tabDots);
        new TabLayoutMediator(tabLayout, mViewPager2,(tab, position) -> tab.setText("")).attach();
    }

    private void doMySearch(String query) {
    }
}
