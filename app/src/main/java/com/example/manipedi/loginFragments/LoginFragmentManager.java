package com.example.manipedi.loginFragments;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.manipedi.R;

public class LoginFragmentManager {
    private FragmentManager fragmentManager;

    public LoginFragmentManager(FragmentActivity activity) {
        fragmentManager = activity.getSupportFragmentManager();
    }

    public void changeFragment(Class<? extends Fragment> fragmentClass) {
        fragmentManager.beginTransaction()
                .replace(R.id.loginHomePageFragment, fragmentClass, null)
                .setReorderingAllowed(true)
                .addToBackStack(fragmentClass.getName()) // name can be null
                .commit();
    }
}
