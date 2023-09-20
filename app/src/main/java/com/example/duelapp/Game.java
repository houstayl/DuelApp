package com.example.duelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.text.Html;
import android.text.Spannable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class Game extends AppCompatActivity {


    private int numGames = MainActivity.switches.length, player1Score = 0, player2Score = 0;
    private Button p1Buttons[] = new Button[4];
    private Button p2Buttons[] = new Button[4];//top left, top right, bottom left, bottom right
    private TextView textViewPlayer1Prompt, textViewPlayer2Prompt, textViewPlayer1Data, textViewPlayer2Data, player1ScoreTV, player2ScoreTV;
    private Color color = new Color();
    private String answer = "";
    private String prompt = "", data = "";
    private String options[] = new String[4];//answer stored at [0]
    private ArrayList<GameMode> games;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        numGames = Settings.numGames();
        games = new ArrayList<GameMode>(numGames);
        setContentView(R.layout.activity_game);
        p1Buttons[0] = (Button)findViewById(R.id.Player1TopLeft);
        p1Buttons[1] = (Button)findViewById(R.id.Player1TopRight);
        p1Buttons[2] = (Button)findViewById(R.id.Player1BottomLeft);
        p1Buttons[3] = (Button)findViewById(R.id.Player1BottomRight);


        p2Buttons[0] = (Button)findViewById(R.id.Player2TopLeft);
        p2Buttons[1] = (Button)findViewById(R.id.Player2TopRight);
        p2Buttons[2] = (Button)findViewById(R.id.Player2BottomLeft);
        p2Buttons[3] = (Button)findViewById(R.id.Player2BottomRight);

        textViewPlayer1Prompt = (TextView)findViewById(R.id.textViewPlayer1Prompt);
        textViewPlayer2Prompt = (TextView)findViewById(R.id.textViewPlayer2Prompt);
        textViewPlayer1Data = (TextView)findViewById(R.id.textViewPlayer1Data);
        textViewPlayer2Data = (TextView)findViewById(R.id.textViewPlayer2Data);

        player1ScoreTV = (TextView)findViewById(R.id.player1ScoreTV);
        player1ScoreTV.setText(""+ player1Score);
        player2ScoreTV = (TextView)findViewById(R.id.player2ScoreTV);
        player2ScoreTV.setText("" + player2Score);
        if(MainActivity.switches[0] == true) {
            games.add(new Spelling());
        }
        if(MainActivity.switches[1] == true) {
            games.add(new MentalMath());
        }
        if(MainActivity.switches[2] == true) {
            games.add(new NthLetter());
        }
        if(MainActivity.switches[3] == true) {
            games.add(new MostCommonColorOfCharacterInWord());
        }
        if(MainActivity.switches[4] == true) {
            games.add(new LengthOfWord());
        }
        if(MainActivity.switches[5] == true) {
            games.add(new ColoredTextOfWordColors());
        }
        playGame();
    }

    public void playGame(){
        if(player1Score > 9 || player2Score > 9){
            displayWinGame();
            return;
        }
        enableButtons();
        int gameNo = (int)(Math.random() * numGames);
        String buttonText[] = games.get(gameNo).generateOptions();
        answer = buttonText[0];
        int swap = (int)(Math.random() * p1Buttons.length);
        buttonText[0] = buttonText[swap];
        buttonText[swap] = answer;
        if(games.get(gameNo) instanceof ColoredTextOfWordColors || games.get(gameNo) instanceof  MostCommonColorOfCharacterInWord){
            displayGameDifferentTextColors(buttonText, games.get(gameNo).prompt, games.get(gameNo).data);
        }
        else{
            displayGame(buttonText, games.get(gameNo).prompt, games.get(gameNo).data);
        }

    }
    public void displayWinGame(){
        player1ScoreTV.setText("" + player1Score);
        player2ScoreTV.setText("" + player2Score);
        String player1 = "You win!", player2 = "You lose!";
        if(player2Score > 9){
            player1 = "You lose!";
            player2 = "You win!";
        }
        textViewPlayer1Data.setText(player1);
        textViewPlayer1Prompt.setText(player1);
        textViewPlayer2Prompt.setText(player2);
        textViewPlayer2Data.setText(player2);
        for(int i = 0; i < p1Buttons.length; i++){
            p1Buttons[i].setText(player1);
            p2Buttons[i].setText(player2);
        }
    }
    public void displayGame(String options[], String prompt, String data) {
        player1ScoreTV.setText("" + player1Score);
        player2ScoreTV.setText("" + player2Score);
        textViewPlayer2Data.setText(data);
        textViewPlayer1Data.setText(data);
        textViewPlayer2Prompt.setText(prompt);
        textViewPlayer1Prompt.setText(prompt);

        for(int i = 0; i < options.length; i++) {
            p1Buttons[i].setText(options[i]);
            p2Buttons[i].setText(options[i]);
        }

    }

    public void displayGameDifferentTextColors(String options[], String prompt, String data) {
        player1ScoreTV.setText("" + player1Score);
        player2ScoreTV.setText("" + player2Score);
        textViewPlayer2Data.setText(Html.fromHtml(data));
        textViewPlayer1Data.setText(Html.fromHtml(data));
        textViewPlayer2Prompt.setText(prompt);
        textViewPlayer1Prompt.setText(prompt);

        for(int i = 0; i < options.length; i++) {
            p1Buttons[i].setText(options[i]);
            p2Buttons[i].setText(options[i]);
        }

    }


    public void player1ButtonClick(View v){
        Button p1Button = (Button)v;
        if( p1Button.getText().equals(answer)){//If they hit the right button

            p1Button.setBackgroundColor(Color.GREEN);//Change the button to green
            //p1Button.invalidate();
            disablePlayer1Buttons();
            disablePlayer2Buttons();
            //Move on to next game and freeze other players button
            player1Score++;//Increase player score
            //If player has 10 points, Player wins
            //Delay for one second
            //Show next game title for one second
            //SystemClock.sleep(1000);
            playGame();
        }
        else{//if they hit the wrong button
            p1Button.setBackgroundColor(Color.RED);//change button to red
            //p1Button.invalidate();
            disablePlayer1Buttons();
            if(!p2Buttons[0].isEnabled()){//If other players buttons are disable -> go to next game
                //SystemClock.sleep(1000);
                playGame();
            }

        }
    }

    public void player2ButtonClick(View v){
        Button p2Button = (Button)v;
        if( p2Button.getText().equals(answer)){//If they hit the right button
            p2Button.setBackgroundColor(Color.GREEN);//Change the button to green
            //p2Button.invalidate();
            disablePlayer2Buttons();
            disablePlayer1Buttons();
            //Move on to next game and freeze other players button
            player2Score++;//Increase player score
            //If player has 10 points, Player wins
            //Delay for one second
            //Show next game title for one second
            //SystemClock.sleep(1000);
            playGame();
        }
        else{//if they hit the wrong button
            p2Button.setBackgroundColor(Color.RED);//change button to red
            //p2Button.invalidate();
            disablePlayer2Buttons();
            if(!p1Buttons[0].isEnabled()){//If other players buttons are disabled -> go to next game
                //SystemClock.sleep(1000);
                playGame();
            }

        }
    }

    public void enableButtons(){
        for(int i = 0; i < p1Buttons.length; i++){
            p1Buttons[i].setEnabled(true);
            p2Buttons[i].setEnabled(true);
            p1Buttons[i].setBackgroundColor(getResources().getColor(R.color.black));
            p2Buttons[i].setBackgroundColor(getResources().getColor(R.color.black));
        }
    }
    public void disablePlayer1Buttons(){
        for(int i = 0; i < p1Buttons.length; i++){
            p1Buttons[i].setEnabled(false);
        }
    }
    public void disablePlayer2Buttons(){
        for(int i = 0; i < p2Buttons.length; i++){
            p2Buttons[i].setEnabled(false);
        }
    }

}


