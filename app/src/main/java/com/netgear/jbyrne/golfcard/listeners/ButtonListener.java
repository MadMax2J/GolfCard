package com.netgear.jbyrne.golfcard.listeners;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.netgear.jbyrne.golfcard.R;
import com.netgear.jbyrne.golfcard.controller.TallyScores;

import java.util.ArrayList;


/**
 * Created by jbyrne on 08/10/2014.
 * ButtonListener Class to handle Button Clicks.
 *
 * Suppressed Warnings for Debug...
 */
@SuppressWarnings({"deprecation", "AccessStaticViaInstance"})
public class ButtonListener implements OnClickListener{

    private Activity activity;
    private ArrayList<View> aryScoreViews = new ArrayList<View>();
    private ArrayList<View> aryParViews = new ArrayList<View>();
    private Drawable originalDrawable;      // I use this to store the Original Background
                                            // of my EditText fields.

    public ButtonListener(Activity _activity){
        this.activity = _activity;
        refreshArrays();
        originalDrawable = this.activity.findViewById(R.id.txtScore1).getBackground();
    }

    /**
     * Updates the Class variable ArrayLists with Score and Par data.
     * If in the future I want to deal with additional holes, simply adding them into this method
     * will facilitate the iterating of them later with no additional coding.
     */
    private void refreshArrays(){

        aryScoreViews.clear();
        aryScoreViews.add(this.activity.findViewById(R.id.txtScore1));
        aryScoreViews.add(this.activity.findViewById(R.id.txtScore2));
        aryScoreViews.add(this.activity.findViewById(R.id.txtScore3));
        aryScoreViews.add(this.activity.findViewById(R.id.txtScore4));
        aryScoreViews.add(this.activity.findViewById(R.id.txtScore5));
        aryScoreViews.add(this.activity.findViewById(R.id.txtScore6));
        aryScoreViews.add(this.activity.findViewById(R.id.txtScore7));
        aryScoreViews.add(this.activity.findViewById(R.id.txtScore8));
        aryScoreViews.add(this.activity.findViewById(R.id.txtScore9));
        ///Add more holes here if needed

        aryParViews.clear();
        aryParViews.add(this.activity.findViewById(R.id.lblPar1));
        aryParViews.add(this.activity.findViewById(R.id.lblPar2));
        aryParViews.add(this.activity.findViewById(R.id.lblPar3));
        aryParViews.add(this.activity.findViewById(R.id.lblPar4));
        aryParViews.add(this.activity.findViewById(R.id.lblPar5));
        aryParViews.add(this.activity.findViewById(R.id.lblPar6));
        aryParViews.add(this.activity.findViewById(R.id.lblPar7));
        aryParViews.add(this.activity.findViewById(R.id.lblPar8));
        aryParViews.add(this.activity.findViewById(R.id.lblPar9));
        ///Add more holes here if needed
    }

