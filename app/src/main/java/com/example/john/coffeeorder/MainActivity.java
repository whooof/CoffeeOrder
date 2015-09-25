package com.example.john.coffeeorder;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    int quantity = 0;
    int price = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /**
     * This method is called when the order button is clicked.
     */

    public void addOne(View view) {
        quantity++;
        display(quantity);
    }

    public void subOne(View view) {
        if (quantity == 0) {
            Toast.makeText(getApplicationContext(), "You can't go below 0!!", Toast.LENGTH_SHORT).show();
        } else {
            quantity--;
            display(quantity);
        }
    }


    public void submitOrder(View view) {

        if (quantity == 0) {
            Toast.makeText(getApplicationContext(), "You need to order at least one coffee!", Toast.LENGTH_SHORT).show();
        } else {
            ImageView coffee = (ImageView) findViewById(R.id.coffee);
            coffee.setVisibility(View.VISIBLE);
            TextView summary = (TextView) findViewById(R.id.summary);
            TextView thankYou = (TextView) findViewById(R.id.thankYou);
            thankYou.setText("Thank you! ");
            summary.setText("Thanks for ordering we are preparing your ");
            if (quantity == 1) {
                summary.append("1 coffee");
            } else {
                summary.append(quantity + " coffees");
            }
            displayPrice(calculatePrice());

            new CountDownTimer(30000 * quantity, 1000) {

                TextView timeLeft = (TextView) findViewById(R.id.timeLeft);
                Button order = (Button) findViewById(R.id.order);

                public void onTick(long millisUntilFinished) {
                    timeLeft.setText("Your order will be ready in: \n" + millisUntilFinished / 1000 + "s");
                    order.setEnabled(false);
                }

                public void onFinish() {
                    timeLeft.setText("You can pick up yor order!");
                    order.setEnabled(true);
                }
            }.start();


        }


    }

    /**
     * Calculates price
     *
     * @return total price for ordered coffees
     *
     */

    private int calculatePrice() {
        return price * quantity;
    }

    /**
     * This method displays the total price on the screen.
     *
     * @param price takes calculated price to display
     */
    private void displayPrice(int price) {
        TextView thankYou = (TextView) findViewById(
                R.id.thankYou);
        thankYou.append("\nIt's " + price + "$ in total.");

    }
    /**
     * This method displays the quantity in the EditText view
     *
     * @param quantity how many coffees you've ordered
     */
    private void display(int quantity) {
        EditText display = (EditText) findViewById(
                R.id.display);
        display.setText("" + quantity);

    }

}