abstract class GameMode {

    protected String data, prompt;
    protected String[] options;

    public GameMode(String data, String prompt) {
        this.data = data;
        this.prompt = prompt;
        this.options = new String[4];
    }

    public abstract String[] generateOptions();


}



class MentalMath extends GameMode{
    public MentalMath() {
        super("", "What is the result of the following arithmetic operation");
    }

    public String[] generateOptions() {
        int[] options = getResultAndChoices();
        String optionsToString[] = new String[options.length];
        for(int i = 0; i < options.length; i++) {
            optionsToString[i] = options[i] + " ";
        }
        return optionsToString;
    }
    public int[] getResultAndChoices(){
        Random r = new Random();
        int operation = r.nextInt(4);
        int a = 0, b = 0, result = 0;
        String operationChar = "";
        if(operation == 0){ //addition    //1 to 99
            a = r.nextInt(99) + 1;
            b = r.nextInt(99) + 1;
            result = a + b;
            operationChar = " + ";
        }
        else if(operation == 1){//subtraction   1 to 99
            a = r.nextInt(99) + 1;
            b = r.nextInt(99) + 1;
            result = a - b;
            operationChar = " - ";
        }
        else if(operation == 2){//multiplication   1 to 12
            a = r.nextInt(12) + 1;
            b = r.nextInt(12) + 1;
            result = a * b;
            operationChar = " * ";
        }
        else if(operation == 3){//division 1 to 12
            a = r.nextInt(12) + 1;
            b = r.nextInt(12) + 1;
            result = a * b;
            operationChar = " / ";

            int temp = a;//Reordering so that we can always have an integer result for division
            a = result;
            result = temp;
        }
        super.data = a + operationChar + b;
        return randomChoices(result);
    }
    public int[] randomChoices(int answer){//Takes an answer and gives similar answers and returns an array where the last index is the answer and the other 3 indexas are false answers.
        int allChoices[] = new int[4];
        int choices[] = {10, -10, 1, -1, -2, 2};
        for(int i = 1; i < allChoices.length; i++){
            int rand = (int)Math.random() * (choices.length - i) + 1;
            allChoices[i] = answer + choices[rand];
            choices[rand] = choices[choices.length - i - 1];
        }
        allChoices[0] = answer;
        return allChoices;


    }

}



