package com.example.petapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
//Array of Images that are displayed by the plant evolution status
    Integer[]plantImageArray = {R.drawable.plant_evolution1,R.drawable.plant_evolution2,R.drawable.plant_evolution3,R.drawable.plant_evolution4,R.drawable.plant_evolution5,R.drawable.plant_evolution6,R.drawable.plant_evolution7,R.drawable.plant_evolution8,R.drawable.plant_evolution9,R.drawable.plant_evolution10};
    //Array of the two actions users can pick that are later used on the onclick method
    Integer[]plantActions = {R.drawable.fertilizer,R.drawable.water};
    //Declaration of the text view values that are changed
    TextView greeting,waterLevel,fertilizerLevel, LifeValue;
    //time in milliseconds used for a later method
    int timeLeftInMillisecond;
    //Progressbar that changes based on the countdown timer
    private ProgressBar progressBar;
    //calling the plant class and creating a new instance of a plant with all variables that are needed and changed
    plant myPlant =  new plant();
    float myPlantLifeStatus = (float) myPlant.getLife();
    int myPlantFertilizer = myPlant.getFertilizer();
    int myPlantWater = myPlant.getWater();
    int myPlantEvolutionStatus = myPlant.getEvolutionStatus();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Finding the id of textviews that change
        greeting = findViewById(R.id.greeting);
        waterLevel = findViewById(R.id.waterValue);
        LifeValue = findViewById(R.id.overallLifeValue);
        fertilizerLevel = findViewById(R.id.fertilizerValue);

        //Time left in millisecond
        timeLeftInMillisecond = 30000;
        //Progress bar definition
        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(timeLeftInMillisecond);
        //calling the countdown timer class with the time left in milliseconds
        countDownTimer(timeLeftInMillisecond);
        //setting the text to the random number from the class and displaying it
        waterLevel.setText(myPlantWater+"%");
        fertilizerLevel.setText(myPlantFertilizer+"%");
    }

    //Countdown timer method that creates a timer
    public void countDownTimer(int time){
        new CountDownTimer(time,1000){
            @Override
            public void onTick(long pp) {
                //setting the text to display seconds
                greeting.setText("Secs left: "+pp/1000);
                int myInteger = ((int)pp);
                //calling the life method
                life();
                //decreasing the plant water level based on the countdown timer
                myPlantWater -= (myInteger/30000);
                //if statement to decrease water level if timer reaches a certain timer
                if(myPlantWater <400){
                    myPlantWater-=10;
                }
                //setting the water text to display the current water
                waterLevel.setText(myPlantWater+"%");
                //doing the same thing as above for the fertilizer
                myPlantFertilizer -= (myInteger/30000);
                if(myPlantFertilizer <400){
                    myPlantFertilizer-=10;
                }
                fertilizerLevel.setText(myPlantFertilizer+"%");
                //progress bar changes based on the ticking
                progressBar.setProgress(myInteger);
            }
            @Override
            public void onFinish() {
                //on finish call the evolve method to devolve or evolve the plant
                evolve();
            }
        }.start();
    }
    //evolve method
    public void evolve(){
        //find the plant view
        ImageView plantView = findViewById(R.id.plant);
        if(myPlantLifeStatus > 80 && myPlantEvolutionStatus<110){
            //increase the plant evolution
             myPlantEvolutionStatus++;
             plantView.setImageResource(plantImageArray[myPlantEvolutionStatus]);
             Toast.makeText(this,"Your plant has evolved",Toast.LENGTH_SHORT).show();
             //call a new countdown timer with more time
             countDownTimer(timeLeftInMillisecond*myPlantEvolutionStatus);
        } else if(myPlantLifeStatus>111 || myPlantLifeStatus <79){
            if(myPlantEvolutionStatus == 1){
                myPlantEvolutionStatus += 1;
                //if your plant dies then it takes you to the instructions
                Toast.makeText(this,"Your plant died.",Toast.LENGTH_SHORT).show();
                Intent instructions = new Intent(this,startActivity.class);
                startActivity(instructions);
                finish();
                //destory so that the timer doesn't run in background
                onDestroy();
            }
            //decrease the evolution status
            myPlantEvolutionStatus-=1;
            //change the drawable
            plantView.setImageResource(plantImageArray[myPlantEvolutionStatus]);
            //create a toast to display devolution
            Toast.makeText(this,"Your plant is devolving",Toast.LENGTH_SHORT).show();
            //call a new countdowntimer
            countDownTimer(timeLeftInMillisecond*myPlantEvolutionStatus);
        }
    }
    //button click water method
    public void addWater(View view) {
        myPlantWater +=10;
        waterLevel.setText(myPlantWater+"%");
        life();
        showAction(400,1);
    }
    //button click fertilizer method
    public void addFertilizer(View view) {
        myPlantFertilizer+=10;
        fertilizerLevel.setText(myPlantFertilizer+"%");
        life();
        showAction(400,0);
    }
    //show life method and get percentage from fertilizer and water levels
    public void life(){
        myPlantLifeStatus = Math.round(((myPlantFertilizer+myPlantWater)*100)/200);
        LifeValue.setText((myPlantLifeStatus)+"%");
    }
    //display the image of the action either fertilizer or water for a certain time with a countdown
    public void showAction(int time, final int action){
        final ImageView plantView = findViewById(R.id.action);
        new CountDownTimer(time,1000){
            @Override
            public void onTick(long pp) {
                plantView.setVisibility(View.VISIBLE);
                plantView.setImageResource(plantActions[action]);
            }
            @Override
            public void onFinish() {
                plantView.setImageResource(plantImageArray[myPlantEvolutionStatus]);
                plantView.setVisibility(View.INVISIBLE);
            }
        }.start();
    }
}