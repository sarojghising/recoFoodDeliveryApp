package com.example.ktmeats;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ktmeats.Common.Common;
import com.example.ktmeats.Interface.ItemClickListener;
import com.example.ktmeats.Model.Category;
import com.example.ktmeats.Service.ListenOrder;
import com.example.ktmeats.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    FirebaseDatabase database;
    DatabaseReference categories;
    TextView txtFullName;

    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;

    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);

        //Initialize firebase

        database = FirebaseDatabase.getInstance();
        categories = database.getReference("Category");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            //go to cart when button clicked
            @Override
            public void onClick(View view) {
                Intent cartIntent = new Intent(Home.this, Cart.class);
                startActivity(cartIntent);
            }
        });

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
       NavigationUI.setupWithNavController(navigationView, navController);

       navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               int id = item.getItemId();

               if(id == R.id.nav_menu){

               }
               else if(id == R.id.nav_cart){
                   //view cart
                   Intent cartIntent = new Intent(Home.this,Cart.class);
                   startActivity(cartIntent);
               }
               else if(id == R.id.nav_orders){
                   //orders page
                   Intent orderStatusIntent = new Intent(Home.this, OrderStatus.class);
                   startActivity(orderStatusIntent);
               }
               else if(id == R.id.nav_sign_out){
                   //Logout/return to login page
                   Intent signIn = new Intent(Home.this, SignIn.class);
                   signIn.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   startActivity(signIn);
               }
               drawer.closeDrawer(GravityCompat.START);
               return true;
           }
       });

        //Set Name for current user
        View headerView = navigationView.getHeaderView(0);
        txtFullName = (TextView) headerView.findViewById(R.id.fullNameTextView);
        txtFullName.setText(Common.currentUser.getName());

        //Load Menu

       
        
     
        recycler_menu = (RecyclerView) findViewById(R.id.recycler_menu);
        recycler_menu.setNestedScrollingEnabled(false);
        recycler_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(layoutManager);
        adapter = new HomeAdapter(getDataSetHome(), Home.this);
        recycler_menu.setAdapter(adapter);
        
        loadMenu();

        //Register Service
        Intent service = new Intent(Home.this, ListenOrder.class);
        startService(service);

    }

    private void loadMenu() {

        Query query = FirebaseDatabase.getInstance().getReference("Category");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for(DataSnapshot cat : dataSnapshot.getChildren()){
                        FetchMatchInformation(cat.getKey());


                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void FetchMatchInformation(String key) {
        DatabaseReference categoryDb = FirebaseDatabase.getInstance().getReference().child("Category").child(key);
        categoryDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = "";
                String image = "";
                String CategoryId = dataSnapshot.getKey();


                if (dataSnapshot.child("name").getValue() != null) {
                    name = dataSnapshot.child("name").getValue().toString();

                    if (dataSnapshot.child("image").getValue() != null) {
                        image = dataSnapshot.child("image").getValue().toString();

                            Category ctg = new Category(name, image, CategoryId);
                            results.add(ctg);

                            adapter.notifyDataSetChanged();
                        }
                    }

                }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }


        });

    }

    private ArrayList<Category> results = new ArrayList<Category>();
    private List<Category> getDataSetHome() {
        return  results;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }



}

