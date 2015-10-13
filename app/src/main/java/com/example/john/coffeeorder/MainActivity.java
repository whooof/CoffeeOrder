package com.example.john.coffeeorder;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int quantity = 1;
    double default_price = 3;
    double price = 3;
    int showDiscountNotification = 1;

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
     * Add one coffee
     *
     */

    public void addOne(View view) {
        if (quantity == 30) {
            toasty("You can't order more then 50!", true);
        } else {
            quantity++;
            display(quantity);
            displayPrice(calculatePrice());
        }
    }

    /**
     * Subtract one coffee
     *
     */

    public void subOne(View view) {
        if (quantity == 1) {
            toasty("You can't go below 1!", true);
        } else {

            quantity--;
            display(quantity);
            displayPrice(calculatePrice());
        }
    }


    /**
     * This method is called when user clicks on one of the checkBoxes with toppings it update the
     * TextView that shows price
     *
     */

    public void refreshPrice(View view) {
        displayPrice(calculatePrice());
    }


    /**
     * This method submit order after choosing quantity, size and toppings. It start a timer
     * counting down time needed to prepare order
     *
     */

    public void submitOrder(View view) {

            ImageView coffee = (ImageView) findViewById(R.id.coffee);
            coffee.setVisibility(View.VISIBLE);

            new CountDownTimer(1000 * quantity, 1000) {

                TextView timeLeft = (TextView) findViewById(R.id.timeLeft);
                Button order = (Button) findViewById(R.id.order);
                EditText editText = (EditText) findViewById(R.id.editText);

                public void onTick(long millisUntilFinished) {
                    timeLeft.setText("Your order will be ready in: \n" + millisUntilFinished / 1000 + "s");
                    order.setEnabled(false);
                    animate(millisUntilFinished);
                }

                public void onFinish() {
                    timeLeft.setText("You can pick up yor order!");
                    order.setEnabled(true);
                    String text = editText.getText().toString().length() == 0 ? "Stranger" : editText.getText().toString();
                    thankYou("Thank you, " + text);
                    order.setText("Order Again!");
                }
            }.start();

        }


    /**
     * Method that calculates chosen toppings price. It multiply quantity times price of each topping
     * (if selected)
     *
     * @return total price of all selected toppings
     */


    private double toppingsPrice() {

        double extra = 0;

        CheckBox whippedCream = (CheckBox) findViewById(R.id.whippedCream);
        CheckBox chocolateFlakes = (CheckBox) findViewById(R.id.chocoFlakes);
        CheckBox caramelSyrup = (CheckBox) findViewById(R.id.caramelSyrup);
        CheckBox vanillaSyrup = (CheckBox) findViewById(R.id.vanillaSyrup);

        extra += whippedCream.isChecked() ? quantity * 0.1 : 0;
        extra += chocolateFlakes.isChecked() ? quantity * 0.05 : 0;
        extra += caramelSyrup.isChecked() ? quantity * 0.2 : 0;
        extra += vanillaSyrup.isChecked() ? quantity * 0.2 : 0;

        return extra;
    }

    /**
     * Calculates price
     *
     * @return total price for ordered coffees
     *
     */

    private double calculatePrice() {
        price = checkSize();
        if ((price * quantity + toppingsPrice()) >= 10 && (price * quantity + toppingsPrice()) < 30 ) {
            if (showDiscountNotification == 1) {
                toasty("You've ordered for more then 10$. Thank You you will receive 10% discount", true);
                showDiscountNotification = 2;
            }
            thankYou("Thank you for a big order! You receive 10% discount! " + String.format("%.2f", 30-(price * quantity + toppingsPrice()))+ "$ more for 30%");
            return (price * quantity + toppingsPrice()) * 0.9;

        } else if ((price * quantity + toppingsPrice()) >= 30){
            if (showDiscountNotification == 2) {
            toasty("You've ordered for more then 30$. Thank You you will receive 30% discount", true);
            showDiscountNotification = 1;
            }
            thankYou("Thank you for a huge order! You receive 30% discount!");
            return (price * quantity + toppingsPrice()) * 0.7;
        }

        else {
            showDiscountNotification = 1;
            thankYou("Add coffees for another: " + String.format("%.2f", 10-(price * quantity + toppingsPrice()))+"$ to get the discount!");
            return price * quantity + toppingsPrice();
        }
    }



    private void thankYou(String msg) {
        TextView thankYou = (TextView) findViewById(R.id.thankYou);
        thankYou.setText(msg);
    }

    /**
     * This method displays the total price on the screen.
     *
     * @param price in double format will show price as a appended string
     */

    private void displayPrice(double price) {
        TextView textView = (TextView) findViewById(
                R.id.textView2);
        textView.setText("Total price: " + String.format("%.2f", price) + "$");

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

    /**
     * Checks what size of coffee is selected and change price accordingly.
     * Also change CoffeeSizeLabel [To be replaced with according coffee size img]
     * @return price per one cup.
     */

    public double checkSize() {
        RadioGroup coffeeSize = (RadioGroup) findViewById(R.id.coffeeSize);
        ImageView coffeeSizeImg = (ImageView) findViewById(R.id.coffeeSizeImg);

        switch (coffeeSize.getCheckedRadioButtonId()) {
            case R.id.radioButton:
                price = default_price * 75 / 100;
                coffeeSizeImg.setImageResource(R.drawable.s);
                coffeeSizeImg.getLayoutParams().height = 155;
                break;
            case R.id.radioButton1:
                price = default_price;
                coffeeSizeImg.setImageResource(R.drawable.m);
                coffeeSizeImg.getLayoutParams().height = 200;
                break;
            case R.id.radioButton2:
                price = default_price * 150 / 100;
                coffeeSizeImg.setImageResource(R.drawable.l);
                coffeeSizeImg.getLayoutParams().height = 250;
                break;
        }

        return price;

    }


}
