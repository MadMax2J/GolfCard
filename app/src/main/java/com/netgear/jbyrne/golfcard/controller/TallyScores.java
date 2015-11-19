package com.netgear.jbyrne.golfcard.controller;

import java.util.ArrayList;

/**
 * Created by jbyrne on 08/10/2014.
 * TallyScores Controller which doesn't really have to do much.
 */
public class TallyScores {

    private static TallyScores instance;

    private TallyScores(){

    }

    public static TallyScores getInstance(){
        if(instance == null){
            instance = new TallyScores();
        }
        return instance;

    }

    /**
     * Method to Tally the Scores and Return Result
     * @param aryPars   an ArrayList of Integer PAR values for each hole
     * @param aryScores an ArrayList of Integer Player Score values for each hole
     * @return          the Round Score
     */
    public int tally(ArrayList<Integer> aryPars, ArrayList<Integer> aryScores){
        int coursePar = 0;
        int playerScore = 0;

        for (Integer holePar : aryPars) {
            coursePar += holePar;
        }

        for (Integer aryScore : aryScores) {
            playerScore += aryScore;
        }

        return playerScore - coursePar;
    }

    /**
     * Method the return the Average Hole Score (I've seen this in other Golf Apps)
     * @param aryPars   an ArrayList of Integer PAR values for each hole
     * @param aryScores an ArrayList of Integer Player Score values for each hole
     * @return          the Score Average
     */
    public float getAverageScore(ArrayList<Integer> aryPars, ArrayList<Integer> aryScores){
        int playerScore = 0;

        for (Integer aryScore : aryScores) {
            playerScore += aryScore;
        }

        return (float)playerScore / aryPars.size();
    }


}