    /**
     * Handles Click Events for the Clear, Tally and In(hidden in this version) Buttons.
     *
     * @param view a Reference to the Button that triggered this Method.
     */
    @Override
    public void onClick(View view) {

        //Need to be able to Show or Hide these TextViews
        TextView lblRoundScore = (TextView)this.activity.findViewById(R.id.lblRoundScore);
        TextView txtRoundScore = (TextView)this.activity.findViewById(R.id.txtRoundScore);
        TextView lblHoleAvg = (TextView)this.activity.findViewById(R.id.lblHoleAvg);
        TextView txtHoleAvg = (TextView)this.activity.findViewById(R.id.txtHoleAvg);


        //Switch used to allow the same Listener to deal with Multiple Buttons.
        Button clickedBtn = (Button) view;
        switch (clickedBtn.getId()) {

            case R.id.btnClear:
                //Iterate through all the scoreViews, clearing
                //their Values and resetting their Appearance.
                for (View scoreView : aryScoreViews) {
                    TextView txtView = (TextView) scoreView;
                    txtView.setText("");

                    //Not ideal as it's deprecated, but until I figure out a better way...
                    txtView.setBackgroundDrawable(originalDrawable);

                    if(txtView.getId() == R.id.txtScore1){  //Give Focus back to Hole 1.
                        txtView.requestFocus();
                    }
                }

                lblRoundScore.setVisibility(lblRoundScore.INVISIBLE);
                txtRoundScore.setVisibility(txtRoundScore.INVISIBLE);
                lblHoleAvg.setVisibility(lblHoleAvg.INVISIBLE);
                txtHoleAvg.setVisibility(txtHoleAvg.INVISIBLE);

                break;

            case R.id.btnTally:
                //Get the latest data from the Activity and populate
                // Integer arrays that I can pass to the Controller.
                //
                //Finally use the result from the Controller to update
                // the Activity with the Player's Round Score and Hole Average.
                //
                //I also employ Exemption Handling to deal with Invalid Input from the User.

                refreshArrays();

                ArrayList<Integer> aryScores = new ArrayList<Integer>();
                ArrayList<Integer> aryPars = new ArrayList<Integer>();

                for (View parView : aryParViews) {
                    TextView txtView = (TextView) parView;

                    int holePar = Integer.parseInt(txtView.getText().toString());

                    aryPars.add(holePar);

                }

                try {
                    //Do a sanity check that all fields are populated
                    for (View scoreView : aryScoreViews) {
                        TextView txtView = (TextView) scoreView;

                        //If this fails, probably due to an empty field, it'll throw a NumberFormatException
                        int sanityCheck = Integer.parseInt(txtView.getText().toString());
                        if (sanityCheck == -1){ //This should never happen.
                            break;
                        }
                    }

                    for (View scoreView : aryScoreViews) {
                        TextView txtView = (TextView) scoreView;

                        int holeScore = Integer.parseInt(txtView.getText().toString());

                        if (holeScore < 1 || holeScore > 12){
                            throw new Exception("Invalid Score Exception");
                        }

                        aryScores.add(holeScore);

                        if (holeScore > aryPars.get(aryScoreViews.indexOf(scoreView))) {
                            scoreView.setBackgroundColor(0x7DFF0000);       //Pale RED
                        } else if (holeScore < aryPars.get(aryScoreViews.indexOf(scoreView))) {
                            scoreView.setBackgroundColor(0x8000FF00);       //Pale GREEN
                        } else {
                            scoreView.setBackgroundDrawable(originalDrawable);  //Not ideal, but
                                                                                // only solution
                                                                                // for the moment.
                        }

                    }

                    //Results from Controller Class...
                    int result = TallyScores.getInstance().tally(aryPars, aryScores);
                    float holeAverage =
                            TallyScores.getInstance().getAverageScore(aryPars, aryScores);


                    //Display results back to the Activity...
                    txtRoundScore.setText(String.valueOf(result));
                    lblRoundScore.setVisibility(lblRoundScore.VISIBLE);
                    txtRoundScore.setVisibility(txtRoundScore.VISIBLE);

                    txtHoleAvg.setText(String.valueOf(holeAverage));
                    lblHoleAvg.setVisibility(lblHoleAvg.VISIBLE);
                    txtHoleAvg.setVisibility(txtHoleAvg.VISIBLE);
                    break;


                }catch (NumberFormatException exception){
                    //Do Nothing if a Field is Blank.
                    //Button is inactive.
                    break;


                }catch (Exception exception){
                    lblRoundScore.setVisibility(lblRoundScore.INVISIBLE);
                    lblHoleAvg.setVisibility(lblHoleAvg.INVISIBLE);

                    txtRoundScore.setText("Error");
                    txtHoleAvg.setText("Invalid Score");
                    txtRoundScore.setVisibility(txtRoundScore.VISIBLE);
                    txtHoleAvg.setVisibility(txtHoleAvg.VISIBLE);
                    break;
                }


            case R.id.btnIn:
                //Button Hidden in this Version.
                break;
        }
    }
}
