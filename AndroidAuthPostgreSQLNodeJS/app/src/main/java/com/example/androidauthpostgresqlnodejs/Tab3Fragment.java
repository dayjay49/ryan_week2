package com.example.androidauthpostgresqlnodejs;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Tab3Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Tab3Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab3Fragment extends Fragment {

    ImageView iv_card_left, iv_card_right;
    TextView tv_score_left, tv_score_right, tv_bet_left, tv_bet_right;
    Button b_deal, b_reset, b_leftbet, b_rightbet;


    Random r, rr;


    int deckCount = 0;
    int betLeft = 0;
    int betRight = 0;
    int leftScore = 50;
    int rightScore = 50;
    String[] shapes = {"clubs", "diamonds", "hearts", "spades"};
    List<String> leftDeck = new ArrayList<String>();
    List<String> rightDeck = new ArrayList<String>();
    List<String> wholeDeck = new ArrayList<String>();
    List<Integer> leftDeckNumbers = new ArrayList<Integer>();
    List<Integer> rightDeckNumbers = new ArrayList<Integer>();

    MediaPlayer mp = new MediaPlayer();

    public Tab3Fragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.blank_tab, container, false);

//        Button Play = (Button)

//        iv_card_left = (ImageView) view.findViewById(R.id.iv_card_left);
//        iv_card_right = (ImageView) view.findViewById(R.id.iv_card_right);
//        tv_score_left = (TextView) view.findViewById(R.id.tv_score_left);
//        tv_score_right = (TextView) view.findViewById(R.id.tv_score_right);
//        tv_bet_left = (TextView) view.findViewById(R.id.bet_score_left);
//        tv_bet_right = (TextView) view.findViewById(R.id.bet_score_right);
//        b_deal = (Button) view.findViewById(R.id.b_deal);
//        b_reset = (Button) view.findViewById(R.id.b_reset);
//        b_leftbet = (Button) view.findViewById(R.id.left_bet);
//        b_rightbet = (Button) view.findViewById(R.id.right_bet);
//
////        b_deal.setOnClickListener();
//
//        r = new Random();
//        rr = new Random();
//
//        shuffleDeck();
//
//        b_leftbet.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (betLeft <= leftScore-5) {
//                    betLeft += 5;
//                    tv_bet_left.setText(String.valueOf(betLeft));
//                } else {
//                    Toast.makeText(requireContext(), "YOU CAN'T BET MORE THAN YOUR SCORE!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        b_rightbet.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (betRight <= rightScore-5) {
//                    betRight += 5;
//                    tv_bet_right.setText(String.valueOf(betRight));
//                } else {
//                    Toast.makeText(requireContext(), "YOU CAN'T BET MORE THAN YOUR SCORE!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        b_deal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //generate the two card numbers
////                int leftNumber = r.nextInt(13) + 2; //this is for cards 2 - 14
////                int rightNumber = r.nextInt(13) + 2; //this is for cards 2 - 14
////                String leftShape = shapes[rr.nextInt(3)];
////                String rightShape = shapes[rr.nextInt(3)];
////                String leftCard = leftShape + leftNumber;
////                String rightCard = rightShape + rightNumber;
//                if (betRight==0 || betLeft==0) {
//                    Toast.makeText(requireContext(), "PLEASE BET AT LEAST 5!!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                else {
//                    if (deckCount == 26) {
////                    Toast.makeText(requireContext(), "RAN OUT OF CARDS!!! PLEASE RESET!!!", Toast.LENGTH_LONG).show();
//                        if (leftScore > rightScore) {
//                            Toast.makeText(requireContext(), "LEFT SIDE WON!!", Toast.LENGTH_LONG).show();
//                            deckCount = 27;
//                        } else if (leftScore < rightScore) {
//                            Toast.makeText(requireContext(), "RIGHT SIDE WON!!", Toast.LENGTH_LONG).show();
//                            deckCount = 27;
//
//                        } else {
//                            Toast.makeText(requireContext(), "TIED UP!!!", Toast.LENGTH_LONG).show();
//                            deckCount = 27;
//
//                        }
//
//                    } else if (deckCount == 27) {
////                    Toast.makeText(requireContext(), "GAME IS OVER!!! PLEASE RESET!!!", Toast.LENGTH_LONG).show();
//                        Toast.makeText(requireContext(), "RAN OUT OF CARDS!!! PLEASE RESET!!!", Toast.LENGTH_LONG).show();
//                    } else if (deckCount == 28) {
//                        Toast.makeText(requireContext(), "GAME IS OVER!!! PLEASE RESET!!!", Toast.LENGTH_LONG).show();
//                    } else if (deckCount < 26) {
//
//
//                        ///////////////NEW VERSION/////////////////////////
//                        //Shuffles exactly 52 cards
//
//                        int leftNumber = leftDeckNumbers.get(deckCount);
//                        int rightNumber = rightDeckNumbers.get(deckCount);
//                        String leftCard = leftDeck.get(deckCount);
//                        String rightCard = rightDeck.get(deckCount);
//
//                        //show card images
//                        int leftImage = getResources().getIdentifier(leftCard, "drawable", getActivity().getPackageName());
//                        iv_card_left.setImageResource(leftImage);
//                        int rightImage = getResources().getIdentifier(rightCard, "drawable", getActivity().getPackageName());
//                        iv_card_right.setImageResource(rightImage);
//
//                        deckCount++;
//
//                        //compare cards, add points and display them
//                        if (leftNumber > rightNumber) {
////                        leftScore++;
//                            leftScore += betRight;
//                            rightScore -= betRight;
////                            Toast.makeText(requireContext(), "LEFT WINS THE BET!", Toast.LENGTH_SHORT).show();
////                            SystemClock.sleep(3000);
//                            tv_score_left.setText(String.valueOf(leftScore));
//                            tv_score_right.setText(String.valueOf(rightScore));
//                            String winner = earlyWinner();
//                            if (winner == "leftwon") {
//                                Toast.makeText(requireContext(), "LEFT SIDE WINS THE GAME!!!!!!", Toast.LENGTH_LONG).show();
//                                deckCount = 28;
//                            } else if (winner == "rightwon") {
//                                Toast.makeText(requireContext(), "RIGHT SIDE WINS THE GAME!!!!!!", Toast.LENGTH_LONG).show();
//                                deckCount = 28;
//                            }
//
//                        } else if (leftNumber < rightNumber) {
////                        rightScore++;
//                            rightScore += betLeft;
//                            leftScore -= betLeft;
////                            Toast.makeText(requireContext(), "RIGHT WINS THE BET!", Toast.LENGTH_SHORT).show();
////                            SystemClock.sleep(3000);
//                            tv_score_right.setText(String.valueOf(rightScore));
//                            tv_score_left.setText(String.valueOf(leftScore));
//                            String winner = earlyWinner();
//                            if (winner == "leftwon") {
//                                Toast.makeText(requireContext(), "LEFT SIDE WINS THE GAME!!!!!!", Toast.LENGTH_LONG).show();
//                                deckCount = 28;
//                            } else if (winner == "rightwon") {
//                                Toast.makeText(requireContext(), "RIGHT SIDE WINS THE GAME!!!!!!", Toast.LENGTH_LONG).show();
//                                deckCount = 28;
//                            }
//
//                        } else {
//                            if (leftScore > rightScore) {
//                                Toast.makeText(requireContext(), "LEFT SIDE WINS THE GAME!!!!!!", Toast.LENGTH_LONG).show();
//                                deckCount = 28;
//                            } else if (leftScore < rightScore) {
//                                Toast.makeText(requireContext(), "RIGHT SIDE WINS THE GAME!!!!!!", Toast.LENGTH_LONG).show();
//                                deckCount = 28;
//                            } else {
//                                Toast.makeText(requireContext(), "TIED UP!!!", Toast.LENGTH_LONG).show();
//                                deckCount = 28;
//                            }
//
//                        }
//                    } else {
//                        Toast.makeText(requireContext(), "COUNTING ERROR... PLEASE RESTART AVOCADO", Toast.LENGTH_LONG).show();
//                    }
//                    betLeft = 0;
//                    betRight = 0;
//                    tv_bet_left.setText(String.valueOf(betLeft));
//                    tv_bet_right.setText(String.valueOf(betRight));
//
//                }
//            }
//        });
//
//        b_reset.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //initialize scores
//                leftScore = 50;
//                tv_score_left.setText(String.valueOf(leftScore));
//                rightScore = 50;
//                tv_score_right.setText(String.valueOf(rightScore));
//
//                shuffleDeck();
//
//                Toast.makeText(requireContext(), "DECK SHUFFLED!!!", Toast.LENGTH_SHORT).show();
//
//                //show back of the cards
//                int leftImage = getResources().getIdentifier("avocard1", "drawable", getActivity().getPackageName());
//                iv_card_left.setImageResource(leftImage);
//                int rightImage = getResources().getIdentifier("avocard1", "drawable", getActivity().getPackageName());
//                iv_card_right.setImageResource(rightImage);
//
//
//
//
//            }
//        });
        return view;

