package edu.rosehulman.everhadg.golfballstand;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import edu.rosehulman.me435.AccessoryActivity;

public class GolfBallStandMain extends AccessoryActivity {

    private ToggleButton mToggle1,mToggle2,mToggle3;
    private TextView mColor1,mColor2,mColor3,mCorrectionText,mColorSelectText;
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

        mColor1 = findViewById(R.id.ball1Label);
        mColor2 = findViewById(R.id.ball2Label);
        mColor3 = findViewById(R.id.ball3Label);
        mCorrectionText = findViewById(R.id.correctionLabel);
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
        }
    }

    public void setBallColorText(int location, String color){
        switch(location) {
            case 1:
                mColor1.setText(color);
                break;
            case 2:
                mColor2.setText(color);
                break;
            case 3:
                mColor3.setText(color);
            default:
                Toast.makeText(this, "Location Error", Toast.LENGTH_LONG);
                break;
        }
    }

    public void setBallToChange(int location){
        mBallToChange = location;
    }

    public void toggleBall1(View view) {
    }

    public void toggleBall2(View view) {
    }

    public void toggleBall3(View view) {
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
    }
}