class Spelling extends GameMode{ //which word is spelled right/wrong all i before e or e before i words and switch them to be opposite

    public Spelling() {
        super("", "Select the word that is spelled incorrectly");
    }

    public String[] generateOptions() {
        String options[] = words();
        return options;
    }


    Random r = new Random();
    String selectedWords[] = new String[4];
    int wrongLetterIndex = 0;

    public String[] words() {
        String wordsie[] = {"achieve", "believe", "cashier", "chief", "field", "grief", "relieved", "shriek", "tier", "yield", "ancient", "concierge", "conscience", "deficient", "efficient", "fancies", "glaciers", "policies", "science", "society"};
        String wordsei[] = {"ceiling", "conceit", "conceive", "deceit", "deceive", "inconceivable", "perceive", "receipt", "receive", "transceiver", "beige", "eight", "foreign", "forfeit", "height", "neighbor", "seize", "vein", "weight", "weird"};
        if (r.nextInt(2) == 0) {//50 % chane to get 3 ies and one ei with one ie switched
            for (int i = 0; i < 3; i++) {
                int rand = (int) (r.nextInt(wordsie.length - i));
                selectedWords[i] = wordsie[rand];
                wordsie[rand] = wordsie[wordsie.length - i - 1];
            }
            selectedWords[3] = wordsei[r.nextInt(wordsei.length)];
            selectedWords[0] = changeIeToEi(selectedWords[0]);//Switching the first ie to ei
        } else {//50 % chance to get 3 eis and one ie with one ei switched
            for (int i = 0; i < 3; i++) {
                int rand = (int) (r.nextInt(wordsei.length - i));
                selectedWords[i] = wordsei[rand];
                wordsei[rand] = wordsei[wordsei.length - i - 1];
            }
            selectedWords[3] = wordsie[r.nextInt(wordsie.length)];
            selectedWords[0] = changeEiToIe(selectedWords[0]);//Switching the first ei to ie
        }
        return selectedWords;
    }

    public String changeIeToEi(String ie){
        for(int i = 0; i < ie.length() - 1; i++){
            if(ie.charAt(i) == 'i' && ie.charAt(i + 1) == 'e'){
                ie = ie.substring(0, i) + 'e' + 'i' + ie.substring(i + 2);
            }
        }
        return ie;
    }
    public String changeEiToIe(String ei){
        for(int i = 0; i < ei.length() - 1; i++){
            if(ei.charAt(i) == 'e' && ei.charAt(i + 1) == 'i'){
                ei = ei.substring(0, i) + 'i' + 'e' + ei.substring(i + 2);
            }
        }
        return ei;
    }
}
class NthLetter extends GameMode{//What is the nth letter of a word with length greater than 4