//        return inflater.inflate(R.layout.fragment_tab3, container, false);
    }

//    public void shuffleDeck(){
//        int cardnumber;
//        String cardshape;
//        Random rrr = new Random();
//        Random rrrr = new Random();
//        wholeDeck.clear();
//        leftDeck.clear();
//        rightDeck.clear();
//        leftDeckNumbers.clear();
//        rightDeckNumbers.clear();
//
//        deckCount = 0;
//
////        if (wholeDeck != null) wholeDeck.clear();
////        wholeDeck.add("START");
//        for (int i=0; i<52; i++) {
//            do {
//                cardnumber = rrr.nextInt(13) + 2; //this is for cards 2 - 14
//                cardshape = shapes[rrrr.nextInt(4)];
//            } while (wholeDeck.contains(cardshape + cardnumber));
//            wholeDeck.add(cardshape + cardnumber);
//            if (i < 26) {
//                leftDeck.add(cardshape + cardnumber);
//                leftDeckNumbers.add(cardnumber);
//            } else {
//                rightDeck.add(cardshape + cardnumber);
//                rightDeckNumbers.add(cardnumber);
//            }
//        }
//    }
//
////    public String earlyWinner() {
////        if (26-deckCount < leftScore-rightScore) {
////            return "leftwon";
////        } else if (26-deckCount < rightScore - leftScore) {
////            return "rightwon";
////        } else {
////            return "";
////        }
////    }
//
//    public String earlyWinner() {
//        if (leftScore>rightScore && rightScore==0) {
//            return "leftwon";
//        } else if (leftScore<rightScore && leftScore==0) {
//            return "rightwon";
//        } else {
//            return "";
//        }
//    }

}

