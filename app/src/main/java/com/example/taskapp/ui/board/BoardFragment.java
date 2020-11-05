package com.example.taskapp.ui.board;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import com.example.taskapp.R;
import com.example.taskapp.utils.Prefs;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class BoardFragment extends Fragment {

    private Button skip;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        return inflater.inflate(R.layout.fragment_board, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewPager(view);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();
            }
        });
        skip = view.findViewById(R.id.btnSkip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHomeFragment();
            }
        });
    }

    private void openHomeFragment() {
        Prefs prefs = new Prefs(requireContext());
        prefs.setShown();
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            navController.navigate(R.id.action_boardFragment_to_phoneFragment2);
        } else {
            navController.navigateUp();
        }
    }

    private void initViewPager(View view) {
        ViewPager viewPager = view.findViewById(R.id.viewPager);
        BoardAdapter adapter = new BoardAdapter();
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = view.findViewById(R.id.dots);
        tabLayout.setupWithViewPager(viewPager, true);
    }

}