package com.example.tictactoe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Represents the internal state of the game
    private TicTacToeGame mGame;

    // Buttons making up the board
    private Button mBoardButtons[];

    // Various text displayed
    private TextView mInfoTextView;
    private TextView mGameInfoTextView;

    // Restart Button
    private Button startButton;

    // Game Over
    Boolean mGameOver;

    // Store button states
    // true for person, false for computer
    // Java默认为false
    private boolean[] mIsHuman = new boolean[9];

    private boolean mIsLastHuman = false;
    private int mDiffClass = 1;

    private int mNumWin = 0;
    private int mNumTie = 0;
    private int mNumLose = 0;

    private static final int REQUEST_CODE = 1;

    private MediaPlayer mediaPlayer;
    private AlertDialog.Builder builder;

    private int winner = 0;

    @Override
    protected void onStart() {
        super.onStart();
        loadPreferences();
    }

    @Override
    protected void onStop() {
        savePreferences();
        // onStop();
        mediaPlayer.stop();
        super.onStop();
    }

    public void savePreferences() {
        SharedPreferences pref = getSharedPreferences("Tic Tac Toe", MODE_PRIVATE);
        pref.edit().putBoolean("b0", mBoardButtons[0].isEnabled()).apply();
        pref.edit().putBoolean("b1", mBoardButtons[1].isEnabled()).apply();
        pref.edit().putBoolean("b2", mBoardButtons[2].isEnabled()).apply();
        pref.edit().putBoolean("b3", mBoardButtons[3].isEnabled()).apply();
        pref.edit().putBoolean("b4", mBoardButtons[4].isEnabled()).apply();
        pref.edit().putBoolean("b5", mBoardButtons[5].isEnabled()).apply();
        pref.edit().putBoolean("b6", mBoardButtons[6].isEnabled()).apply();
        pref.edit().putBoolean("b7", mBoardButtons[7].isEnabled()).apply();
        pref.edit().putBoolean("b8", mBoardButtons[8].isEnabled()).apply();

        pref.edit().putString("s0", String.valueOf(mBoardButtons[0].getText())).apply();
        pref.edit().putString("s1", String.valueOf(mBoardButtons[1].getText())).apply();
        pref.edit().putString("s2", String.valueOf(mBoardButtons[2].getText())).apply();
        pref.edit().putString("s3", String.valueOf(mBoardButtons[3].getText())).apply();
        pref.edit().putString("s4", String.valueOf(mBoardButtons[4].getText())).apply();
        pref.edit().putString("s5", String.valueOf(mBoardButtons[5].getText())).apply();
        pref.edit().putString("s6", String.valueOf(mBoardButtons[6].getText())).apply();
        pref.edit().putString("s7", String.valueOf(mBoardButtons[7].getText())).apply();
        pref.edit().putString("s8", String.valueOf(mBoardButtons[8].getText())).apply();

        pref.edit().putBoolean("h0", mIsHuman[0]).apply();
        pref.edit().putBoolean("h1", mIsHuman[1]).apply();
        pref.edit().putBoolean("h2", mIsHuman[2]).apply();
        pref.edit().putBoolean("h3", mIsHuman[3]).apply();
        pref.edit().putBoolean("h4", mIsHuman[4]).apply();
        pref.edit().putBoolean("h5", mIsHuman[5]).apply();
        pref.edit().putBoolean("h6", mIsHuman[6]).apply();
        pref.edit().putBoolean("h7", mIsHuman[7]).apply();
        pref.edit().putBoolean("h8", mIsHuman[8]).apply();

        pref.edit().putString("textInfo", String.valueOf(mInfoTextView.getText())).apply();
        pref.edit().putBoolean("isGameOver", mGameOver).apply();
        pref.edit().putBoolean("isLastHuman", mIsLastHuman).apply();
        pref.edit().putInt("numWin", mNumWin).apply();
        pref.edit().putInt("numTie", mNumTie).apply();
        pref.edit().putInt("numLose", mNumLose).apply();
        pref.edit().putInt("difClass",mDiffClass).apply();
    }

    public void loadPreferences() {
        SharedPreferences pref = getSharedPreferences("Tic Tac Toe", MODE_PRIVATE);
        mBoardButtons[0].setEnabled(pref.getBoolean("b0", true));
        mBoardButtons[1].setEnabled(pref.getBoolean("b1", true));
        mBoardButtons[2].setEnabled(pref.getBoolean("b2", true));
        mBoardButtons[3].setEnabled(pref.getBoolean("b3", true));
        mBoardButtons[4].setEnabled(pref.getBoolean("b4", true));
        mBoardButtons[5].setEnabled(pref.getBoolean("b5", true));
        mBoardButtons[6].setEnabled(pref.getBoolean("b6", true));
        mBoardButtons[7].setEnabled(pref.getBoolean("b7", true));
        mBoardButtons[8].setEnabled(pref.getBoolean("b8", true));

        mBoardButtons[0].setText(pref.getString("s0", ""));
        mBoardButtons[1].setText(pref.getString("s1", ""));
        mBoardButtons[2].setText(pref.getString("s2", ""));
        mBoardButtons[3].setText(pref.getString("s3", ""));
        mBoardButtons[4].setText(pref.getString("s4", ""));
        mBoardButtons[5].setText(pref.getString("s5", ""));
        mBoardButtons[6].setText(pref.getString("s6", ""));
        mBoardButtons[7].setText(pref.getString("s7", ""));
        mBoardButtons[8].setText(pref.getString("s8", ""));


        mIsHuman[0] = pref.getBoolean("h0", false);
        mIsHuman[1] = pref.getBoolean("h1", false);
        mIsHuman[2] = pref.getBoolean("h2", false);
        mIsHuman[3] = pref.getBoolean("h3", false);
        mIsHuman[4] = pref.getBoolean("h4", false);
        mIsHuman[5] = pref.getBoolean("h5", false);
        mIsHuman[6] = pref.getBoolean("h6", false);
        mIsHuman[7] = pref.getBoolean("h7", false);
        mIsHuman[8] = pref.getBoolean("h8", false);

        for (int i=0; i<mIsHuman.length; i++){
            if (mIsHuman[i]){
                mBoardButtons[i].setTextColor(Color.rgb(0, 200, 0));
            } else{
                mBoardButtons[i].setTextColor(Color.rgb(200, 0, 0));
            }
        }

        mInfoTextView.setText(pref.getString("textInfo", this.getString(R.string.you_first)));
        mGameOver = pref.getBoolean("isGameOver", false);

        for(int i = 0; i < mBoardButtons.length; i++){
            String c = mBoardButtons[i].getText().toString();
            if (!c.equals("") ){
                mGame.SetBoardPreference(i, c.charAt(0));
            }
        }
        mIsLastHuman = pref.getBoolean("isLastHuman", false);
        mNumWin = pref.getInt("numWin", 0);
        mNumTie = pref.getInt("numTie", 0);
        mNumLose = pref.getInt("numLose", 0);
        mDiffClass = pref.getInt("difClass", 1);
        showGameInfo();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGame = new TicTacToeGame();

        mBoardButtons = new Button[mGame.BOARD_SIZE];
        mBoardButtons[0] = (Button) findViewById(R.id.button0);
        mBoardButtons[1] = (Button) findViewById(R.id.button1);
        mBoardButtons[2] = (Button) findViewById(R.id.button2);
        mBoardButtons[3] = (Button) findViewById(R.id.button3);
        mBoardButtons[4] = (Button) findViewById(R.id.button4);
        mBoardButtons[5] = (Button) findViewById(R.id.button5);
        mBoardButtons[6] = (Button) findViewById(R.id.button6);
        mBoardButtons[7] = (Button) findViewById(R.id.button7);
        mBoardButtons[8] = (Button) findViewById(R.id.button8);
        mInfoTextView = (TextView) findViewById(R.id.information);
        mGameInfoTextView = (TextView) findViewById(R.id.gameInfo);

        mGame = new TicTacToeGame();
        startNewGame();
    }

    //--- Set up the game board.
    private void startNewGame() {
        mGameOver = false;
        mGame.clearBoard();
        // mIsHuman = new boolean[9];
        //---Reset all buttons
        for (int i = 0; i < mBoardButtons.length; i++) {
            mBoardButtons[i].setText("");
            mBoardButtons[i].setEnabled(true);
            mBoardButtons[i].setOnClickListener(new ButtonClickListener(i));
            mIsHuman[i] = false;
            // mInfoTextView.setText(R.string.your_turn);
        }

        if(winner == 2 || winner == 3){
            showImg(4);
        }
        winner = 0;
        // play the audio
        playMusic();

        // Check first player of the last game
        changeFirstPlayer();
        if(mIsLastHuman){
            //Human goes first
            mInfoTextView.setText(R.string.you_first);
        } else {
            // mInfoTextView.setText(R.string.enemy_first);
            // SystemClock.sleep(3000);
            // setMove(TicTacToeGame.COMPUTER_PLAYER, 5);
            int move = mGame.getComputerMove(mDiffClass);
            setMove(TicTacToeGame.COMPUTER_PLAYER, move);
            mInfoTextView.setText(R.string.your_turn);
        }
        showGameInfo();
        showDialog();
        builder.create();
        builder.show();
    }

    private void changeFirstPlayer(){
        if(mIsLastHuman){
            mIsLastHuman = false;
        } else{
            mIsLastHuman = true;
        }
    }

    private void showGameInfo(){
        String str = "Win: " + mNumWin + ", Tie: " + mNumTie + ", lose: " + mNumLose;
        mGameInfoTextView.setText(str);
    }

    private void showDialog(){
        builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.stroy_button);
        builder.setMessage(R.string.stroy_description);
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            { }
        });
    }

    private void setMove(char player, int location) {
        mGame.setMove(player, location);
        mBoardButtons[location].setEnabled(false);
        mBoardButtons[location].setText(String.valueOf(player));
        if (player == TicTacToeGame.HUMAN_PLAYER){
            mIsHuman[location] = true;
            mBoardButtons[location].setTextColor(Color.rgb(0, 200, 0));
        } else{
            mBoardButtons[location].setTextColor(Color.rgb(200, 0, 0));
        }
    }

    //--- OnClickListener for Restart a New Game Button
    public void newGame(View v) {
        mediaPlayer.stop();
        startNewGame();
    }

    //--- Play the music
    private void playMusic(){

        Uri uri;

        if(winner == 0){
            uri = Uri.parse("android.resource://"
                    + getPackageName() + "/" + R.raw.sound_background);
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
        }
        if(winner == 1 || winner == 3) {
            uri = Uri.parse("android.resource://"
                    + getPackageName() + "/" + R.raw.sound_lose);
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();
        }
        else if(winner == 2){
            uri = Uri.parse("android.resource://"
                    + getPackageName() + "/" + R.raw.sound_win);
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();
        }
    }

    private void showImg(int choose){
        //SHOW IMAGE
        ImageView image = (ImageView) findViewById(R.id.imageView);
        // System.out.println(choose);

        if (choose == 2) {
            // player win
            image.setImageResource(R.drawable.pic_win);
            image.animate().rotationXBy(360).setDuration(2000);
            // image.animate().scaleX(1.2f).scaleY(1.2f).setDuration(3000);
        } else if (choose == 3) {
            image.setImageResource(R.drawable.pic_lose);
            image.animate().rotationXBy(360).setDuration(2000);
            // image.animate().scaleX(0.8f).scaleY(0.8f).setDuration(3000);
        } else if(choose == 4 || choose == 5){
            image.setImageResource(R.drawable.pic_tie);
            image.animate().rotationXBy(360).setDuration(2000);
            // LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            //        ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
            // image.setLayoutParams(params);
        } else{
            // tie = 1
            image.setImageResource(R.drawable.pic_tie);
        }
    }

    //---Handles clicks on the game board buttons
    private class ButtonClickListener implements View.OnClickListener {
        int location;

        public ButtonClickListener(int location) {
            this.location = location;
        }

        @Override
        public void onClick(View v) {
            if (mGameOver == false) {
                if (mBoardButtons[location].isEnabled()) {
                    setMove(TicTacToeGame.HUMAN_PLAYER, location);
                    //--- If no winner yet, let the computer make a move
                    winner = mGame.checkForWinner();
                    if (winner == 0) {
                        mInfoTextView.setText(R.string.android_turn);
                        int move = mGame.getComputerMove(mDiffClass);
                        setMove(TicTacToeGame.COMPUTER_PLAYER, move);
                        winner = mGame.checkForWinner();
                    }
                    if (winner == 0) {
                        mInfoTextView.setTextColor(Color.rgb(0, 0, 0));
                        mInfoTextView.setText(R.string.your_turn);
                    } else if (winner == 1) {
                        mInfoTextView.setTextColor(Color.rgb(0, 0, 200));
                        mInfoTextView.setText(R.string.rst_tie);
                        mNumTie = mNumTie + 1;
                        showGameInfo();
                        mGameOver = true;
                        mediaPlayer.stop();
                        playMusic();
                        showImg(winner);
                    } else if (winner == 2) {
                        mInfoTextView.setTextColor(Color.rgb(0, 200, 0));
                        mInfoTextView.setText(R.string.rst_win);
                        mNumWin = mNumWin + 1;
                        showGameInfo();
                        mGameOver = true;
                        mediaPlayer.stop();
                        playMusic();
                        showImg(winner);
                    } else {
                        mInfoTextView.setTextColor(Color.rgb(200, 0, 0));
                        mInfoTextView.setText(R.string.rst_lose);
                        mNumLose = mNumLose + 1;
                        showGameInfo();
                        mGameOver = true;
                        mediaPlayer.stop();
                        playMusic();
                        showImg(winner);
                    }

                }
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu;
        // this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_wiki:
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(getString(R.string.wiki_website)));
                startActivity(intent);
                return true;

            case R.id.menu_exit:
                finish();
                return true;

            case R.id.menu_level1:
                mDiffClass = 1;
                mediaPlayer.stop();
                startNewGame();
                return true;

            case R.id.menu_level2:
                mDiffClass = 2;
                mediaPlayer.stop();
                startNewGame();
                return true;

            case R.id.menu_level3:
                mDiffClass = 3;
                mediaPlayer.stop();
                startNewGame();
                return true;
        }
        return false;
    }
}