package edu.rosehulman.everhadg.golfballstand;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import edu.rosehulman.me435.AccessoryActivity;

public class GolfBallStandMain extends AccessoryActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_golf_ball_stand_main);
    }

    public void performBallTest(View view) {
        sendCommand("CUSTOM COLOR DETECT");
    }

    @Override
    protected void onCommandReceived(String receivedCommand) {
        super.onCommandReceived(receivedCommand);
        Toast.makeText(this, "Received " + receivedCommand, Toast.LENGTH_SHORT);
        if (receivedCommand.contains("Location")) {
            String[] temp = receivedCommand.split(":");
            Toast.makeText(this,temp[1],Toast.LENGTH_SHORT);
            int location = Integer.parseInt(temp[1]);
            int ballnum = Integer.parseInt(temp[2]);
            String ball;
            switch (ballnum) {
                case -1:
                    ball = "no ball";
                    break;
                case 0:
                    ball = "black ball";
                    break;
                case 1:
                    ball = "blue ball";
                    break;
                case 2:
                    ball = "green ball";
                    break;
                case 3:
                    ball = "red ball";
                    break;
                case 4:
                    ball = "yellow ball";
                    break;
                case 5:
                    ball = "white ball";
                    break;
                default:
                    ball = "Error";
                    break;
            }
            //TODO Change the Textview in response to the return ball color
            Toast.makeText(this,"Location " + location + " has " + ball,Toast.LENGTH_SHORT);
        }
    }
}
