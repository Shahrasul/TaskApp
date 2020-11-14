package com.example.taskapp.ui.dashboard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapp.Interfaces.OnItemClickListener;
import com.example.taskapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Objects;

public class DashboardFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<com.example.taskapp.models.Task> taskList;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final DashTaskAdapter dashTaskAdapter = new DashTaskAdapter();
        recyclerView = view.findViewById(R.id.recyclerView_dashboard);
        recyclerView.setAdapter(dashTaskAdapter);
        FirebaseFirestore.getInstance().collection("tasks").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    taskList = Objects.requireNonNull(task.getResult()).toObjects(com.example.taskapp.models.Task.class);
                    Log.e("TAG", taskList.toString());
                    dashTaskAdapter.addList(taskList);
                } else Objects.requireNonNull(task.getException()).printStackTrace();
            }
        });
        dashTaskAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {

            }

            @Override
            public void onItemLongClick(final int pos) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Удаление");
                builder.setMessage("Удалить элемент списка?");
                builder.setNegativeButton("Отмена", null);
                builder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                FirebaseFirestore.getInstance().collection("tasks").document((taskList.get(pos).getFirebaseId()))
                                        .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(requireContext(), "Успешно удалено", Toast.LENGTH_SHORT).show();
                                        } else task.getException().printStackTrace();
                                    }
                                });
                                dashTaskAdapter.removeItem(pos);

                            }
                        });
                builder.show();
            }
        });
    }
}