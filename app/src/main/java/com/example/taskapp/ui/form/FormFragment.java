package com.example.taskapp.ui.form;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.taskapp.App;
import com.example.taskapp.R;
import com.example.taskapp.models.Task;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class FormFragment extends Fragment {
    private EditText editText;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_form, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.editText);
        view.findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }


    private void save() {
        String timeStamp = new SimpleDateFormat("dd:MM:yyyy, HH:mm").format(Calendar.getInstance().getTime());
        Task task = new Task(editText.getText().toString().trim(),timeStamp);
        App.getDatabase().taskDao().insert(task);
//        Bundle bundle = new Bundle();
  //      bundle.putSerializable("task",task);
    //    getParentFragmentManager().setFragmentResult("form",bundle);
        close();
    }
    private void close(){
        NavController navController = Navigation.findNavController(requireActivity(),R.id.nav_host_fragment);
        navController.navigateUp();
    }
}