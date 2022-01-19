package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CartActivity extends AppCompatActivity {

    Button productsBtn;
    TextView cart;
    SharedPreferences data;

    Button clear, purchase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        productsBtn=(Button)findViewById(R.id.productsBtn);
        productsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CartActivity.this, ShoppingActivity.class);
                startActivity(intent);
            }
        });

        cart = (TextView)findViewById(R.id.cartTotalText);
        getCart();

        clear = (Button) findViewById(R.id.clearCartBtn);
        purchase = (Button) findViewById(R.id.purchaseBtn);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                clearCart(true);
                cart.setText("Cart is empty.");
            }
        });

        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                clearCart(false);
                cart.setText("Thank you for purchasing.");
            }
        });
    }

    void getCart()
    {
        data = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);

        String text = "";
        float price = 0;

        for (int i = 0; i < Market.product.length; i++)
        {
            String product = Market.product[i];

            int amount = data.getInt(product, 0);

            if (amount != 0)
            {
                text += capitalize(product) + (amount != 1 ? " (" + amount + ")\n" : "\n");

                price += Market.price[i] * amount;
            }
        }

        if (text.equals("")) text += "Cart is empty.";
        else text += "Total Price: " + price + "₺";

        cart.setText(text);
    }

    String capitalize(String text)
    {
        return String.valueOf(text.charAt(0)).toUpperCase() + text.substring(1);
    }

    void clearCart(boolean clearMsg)
    {
        SharedPreferences.Editor dataHandler = data.edit();

        for (int i = 0; i < Market.product.length; i++)
        {
            dataHandler.putInt(Market.product[i], 0);
        }

        dataHandler.commit();

        if (clearMsg) Toast.makeText(CartActivity.this, "Cart cleared.", Toast.LENGTH_SHORT).show();
        else Toast.makeText(CartActivity.this, "Order is on the way...", Toast.LENGTH_SHORT).show();
    }
}