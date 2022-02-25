package com.example.carpoolbuddy.Vehicles;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.carpoolbuddy.R;
import com.example.carpoolbuddy.User.CISUser;
import com.example.carpoolbuddy.User.UserProfileActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

/**
 * This class allows users to add vehicles while giving in information for the vehicles. The vehicle will then be added to Firebase Firestore.
 *
 * @author Alden Chan
 * @version 0.1
 */
public class AddVehicleActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;

    protected EditText model;
    protected String vehicleType;
    protected String userIDString;
    protected double userMoney;
    protected EditText capacity;
    protected EditText basePrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);


        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userIDString = user.getUid();


        Spinner spinner = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.vehicleType, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        model = findViewById(R.id.addModelText);
        vehicleType = spinner.getSelectedItem().toString();
        capacity = findViewById(R.id.addCapacityText);
        basePrice = findViewById(R.id.addBasePriceText);

        firestore.collection("Users").document(userIDString).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot ds = task.getResult();
                    Log.d("Add Vehicle", "Money:"+ ds.get("money").toString());
                    CISUser curUser = ds.toObject(CISUser.class);
                    userMoney = curUser.getMoney();
                }
                else {
                Log.d("Add Vehicle", "Failed to get User", task.getException());
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        String choice = adapterView.getItemAtPosition(position).toString();
        vehicleType = choice;
        Toast.makeText(getApplicationContext(), choice, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * This method collected the information provided by the user about the vehicle and creates a new CISVehicles and then adds it to Firebase Firestore.
     * Additionally, if the vehicle is a electric car, it rewards the user as a way to increase incentive for using electric vehicles.
     *
     * @param v
     */
    //Allow users to add vehicles
    public void addVehicle(View v){
        System.out.println("Add Vehicle");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserID = user.getUid();
        String modelString = model.getText().toString();
        String vehicleTypeString = vehicleType;
        String capacityString = capacity.getText().toString();
        int capacityInt = Integer.parseInt(capacityString);
        int spaceInt = capacityInt;
        String basePriceString = basePrice.getText().toString();
        double basePriceDouble = Double.parseDouble(basePriceString);


//      String owner, String model, String vehicleType, int capacity, int space, String vehicleID, boolean open, double basePrice, ArrayList ridersIDs
        CISVehicles newVehicles = new CISVehicles(currentUserID, modelString, vehicleTypeString, capacityInt, spaceInt, UUID.randomUUID().toString(), true, basePriceDouble, null);

        firestore.collection("Vehicles").document(newVehicles.getVehicleID()).set(newVehicles);
        Log.d("Add Vehicles", "Vehicle type:"+ newVehicles.getVehicleType());
        Log.d("Add Vehicles", "addVehicle:success");

        if(vehicleTypeString.equals("ElectricCar")){
            userMoney = userMoney + 20;
            firestore.collection("Users").document(userIDString).update("money", userMoney);
            String text = ("Thank you for using a Electric Car, you are helping save the world! +20 money");
            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        }
        Intent intent = new Intent(this, VehiclesInfoActivity.class);
        startActivity(intent);
    }


}