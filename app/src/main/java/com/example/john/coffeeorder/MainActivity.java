package com.example.john.coffeeorder;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int quantity = 1;
    int default_price = 3;
    int price = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayPrice(calculatePrice());

        RadioGroup coffeeSize = (RadioGroup) findViewById(R.id.coffeeSize);
        coffeeSize.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                displayPrice(calculatePrice());
            }

        });
    }

    /**
     * Quick Toast generator using two params
     *
     * @param text   the text of the Toast
     * @param longer if true then Toast length will be set to long otherwise to short
     */

    private void toasty(String text, boolean longer) {
        int length;
        if (longer) {
            length = Toast.LENGTH_LONG;
        } else {
            length = Toast.LENGTH_SHORT;
        }
        Toast.makeText(getApplicationContext(), text, length).show();

    }

    /**
     * This method is called when the order button is clicked.
     */

    public void addOne(View view) {
        quantity++;
        display(quantity);
        displayPrice(calculatePrice());
    }

    public void subOne(View view) {
        if (quantity == 1) {
            toasty("You can't go below 1!", false);
        } else {
            quantity--;
            display(quantity);
            displayPrice(calculatePrice());
        }
    }


    public void submitOrder(View view) {

            ImageView coffee = (ImageView) findViewById(R.id.coffee);
            coffee.setVisibility(View.VISIBLE);
//            TextView summary = (TextView) findViewById(R.id.summary);
//            summary.setText("Thanks for ordering we are preparing your ");
//            if (quantity == 1) {
//                summary.append("1 coffee");
//            } else {
//                summary.append(quantity + " coffees");
//            }

            new CountDownTimer(1000 * quantity, 1000) {

                TextView timeLeft = (TextView) findViewById(R.id.timeLeft);
                Button order = (Button) findViewById(R.id.order);

                public void onTick(long millisUntilFinished) {
                    timeLeft.setText("Your order will be ready in: \n" + millisUntilFinished / 1000 + "s");
                    order.setEnabled(false);
                    animate(millisUntilFinished);
                    thankYou("");
                }

                public void onFinish() {
                    timeLeft.setText("You can pick up yor order!");
                    order.setEnabled(true);
                    thankYou("Thank you!");
                }
            }.start();

        }


    /**
     * Calculates price
     *
     * @return total price for ordered coffees
     *
     */

    private int calculatePrice() {
        price = checkSize();
        return price * quantity;
    }

    /**
     * This method displays the total price on the screen.
     *
     */
    private void thankYou(String msg) {
        TextView thankYou = (TextView) findViewById(R.id.thankYou);
        thankYou.setText(msg);
    }

    private void displayPrice(int price) {
        TextView textView = (TextView) findViewById(
                R.id.textView2);
        textView.setText("Total price: " + price + "$");

    }
    /**
     * This method displays the quantity in the EditText view
     *
     * @param quantity how many coffees you've ordered
     */
    private void display(int quantity) {
        TextView display = (TextView) findViewById(
                R.id.display);
        display.setText("" + quantity);

    }

    /**
     * Method makes a simple animation out of two pictures, change occurs after each second
     *
     * @param time how long should it animate it's divided by 1000
     */

    private void animate(long time) {
        ImageView coffee = (ImageView) findViewById(R.id.coffee);
        if ((time / 1000) % 2 == 0) {
            coffee.setImageResource(R.drawable.cof1);
        } else {
            coffee.setImageResource(R.drawable.cof2);
        }
    }


    public int checkSize() {
        RadioGroup coffeeSize = (RadioGroup) findViewById(R.id.coffeeSize);
        switch (coffeeSize.getCheckedRadioButtonId()) {
            case R.id.radioButton:
                price = default_price * 75 / 100;
                break;
            case R.id.radioButton1:
                price = default_price;
                break;
            case R.id.radioButton2:
                price = default_price * 150 / 100;
                break;
        }

        return price;

    }


}
