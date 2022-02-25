package com.example.carpoolbuddy.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.carpoolbuddy.AuthActivity;
import com.example.carpoolbuddy.R;
import com.example.carpoolbuddy.Vehicles.VehiclesInfoActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * This class shows the user which account they are signed into, while also displaying their rank and amount of money left. Additionally, this class also allows user to view the vehicles or sign out.
 *
 * @author Alden Chan
 * @version 0.1
 */
public class UserProfileActivity extends AppCompatActivity {

    private TextView displayUserText;
    private TextView displayUserMoney;

    protected String userRank;

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userRank = "";

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        String userIDString = user.getUid();

        setContentView(R.layout.activity_user_profile);

        String email = getIntent().getExtras().get("email").toString();

        displayUserText = findViewById(R.id.displayUserText);

        /**
         * This section fetches the current user's information and displays it on the screen. Additionally, it will also determine which rank the user is based off their total rides booked.
         */
        firestore.collection("Users").document(userIDString).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot ds = task.getResult();
                    Log.d("User Profile", "Money"+ ds.get("money").toString());
                    CISUser curUser = ds.toObject(CISUser.class);
                    double money = curUser.getMoney();
                    displayUserMoney = findViewById(R.id.displayMoneyText);
                    Log.d("User Profile", "Display Money:"+ money);
                    displayUserMoney.setText("Money: "+ money);

                    Log.d("User Profile", "Total Booked Times:"+ ds.get("totalBookedTimes").toString());
                    int totalBookedTimes = curUser.getTotalBookedTimes();

                    //Set User Rank by total rides booked
                    if(totalBookedTimes == 10){
                        userRank = ("[IRON]");
                    }
                    if(totalBookedTimes == 20){
                        userRank = ("[GOLD]");
                    }
                    if(totalBookedTimes == 50){
                        userRank = ("[DIAMOND]");
                    }
                    displayUserText.setText("Hello, "+ userRank +" \n"+ email);
                }
            }
        });

    }

    public void goToVehiclesProfile(View v){
        Intent intent = new Intent(this, VehiclesInfoActivity.class);
        startActivity(intent);
    }

    //Allow users to sign out
    public void signOut(View v) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
    }
}