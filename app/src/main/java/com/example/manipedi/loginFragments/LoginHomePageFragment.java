package com.example.manipedi.loginFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.manipedi.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginHomePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginHomePageFragment extends Fragment {

    public LoginHomePageFragment() { }

    public static LoginHomePageFragment newInstance() {
        LoginHomePageFragment fragment = new LoginHomePageFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login_home_page, container, false);

        Button loginBtn = view.findViewById(R.id.loginFragment_loginBtn);
        Button signUpBtn = view.findViewById(R.id.loginHomePage_signUpBtn);
        LoginFragmentManager fragmentManager = new LoginFragmentManager(getActivity());

        loginBtn.setOnClickListener(view1 ->
            fragmentManager.changeFragment(LoginFragment.class));

        signUpBtn.setOnClickListener(view1 ->
                fragmentManager.changeFragment(SignUpFragment.class));

        return view;
    }
}