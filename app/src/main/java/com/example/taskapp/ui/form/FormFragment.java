package com.example.taskapp.ui.form;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.taskapp.App;
import com.example.taskapp.R;
import com.example.taskapp.models.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;


public class FormFragment extends Fragment implements OnSuccessListener<DocumentReference>, OnFailureListener{
    private EditText editText;
    Task task;


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
        task = new Task(editText.getText().toString().trim(), timeStamp);
        App.getDatabase().taskDao().insert(task);
        saveToFirestore(task);

    }

    private void saveToFirestore(final Task taskModel) {
        Log.e("TAG", taskModel.getTitle());
        FirebaseFirestore.getInstance()
                .collection("tasks")
                .add(taskModel)
                .addOnSuccessListener(requireActivity(),this)
                .addOnFailureListener(requireActivity(), this);
    }

    private void close() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigateUp();
    }

    @Override
    public void onSuccess(DocumentReference documentReference) {
        task.setFirebaseId(Objects.requireNonNull(documentReference.getId()));
        Toast.makeText(requireActivity(), "Успешно", Toast.LENGTH_SHORT).show();
        FirebaseFirestore
                .getInstance()
                .collection("tasks")
                .document(task.getFirebaseId())
                .set(task)
                .addOnFailureListener(requireActivity(), this);
        close();
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        Log.e("firebase exception", "onFailure: ", e);
    }
}