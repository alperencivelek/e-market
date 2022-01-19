package com.example.project;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class ShoppingFragment extends Fragment {

    View view;

    Button bread, milk, tomato, cucumber, onion, olive, cheese, carrot, water, chocolatte, chips;
    // Ürün butonları

    SharedPreferences data;
    SharedPreferences.Editor dataHandler;
    // Preference handler değeri


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, // Klasik create metodu
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_shopping, container, false);

        initializeButtons(); // Ek olarak buttonları aktifleştirme

        return view;
    }

    void initializeButtons()
    {
        data = getActivity().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        dataHandler = data.edit();


        bread = (Button)view.findViewById(R.id.breadBtn); // Buttonları bulma
        milk = (Button)view.findViewById(R.id.milkBtn);
        tomato = (Button)view.findViewById(R.id.tomatoBtn);
        cucumber = (Button)view.findViewById(R.id.cucumberBtn);
        onion = (Button)view.findViewById(R.id.onionBtn);
        olive = (Button)view.findViewById(R.id.oliveBtn);
        cheese = (Button)view.findViewById(R.id.chesseBtn);
        carrot = (Button)view.findViewById(R.id.carrotBtn);
        water = (Button)view.findViewById(R.id.waterBtn);
        chocolatte = (Button)view.findViewById(R.id.chocolatteBtn);
        chips = (Button)view.findViewById(R.id.chipsBtn);

        Button[] allButtons = {bread, milk, tomato, cucumber, onion, olive, cheese, carrot, water, chocolatte, chips};

        for (int i = 0; i < allButtons.length; i++)
        {
            Button button = allButtons[i];

            int fI = i;

            button.setOnClickListener(new View.OnClickListener() { // Tüm buttonlara click metod ataması
                @Override
                public void onClick(View view) {
                    addToCart(fI);
                }
            });
        }
    }

    void addToCart(int code)
    {
        String product = Market.product[code];

        dataHandler.putInt(product, data.getInt(product, 0) + 1); // Eklenen ürünü storageye ekleyip kaydetme.
        dataHandler.commit();

        Toast.makeText(getView().getContext(), "Added " + product + " to cart.", Toast.LENGTH_SHORT).show();
    }
}