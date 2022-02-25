package com.example.carpoolbuddy.Vehicles;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carpoolbuddy.R;
import com.example.carpoolbuddy.User.CISUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * This class lets users view a specific vehicle they selected.
 *
 * @author Alden Chan
 * @version 0.1
 */
public class VehicleProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;

    protected String selectedVehicleOwner;
    protected int selectedVehicleSpace;
    protected String selectedVehicleID;
    protected boolean selectedVehicleOpen;
    protected double selectedVehiclePrice;
    protected int selectedUserBookedTimes;
    protected int selectedUserTotalBookedTimes;
    protected double selectedUserMoney;
    protected String userIDString;
    protected int discountStatus;
    protected int rankStatus;
    private Button reserveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_profile);
        Log.d("OnClickListener", "onCreate: started");

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        TextView vehicleProfileText = findViewById(R.id.vehicleProfileTextView);
        reserveButton = findViewById(R.id.bookCloseButton);

        String selectedVehicleString;

        FirebaseUser user = mAuth.getCurrentUser();
        String userIDString = user.getUid();

        if(getIntent().hasExtra("selectedVehicle")){
            Intent intent = getIntent();
            CISVehicles selectedVehicle = (CISVehicles) intent.getSerializableExtra("selectedVehicle");
            selectedVehicleString = "BasePrice: "+ selectedVehicle.getBasePrice() +"\n Capacity: "+ selectedVehicle.getCapacity() +"\n Space Left: " + selectedVehicle.getSpace()+"\n Model: "+ selectedVehicle.getModel()+ "\n VehicleType: "+ selectedVehicle.getVehicleType();
            vehicleProfileText.setText(selectedVehicleString);
            selectedVehicleOwner = selectedVehicle.getOwner();
            selectedVehicleSpace = selectedVehicle.getSpace();
            selectedVehicleID = selectedVehicle.getVehicleID();
            selectedVehicleOpen = selectedVehicle.isOpen();
            selectedVehiclePrice = selectedVehicle.getBasePrice();
            Log.d("VehicleProfile", "onCreate:" + selectedVehicle.toString());
            Log.d("VehicleProfile", "Vehicle Owner:"+ selectedVehicleOwner);
        }

        /**
         * This section determines what the button should display. Either close/open or reserve.
         */
        if(userIDString!=selectedVehicleOwner) {
            reserveButton.setText("Reserve");
        }
        if(userIDString.equals(selectedVehicleOwner)) {
            if(selectedVehicleOpen == true){
                reserveButton.setText("Close");
            }
            if(selectedVehicleOpen == false){
                reserveButton.setText("Open");
            }
        }
    }

    /**
     * This method fetches the current user's information and runs the method "calculations"
     * @param v
     */
    //Reduce current capacity for this vehicle
    public void bookCloseButton(View v) {
        FirebaseUser user = mAuth.getCurrentUser();
        userIDString = user.getUid();
        discountStatus = 0;
        rankStatus = 0;
        Log.d("VehicleProfile", "Cur User:" + userIDString);

        firestore.collection("Users").document(userIDString).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    if (!userIDString.equals(selectedVehicleOwner)) {
                        DocumentSnapshot ds = task.getResult();
                        Log.d("Cur User BookedTimes", ds.get("bookedTimes").toString());
                        CISUser curUser = ds.toObject(CISUser.class);
                        if (curUser != null) {
                            selectedUserBookedTimes = curUser.getBookedTimes();
                            Log.d("User Booked Times:", String.valueOf(selectedUserBookedTimes));
                            Log.d("User Money:", String.valueOf(curUser.getMoney()));
                            selectedUserMoney = curUser.getMoney();
                            selectedUserTotalBookedTimes = curUser.getTotalBookedTimes();
                        }
                        if (curUser == null) {
                            Log.d("Get User", "curUser Null", task.getException());
                        }
                    }
                    calculations();
                } else {
                    Log.d("Get User", "Failed to get User", task.getException());
                }
            }
        });
    }

    /**
     * This method takes the current user's information and the selected vehicle information and runs calculations accordingly.
     * It determines if the current user is the owner.
     * If the current user is not the vehicle owner, it will calculate the amount of times the user has booked a ride and give/calculate discounts if applicable.
     * Additionally, it will also deduct the user's money by the vehicle's base price while reducing the vehicle's space and update to Firebase Firestore.
     * If the vehicle has 0 seats left, it will remove the vehicle.
     * If the current user is the vehicle owner, it will act accordingly to whether the vehicle is open or closed and update the Firebase Firestore to the open status of the vehicle.
     */
    public void calculations(){
        if(!userIDString.equals(selectedVehicleOwner)){
            Log.d("User Discount", "Cur User:"+ userIDString);
            selectedVehicleSpace = selectedVehicleSpace - 1;
            firestore.collection("Vehicles").document(selectedVehicleID).update("space", selectedVehicleSpace);
            Log.d("UserBookedTimes", String.valueOf(selectedUserBookedTimes));
            if(selectedUserBookedTimes==4){
                selectedUserMoney = selectedUserMoney - selectedVehiclePrice;
                selectedUserTotalBookedTimes = selectedUserTotalBookedTimes + 1;
                selectedUserMoney = selectedUserMoney+10;
                if (selectedUserTotalBookedTimes == 10){
                    selectedUserMoney = selectedUserMoney+1;
                    rankStatus = 1;
                }
                if (selectedUserTotalBookedTimes == 20){
                    selectedUserMoney = selectedUserMoney+2;
                    rankStatus = 2;
                }
                if (selectedUserTotalBookedTimes == 50){
                    selectedUserMoney = selectedUserMoney+5;
                    rankStatus = 3;
                }
                discountStatus = 1;
                firestore.collection("Users").document(userIDString).update("money", selectedUserMoney);
                firestore.collection("Users").document(userIDString).update("bookedTimes", 0);
                firestore.collection("Users").document(userIDString).update("totalBookedTimes", selectedUserTotalBookedTimes);
                Log.d("Vehicle Profile", "User Money "+ selectedUserMoney);
                Log.d("Vehicle Profile", "Price discounted: Success");
                //userTier = if 10> then Silver, 20> then Gold, 30> Diamond
            }
            if(selectedUserBookedTimes != 4){
                selectedUserBookedTimes = selectedUserBookedTimes +1;
                selectedUserTotalBookedTimes = selectedUserTotalBookedTimes + 1;
                firestore.collection("Users").document(userIDString).update("bookedTimes", selectedUserBookedTimes);
                selectedUserMoney = selectedUserMoney - selectedVehiclePrice;
                if (selectedUserTotalBookedTimes == 10){
                    selectedUserMoney = selectedUserMoney+1;
                    rankStatus = 1;
                }
                if (selectedUserTotalBookedTimes == 20){
                    selectedUserMoney = selectedUserMoney+2;
                    rankStatus = 2;
                }
                if (selectedUserTotalBookedTimes == 50){
                    selectedUserMoney = selectedUserMoney+5;
                    rankStatus = 3;
                }
                firestore.collection("Users").document(userIDString).update("money", selectedUserMoney);
                firestore.collection("Users").document(userIDString).update("totalBookedTimes", selectedUserTotalBookedTimes);
                discountStatus = 2;
                Log.d("Vehicle Profile", "Amount of rides booked is below 5");
                Log.d("Vehicle Profile", "User Money "+ selectedUserMoney);
            }
            if(selectedVehicleSpace==0){
                firestore.collection("Vehicles").document(selectedVehicleID).delete();
            }
            else{
                Log.d("Vehicle Profile", "User Discount failed, Booked Times:"+ selectedUserBookedTimes);
            }
        }
        if(userIDString.equals(selectedVehicleOwner)){
            if(userIDString.equals(selectedVehicleOwner)) {
                if(selectedVehicleOpen == true){
                    firestore.collection("Vehicles").document(selectedVehicleID).update("open",false);
                    Log.d("Vehicle Profile", "Vehicle Closed");
                }
                if(selectedVehicleOpen == false){
                    firestore.collection("Vehicles").document(selectedVehicleID).update("open",true);
                    Log.d("Vehicle Profile", "Vehicle Opened");
                }
            }
        }
        Intent intent = new Intent(this, VehiclesInfoActivity.class);
        if(discountStatus == 1){
            String text = ("Congratulations! You have booked 5 seats and as a reward you get discount of 10 dollars.");
            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        }
        if(discountStatus == 2){
            String text = ("Amount of rides booked:"+ selectedUserBookedTimes);
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        }
        if(rankStatus == 1){
            String text = ("Because you are a iron member. -1 dollars discount");
            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        }
        if(rankStatus == 2){
            String text = ("Because you are a gold member. -2 dollars discount");
            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        }
        if(rankStatus == 3){
            String text = ("Because you are a diamond member. -5 dollars discount");
            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        }
        startActivity(intent);
    }
}