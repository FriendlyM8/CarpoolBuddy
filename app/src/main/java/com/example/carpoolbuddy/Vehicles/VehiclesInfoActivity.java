package com.example.carpoolbuddy.Vehicles;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;

import com.example.carpoolbuddy.AuthActivity;
import com.example.carpoolbuddy.R;
import com.example.carpoolbuddy.Vehicles.AddVehicleActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * This class displays all the open vehicles to the user and shows additional closed vehicles owned by the owner. It also allows the user to go to add vehicles and sign out.
 * Additionally, it will also detect the vehicle clicked on by the user.
 *
 * @author Alden Chan
 * @version 0.1
 */
public class VehiclesInfoActivity extends AppCompatActivity implements VehicleRecyclerViewAdapter.OnVehicleListener {

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;

    RecyclerView recView;
    ArrayList<CISVehicles> vehicleList;
    ArrayList<String> vehicleListString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicles_info);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        recView = findViewById(R.id.vehiclesListTextView);

        vehicleList = new ArrayList();
        vehicleListString = new ArrayList();

        VehicleRecyclerViewAdapter myAdapter = new VehicleRecyclerViewAdapter(vehicleListString, this);
        recView.setAdapter(myAdapter);
        recView.setLayoutManager(new LinearLayoutManager(this));
        Log.d("RecyclerView", "Rec View Success");

        FirebaseUser user = mAuth.getCurrentUser();
        String userIDString = user.getUid();

        /**
         * This section fetches the vehicles open and all vehicles owned by the current user and displays it using a recycler view.
         */
        firestore.collection("Vehicles").whereEqualTo("open", true).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        CISVehicles curVehicle = document.toObject(CISVehicles.class);
                        Log.d("Vehicle Model:", curVehicle.getModel());
                        Log.d("Vehicle Seats Available:", String.valueOf(curVehicle.getSpace()));
                        Log.d("Vehicle Base Price", String.valueOf(curVehicle.getBasePrice()));
                        vehicleListString.add("Model: "+ curVehicle.getModel() +", Seats Available: "+ curVehicle.getSpace()+ ", Price: "+ curVehicle.getBasePrice());
                        vehicleList.add(curVehicle);
                        myAdapter.notifyItemRangeInserted(0, vehicleList.size());
                    }
                }
                else{
                    Log.d("Get Vehicles", "Failed to get Vehicles", task.getException());
                }
            }
        });

        firestore.collection("Vehicles").whereEqualTo("owner", userIDString).whereEqualTo("open", false).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        CISVehicles curVehicle = document.toObject(CISVehicles.class);
                        Log.d("Vehicle Model:", curVehicle.getModel());
                        Log.d("Vehicle Seats Available:", String.valueOf(curVehicle.getSpace()));
                        Log.d("Vehicle Base Price", String.valueOf(curVehicle.getBasePrice()));
                        vehicleListString.add("Model: " + curVehicle.getModel() + ", Seats Available: " + curVehicle.getSpace() + ", Price: " + curVehicle.getBasePrice());
                        vehicleList.add(curVehicle);
                        myAdapter.notifyItemRangeInserted(0, vehicleList.size());
                    }
                }
                else {
                    Log.d("Get Vehicles", "Failed to get Vehicles", task.getException());
                }
            }
        });
    }

    //Allow users to sign out
    public void signOut(View v) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
    }


    public void goToAddVehicle(View v){
        Intent intent = new Intent(this, AddVehicleActivity.class);
        startActivity(intent);
    }

    /**
     * This method detects the vehicle selected by the user and sends the information of the vehicle using a putExtra.
     * @param position
     */
    @Override
    public void onVehicleClick(int position) {
        vehicleList.get(position);
        Log.d("selectedVehicle", "VehicleList get position: success");
        Intent intent = new Intent(this, VehicleProfileActivity.class);
        intent.putExtra("selectedVehicle", vehicleList.get(position));
        Log.d("selectedVehicle", "Put Extra: success");
        startActivity(intent);
    }
}