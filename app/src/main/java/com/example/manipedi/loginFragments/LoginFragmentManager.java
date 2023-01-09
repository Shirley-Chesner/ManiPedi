package com.example.manipedi.loginFragments;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.manipedi.R;

public class LoginFragmentManager {
    private FragmentManager fragmentManager;

    public LoginFragmentManager(FragmentActivity activity) {
        fragmentManager = activity.getSupportFragmentManager();
    }

    public void changeFragment(Class<? extends Fragment> fragmentClass) {
        this.changeFragment(fragmentClass, false);
    }

    public void changeFragment(Class<? extends Fragment> fragmentClass, boolean addToBackStack) {
        FragmentTransaction transaction= fragmentManager.beginTransaction()
//                .setCustomAnimations(
//                        R.anim.slide_in_bottom,  // enter
//                        R.anim.fade_out,  // exit
//                        R.anim.fade_in,   // popEnter
//                        R.anim.slide_out_buttom  // popExit
//                )
                .replace(R.id.loginHomePageFragment, fragmentClass, null)
                .setReorderingAllowed(true);

        if (addToBackStack) transaction.addToBackStack(fragmentClass.getName());

        transaction.commit();
    }
}
