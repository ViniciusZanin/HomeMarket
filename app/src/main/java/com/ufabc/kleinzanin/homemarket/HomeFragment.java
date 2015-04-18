package com.ufabc.kleinzanin.homemarket;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment {
	
	public HomeFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
         
        return rootView;
    }

    /*public void testeDespensa(View view) {

        Button TesteProduto = (Button)
        TesteProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDespensa();
            }
        });
    }

    private void initDespensa() {
        startActivity((new Intent(Context, Despensa.class)));
    }*/
}
