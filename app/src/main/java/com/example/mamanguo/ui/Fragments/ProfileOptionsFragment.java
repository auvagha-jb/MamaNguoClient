package com.example.mamanguo.ui.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mamanguo.R;


public class ProfileOptionsFragment extends Fragment {
    private static final String TAG = "ProfileOptionsFragment";
    private Button btnEdit;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.fragment_profile_edit, container, false);
        //btnEdit = RootView.findViewById(R.id.order_cardView);
        return RootView;
    }


    private void replaceFragment() {
        try {
            Fragment fragment = new ProfileEditFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.profile_options, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } catch (NullPointerException e) {
            Log.e(TAG, e.getMessage());
        }
    }


}
