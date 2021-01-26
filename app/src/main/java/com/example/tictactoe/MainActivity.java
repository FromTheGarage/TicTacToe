package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.os.Bundle;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];  //board of 9 buttons

    private int roundCount; //
    private int p1Points;
    private int p2Points;


    private TextView textViewPlayer1;
    private TextView textViewPlayer2;
    private boolean p1Turn = true;  //false when it is second player's turn


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayer1 = findViewById(R.id.p1_view);
        textViewPlayer2 = findViewById(R.id.p2_view);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                //buttons[i][j].setOnClickListener(this); BUG: Java can't find buttons by id
            }
        }



        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

            } //end onClick()

        });
    } //end onCreate()

    @Override
    public void onClick(View v) {
        if(!((Button) v).getText().toString().equals("")) {
            return;
        }

        if(p1Turn){ //Player 1 is X's, Player 2 is O's
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }

        roundCount++;   //Increment round after both players make a move

        if(checkForWin()) {
            if(p1Turn){
                p1Wins();
            } else {
                p2Wins();
            }
        } else if(roundCount == 9) {
            draw();
        } else{
            p1Turn = !p1Turn;
        }
    } //end onClick()


private boolean checkForWin() {
    String[][] field = new String[3][3];

    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
            field[i][j] = buttons[i][j].getText().toString();
        } //end loop
    } //end loop

    for (int i = 0; i < 3; i++) {
        if (field[i][0].equals(field[i][1])
                && field[i][0].equals(field[i][2])
                && !field[i][0].equals("")) {   //horizontal victory
            return true;
        } //end if
    } //end loop

    for(int i = 0; i < 3; i++){
        if(field[0][i].equals(field[1][i])
            && field[0][i].equals(field[2][i])
            && !field[0][i].equals("")) {   //vertical victory
            return true;
        } //end if
    } //end loop

    if( field[0][0].equals(field[1][1])
        && field[0][0].equals(field[2][2])
        && !field[0][0].equals("")) { //diagonal victory (top left, middle, bottom right)
        return true;
    } //end if

    if (field[0][2].equals(field[1][1])
        && field[0][2].equals(field[2][0])
        && !field[0][2].equals("")) {   //diagonal victory (bottom left, middle, top right)
        return true;
    } //end if

    return false;
}

private void p1Wins(){
        p1Points++;
    Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show();
    updatePointsText();
    resetBoard();

} //end p1Wins()

private void p2Wins(){
        p2Points++;
    Toast.makeText(this, "reset" +
            "Player 2 wins!", Toast.LENGTH_SHORT).show();
    updatePointsText();
    resetBoard();

} //end p2Wins()

private void draw(){
    Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
    resetBoard();

} //end draw()

private void updatePointsText(){
        textViewPlayer1.setText("Player 1: " + p1Points);
        textViewPlayer2.setText("Player 2: " + p2Points);
} //end updatePointsText()

private void resetBoard(){
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }

        roundCount = 0;
        p1Turn = true;
} //end resetBoard()

private void resetGame(){
        p1Points = 0;
        p2Points = 0;
        updatePointsText();
        resetBoard();
} //end resetGame()

@Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        p1Points = savedInstanceState.getInt("p1Points");
        p2Points = savedInstanceState.getInt("p2Points");
        p1Turn = savedInstanceState.getBoolean("p1Turn");
} //end onRestoreInstanceState()

} //end class MainActivity
