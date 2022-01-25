package com.ufv.strafe.ui.jogar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ufv.strafe.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JogarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JogarFragment extends Fragment  {


    private NavController navController;

    public JogarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment JogarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static JogarFragment newInstance(String param1, String param2) {
        JogarFragment fragment = new JogarFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_jogar, container, false);
    }



}