    public NthLetter() {
        super("The word", "What is the <nth letter> of the word");
    }

    public String[] generateOptions() {
        super.data = getWord();
        String options[] = getNthLetters(super.data);
        return options;
    }
    public String getWord(){
        char selections[] = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        String word = "";
        for(int i = 0; i < 12; i++){
            int rand = ((int)(Math.random() * (selections.length - i)));
            word += selections[rand];
            selections[rand] = selections[selections.length - i - 1];
        }
        return word;
    }

    public String[] getNthLetters(String word){
        int allChoices[] = new int[word.length()];
        for(int i = 0; i < word.length(); i++){
            allChoices[i] = i;
        }
        String convertIntToString[] = {"1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "8th", "9th", "10th", "11th", "12th"};
        int indexes[] = new int[4];
        for(int i = 0; i < indexes.length; i++){
            int rand = (int)(Math.random() * (allChoices.length - i));
            indexes[i] = allChoices[rand];
            allChoices[rand] = allChoices[allChoices.length - i - 1];
        }
        super.prompt = "What is the " + convertIntToString[indexes[0]] + " letter of the " + word.length() + " letter word?";
        String indexesToChars[] = new String[4];
        for(int i = 0; i < indexes.length; i++){
            indexesToChars[i] = word.charAt(indexes[i]) + "";
        }
        return indexesToChars;
    }

}
class LengthOfWord extends GameMode{//What is the length of the word

    public LengthOfWord(){
        super("the word", "What is the length of the word");
    }
    public String[] generateOptions(){
        super.data = getWord();
        int[] options = getSimilarOptions(super.data.length());
        String optionsToString[] = new String[options.length];
        for(int i = 0; i < options.length; i++) {
            optionsToString[i] = options[i] + " ";
        }
        return optionsToString;
    }
    public String getWord(){
        String subWordsBeginning[] = {"sub", "prep", "dec", "bic", "trans", "inter", "super", "dis", "con", "mal"};
        String subWordsMiddle1[] = {"oppo", "appa", "ede", "ide", "uta", "ine", "umi", "awe", "ive", "oku"};
        String subWordsMiddle2[] = {"mui", "noe", "vei", "deo", "lou", "leo", "geo", "cei", "que", "qui"};
        String subWordsEnd[] = {"tion", "sion", "ment", "ness", "ship", "hood", "ful", "less", "like", "ward"};
        String word = subWordsBeginning[(int)(Math.random() * 10)] + subWordsMiddle1[(int)(Math.random()* 10)] + subWordsMiddle2[(int)(Math.random()* 10)] +subWordsEnd[(int)(Math.random() * 10)];
        return word;
    }

    public int[] getSimilarOptions(int length){
        int allChoices[] = new int[4];
        int choices[] = {1, -1, -2, 2, 3, -3};
        for(int i = 1; i < allChoices.length; i++){
            int rand = (int)Math.random() * (choices.length - i) + 1;
            allChoices[i] = length + choices[rand];
            choices[rand] = choices[choices.length - i - 1];
        }
        allChoices[0] = length;
        return allChoices;
    }
}

class ColoredTextOfWordColors extends GameMode{//Color of a word that is also a color
    private int answerIndex;
    public ColoredTextOfWordColors(){
        super("A color or a word", "prompt");
    }
    public String[] generateOptions(){
        String options[] = getWordsAndColors();
        return options;
    }
    public String[] getWordsAndColors(){//returns array with color, color, hexcolor, hexcolor
        String words[] = {"Yellow", "Blue", "Green", "White", "Brown", "Purple", "Orange"};
        String hexValues[] = {"#FFFF00", "#0000FF", "#00FF00", "#FFFFFF", "#964B00", "#A020F0", "#FFA500"};
        String options[] = new String[4]; // left word, right word, left word color, right word color
        for(int i = 0; i < 4; i++){
            int rand = (int)(Math.random() * (words.length - i));
            options[i] = words[rand];
            words[rand] = words[words.length - i - 1];
        }
        String displayString = "<font color ="  + getHexValue(options[2]) + ">" + options[0] + "</font>   " + "<font color ="  + getHexValue(options[3]) + ">" + options[1] + "</font>   "; //textcolor word
        super.data = displayString;

        int answerIndex = 0;
        if((int)(Math.random() * 2) == 0){//Choose the word
            int selection = (int)(Math.random() * 2);
            if(selection == 0){
                super.prompt = "What is the word on the left?";
                answerIndex = 0;
            }
            else{
                super.prompt = "What is the word on the right?";
                answerIndex = 1;
            }
        }
        else{//Choose the color of the word
            int selection = (int)(Math.random() * 2);
            if(selection == 0){
                super.prompt = "What is the color of the word on the left?";
                answerIndex = 2;
            }
            else{
                super.prompt = "What is the color of the word on the right?";
                answerIndex = 3;
            }
        }
        String temp = options[0];
        options[0] = options[answerIndex];
        options[answerIndex] = temp;
        return options;
    }

