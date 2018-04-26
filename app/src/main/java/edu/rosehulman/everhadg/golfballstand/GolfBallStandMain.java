package edu.rosehulman.everhadg.golfballstand;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.HashMap;

import edu.rosehulman.me435.AccessoryActivity;

public class GolfBallStandMain extends AccessoryActivity {
    public enum Category{
        RED_GREEN,YELLOW_BLUE,WHITE_BLACK,NONE
    }

    private ToggleButton mToggle1,mToggle2,mToggle3;
    private HashMap<Category, Integer> mBallMap;
    private Boolean[] mIsWhite;
    private TextView mColorSelectText;
    private TextView[] mBallColors;
    private int mBallToChange,mColorIndex;
    private String[] mColorCorrection = {"None", "White","Black","Blue","Red","Green","Yellow"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_golf_ball_stand_main);

        mToggle1 = findViewById(R.id.ball1Toggle);
        mToggle1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    // Toggle is enabled
                    mToggle2.setChecked(false);
                    mToggle2.setChecked(false);
                    setBallToChange(1);
                } else {
                    // Toggle is disabled
                }
            }
        });
        mToggle2 = findViewById(R.id.ball2Toggle);
        mToggle2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    mToggle1.setChecked(false);
                    mToggle3.setChecked(false);
                    setBallToChange(2);
                    // Toggle is enabled
                } else {
                    // Toggle is disabled
                }
            }
        });
        mToggle3 = findViewById(R.id.ball3Toggle);
        mToggle3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    // Toggle is enabled
                    mToggle1.setChecked(false);
                    mToggle2.setChecked(false);
                    setBallToChange(3);
                } else {
                    // Toggle is disabled
                }
            }
        });
        mBallMap = new HashMap<>();
        mIsWhite = new Boolean[3];
        mBallColors = new TextView[3];
        mBallColors[0] = findViewById(R.id.ball1Label);
        mBallColors[1] = findViewById(R.id.ball2Label);
        mBallColors[2] = findViewById(R.id.ball3Label);
        mColorSelectText = findViewById(R.id.colorSelectLabel);

    }

    public void performBallTest(View view) {
        sendCommand("CUSTOM COLOR DETECT");
    }

    @Override
    protected void onCommandReceived(String receivedCommand) {
        super.onCommandReceived(receivedCommand);
        Toast.makeText(this, "Received " + receivedCommand, Toast.LENGTH_SHORT).show();
        if (receivedCommand.contains("Location")) {
            String[] temp = receivedCommand.split(" ");
            int location = Integer.parseInt(temp[1]);
            int ballnum= Integer.parseInt(temp[2]) - 1;
            String ball;
            switch (ballnum) {
                case -1:
                    ball = "none";
                    break;
                case 0:
                    ball = "black";
                    break;
                case 1:
                    ball = "blue";
                    break;
                case 2:
                    ball = "green";
                    break;
                case 3:
                    ball = "red";
                    break;
                case 4:
                    ball = "yellow";
                    break;
                case 5:
                    ball = "white";
                    break;
                default:
                    ball = "Error";
                    break;
            }
            setBallColorText(location,ball);
            populateBallMap();
        }
    }

    public void setBallColorText(int location, String color){
        mBallColors[location-1].setText(color);
    }

    public void populateBallMap(){
        mBallMap = new HashMap<>();
        for(int i=0; i<mBallColors.length; i++) {
            String temp = mBallColors[i].getText().toString());
            if (temp.equalsIgnoreCase("Blue")||temp.equalsIgnoreCase("Yellow")){
               mBallMap.put(Category.YELLOW_BLUE,i);
            } else if (temp.equalsIgnoreCase("Red")||temp.equalsIgnoreCase("Green")){
                mBallMap.put(Category.RED_GREEN,i);
            } else if (temp.equalsIgnoreCase("White")||temp.equalsIgnoreCase("Black")) {
                mBallMap.put(Category.WHITE_BLACK, i);
            }
        }
    }

    public void setBallToChange(int location){
        mBallToChange = location;
    }

    public void shiftColorLeft(View view){
        mColorIndex--;
        if(mColorIndex<0){
            mColorIndex = 6;
        }
        mColorSelectText.setText(mColorCorrection[mColorIndex]);
    }

    public void shiftColorRight(View view){
        mColorIndex++;
        if(mColorIndex>6){
            mColorIndex = 0;
        }
        mColorSelectText.setText(mColorCorrection[mColorIndex]);
    }

    public void updateColor(View view){
        setBallColorText(mBallToChange,mColorCorrection[mColorIndex]);
        populateBallMap();
    }

    public void goProgram(View view) {
        populateBallMap();
        dropBall(mBallMap.get(Category.YELLOW_BLUE));
        int wBIndex = mBallMap.get(Category.WHITE_BLACK);
        if (mBallColors[wBIndex].getText().toString().equalsIgnoreCase("White")){
            lazyToast("Drop off White Ball");
        } else {
            lazyToast("Don't Drop off Black Ball");
        }
        dropBall(mBallMap.get(Category.RED_GREEN));
    }

    public void dropBall(int position){
        //TODO send command to arm to drop off ball at position
        lazyToast("Drop Ball" + position);
    }

    public void lazyToast(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }
}
