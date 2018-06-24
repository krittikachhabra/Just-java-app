package com.example.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import static android.R.id.message;
import static java.net.Proxy.Type.HTTP;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 0;
    int priceOfCoffees = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        String priceMessage;
        if ( quantity < 0 )
            priceMessage = "Oops! Can't have negative cups.";
        else
            
            priceMessage = createOrderSummary();
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL,  new String[] {"//insert reciever's email ID here"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for "+getName());
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        Intent intent2 = new Intent(Intent.ACTION_SEND);
        intent2.setData(Uri.parse("smsto:"));  // This ensures only SMS apps respond
        intent2.putExtra("sms_body", priceMessage);
        if (intent2.resolveActivity(getPackageManager()) != null) {
            startActivity(intent2);
        }
    }

    /**
     * This method increments the given quantity and price on the screen.
     */
    public void increment(View view) {
        if(quantity < 100)
            quantity++;
        else
        {
            Context context = getApplicationContext();
            String text = "Too much Coffee!!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        display(quantity);
    }

    /**
     * This method decrements the given quantity and price on the screen.
     */
    public void decrement(View view) {
        if(quantity>=1)
            quantity--;
        else
        {
            Context context = getApplicationContext();
            String text = "Too little coffee!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        display(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.orderSummary_text_view);
//        orderSummaryTextView.setText(message);
//    }

    /**
     * Calculates the price of the order based on the current quantity.
     */
    private int calculatePrice(boolean topping1,boolean topping2) {
        int price = priceOfCoffees;
        if(topping1==true)
            price += 1;
        if(topping2==true)
            price += 2;
        price = quantity * price;
        return price;
    }

    /*get user name*/
    private String getName()
    {
        EditText nameView = (EditText) findViewById(R.id.nameView);
        String name = nameView.getText().toString();
        return name;
    }
    /* this method creates order summary */
    private String createOrderSummary()
    {
        CheckBox checkBox = (CheckBox) findViewById(R.id.topping_1);
        boolean checkState = checkBox.isChecked();
        CheckBox checkBox2 = (CheckBox) findViewById(R.id.topping_2);
        boolean checkState2 = checkBox2.isChecked();
        String priceMessage = "Name: " + getName();
        priceMessage = priceMessage + "\nAdd whipped cream? " + checkState;
        priceMessage = priceMessage + "\nAdd chocolate? " + checkState2;
        priceMessage = priceMessage + "\nQuantity: " + quantity;
        priceMessage = priceMessage + "\nTotal: $" + calculatePrice(checkState,checkState2);
        priceMessage = priceMessage + "\nThank-You!";
        return priceMessage;
    }
}