    public String getHexValue(String color){
        if(color.equals("Yellow")){
            return "#FFFF00";
        }
        else if(color.equals("Blue")){
            return "#0000FF";
        }
        else if(color.equals("Green")){
            return "#00FF00";
        }
        else if(color.equals("White")){
            return "#FFFFFF";
        }
        else if(color.equals("Brown")){
            return "#964B00";
        }
        else if(color.equals("Purple")){
            return "#A020F0";
        }
        else{//orange
            return "#FFA500";
        }
    }
}
class MostCommonColorOfCharacterInWord extends GameMode{//Each charcter is one of 4 colors. Select the most common/rare color
    public MostCommonColorOfCharacterInWord(){
        super("A word with different color text", "What color appears 5 times in the 17 letter word?");
    }

    public String[] generateOptions(){
        String word = generateWord();
        String options[] = getColors(word);
        String convertHexToWord[] = new String[options.length];
        for(int i = 0; i < options.length; i++){
            convertHexToWord[i] = convertHexToWord(options[i]);
        }

        return convertHexToWord;
    }

    public String generateWord(){
        char selections[] = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        String word = "";
        for(int i = 0; i < 17; i++){
            int rand = ((int)(Math.random() * (selections.length - i)));
            word += selections[rand];
            selections[rand] = selections[selections.length - i - 1];
        }
        return word;
    }
    public String[] getColors(String word){
        int wordLength = word.length();
        String colors[] = {"#0000FF", "#00FF00", "#FFFFFF", "#A020F0"};//blue green black purple; color stores in index 0 is most common
        String wordCharacterColor[] = new String[wordLength];//last index in array stores the most common color
        for(int i = 0; i < wordLength / 4; i++){
            String colorstemp[] = {"#0000FF", "#00FF00", "#FFFFFF", "#A020F0"};
            for(int j = 0; j < 4; j++){
                int rand = (int)(Math.random() * (colorstemp.length - j));
                wordCharacterColor[4 * i + j] = colorstemp[rand];
                colorstemp[rand] = colorstemp[colorstemp.length - j - 1];
            }
        }
        int rand = (int)(Math.random() * colors.length);
        wordCharacterColor[wordLength - 1] = colors[rand];//fill in last index. this is most common color
        colors[rand] = colors[0];
        colors[0] = wordCharacterColor[wordLength - 1];//store answer in first index

        //shuffle colors
        for(int i = 0; i < wordCharacterColor.length; i++){
            String temp = wordCharacterColor[i];
            int rand2 = (int)(Math.random() * wordCharacterColor.length);
            wordCharacterColor[i] = wordCharacterColor[rand];
            wordCharacterColor[rand] = temp;
        }

        //format string so that is has colors
        String coloredWord = "";
        for(int i = 0; i < wordLength; i++){
            coloredWord += "<font color =" + wordCharacterColor[i] + ">" + word.charAt(i) + "</font>";
        }
        super.data = /*Html.fromHtml*/(coloredWord);

        return colors;
    }

    public String convertHexToWord(String hexValue){
        if(hexValue.equals("#0000FF")){
            return "Blue";
        }
        else if(hexValue.equals("#00FF00")){
            return "Green";
        }
        else if(hexValue.equals("#FFFFFF")){
            return "White";
        }
        else{
            return "Purple";
        }
    }



}


