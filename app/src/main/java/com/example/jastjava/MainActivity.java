package com.example.jastjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.io.StringBufferInputStream;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void decrement(View view) {
        if(quantity == 0){
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    public void increment(View view) {
        if(quantity == 100){
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        Integer i = numberOfCoffees;
        quantityTextView.setText(i.toString());
    }

    public void submitOrder(View view) {
        EditText nameField = findViewById(R.id.name_field);
        Editable nameEditable = nameField.getText();
        String name = nameEditable.toString();
        name = "Ahmed";

        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);

        String message = createOrderSummary(name, price, hasWhippedCream, hasChocolate);
        //Log.i("MainActivity", message);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject, name));
        intent.putExtra(Intent.EXTRA_TEXT, message);

        startActivity(intent);

/*        if(intent.resolveActivity(getPackageManager())!= null){
            startActivity(intent);
        }*/
    }

    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate){
        int basePrice = 5;

        if(hasWhippedCream) {
            basePrice += 1;
        }
        if (hasChocolate) {

            basePrice += 2;
        }
        return basePrice * quantity;
    }

    private String createOrderSummary(String name, int price, boolean hasWhippedCream, boolean hasChocolate){
        String priceMessage = "\n" + getString(R.string.order_summary_name, name);
        priceMessage += "\n" + getString(R.string.order_summary_whipped_cream, hasWhippedCream);
        priceMessage += "\n" + getString(R.string.order_summary_chocolate, hasChocolate);
        priceMessage += "\n" + getString(R.string.order_summary_price, NumberFormat.getCurrencyInstance().format(price));
        priceMessage += "\n" + getString(R.string.order_summary_thank_you);
        return priceMessage;
    }
}