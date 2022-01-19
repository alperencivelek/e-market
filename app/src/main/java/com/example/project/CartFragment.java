package com.example.project;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CartFragment extends Fragment {

    View view;

    TextView cart; // Ürünleri gösteren textview
    SharedPreferences data;
    // preference

    Button clear, purchase; // satın alma, iptal etme butonları

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, // Klasik fragment metodu
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_cart, container, false);

        initializeCart(); // Sepetteki ürünleri yazdıran metod.

        return view;
    }


    void initializeCart()
    {
        cart = (TextView)view.findViewById(R.id.cartTotalText); // Atamalar

        getCart(); // Sepeti çağırma

        clear = (Button) view.findViewById(R.id.clearCartBtn);
        purchase = (Button) view.findViewById(R.id.purchaseBtn);

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
        data = getActivity().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);

        String text = "";
        float price = 0;

        for (int i = 0; i < Market.product.length; i++) // Ürünlerdeki fiyatı toplayan ve texte ekleyen döngü
        {
            String product = Market.product[i];

            int amount = data.getInt(product, 0);

            if (amount != 0)
            {
                text += capitalize(product) + (amount != 1 ? " (" + amount + ")\n" : "\n");

                price += Market.price[i] * amount;
            }
        }

        if (text.equals("")) text += "Cart is empty."; // Sepet boş olduğunda olacak yazı.
        else text += "Total Price: " + price + "₺"; // Sepet boş değilse toplam ücret.

        cart.setText(text); // Yazdırma.
    }

    String capitalize(String text) // Baş harfleri büyük yapan metod
    {
        return String.valueOf(text.charAt(0)).toUpperCase() + text.substring(1);
    }

    void clearCart(boolean clearMsg)
    {
        SharedPreferences.Editor dataHandler = data.edit();

        for (int i = 0; i < Market.product.length; i++)
        {
            dataHandler.putInt(Market.product[i], 0); // Tüm değerleri sıfırlayan döngü
        }

        dataHandler.commit();

        // Duruma göre yazdırılacak mesaj.
        if (clearMsg) Toast.makeText(getView().getContext(), "Cart cleared.", Toast.LENGTH_SHORT).show();
        else Toast.makeText(getView().getContext(), "Order is on the way...", Toast.LENGTH_SHORT).show();
    }
}