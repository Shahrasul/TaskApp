package com.example.taskapp.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapp.App;
import com.example.taskapp.Interfaces.OnItemClickListener;
import com.example.taskapp.R;
import com.example.taskapp.models.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HomeFragment extends Fragment {

    private TaskAdapter adapter;
    public List<Task> taskList = new ArrayList<>();
    private Task task;
    private NavController navController;
    boolean sort = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new TaskAdapter(taskList);
        loadData();
        Random r = new Random();

        //for (int i = 0; i < 10; i++) {
        //String[] randomWords = generateRandomWords(10);
        //String randomWord = randomWords[r.nextInt(randomWords.length)];
        // task = new Task("randomWord",
        //new SimpleDateFormat("dd:MM:yyyy, HH:mm").format(Calendar.getInstance().getTime()));
        //  adapter.addItem(task);
        //taskList.add(task);
        //  }
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
//                Toast.makeText(requireContext(), taskList.get(pos).getTitle() + " ", Toast.LENGTH_SHORT).show();
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
                                App.getDatabase().taskDao().deleteByID(taskList.get(pos).getId());
                                adapter.removeItem(pos);

                            }
                        });
                builder.show();
            }
        });
    }

    //public static String[] generateRandomWords(int numberOfWorlds) {
    //  String[] randomStrings = new String[numberOfWorlds];
    //Random random = new Random();
    //      for(int i = 0; i < numberOfWorlds; i++)
    //    {
    //      char[] word = new char[random.nextInt(8)+3];
    //    for(int j = 0; j < word.length; j++)
    //  {
    //    word[j] = (char)('a' + random.nextInt(26));
    //}
    //randomStrings[i] = new String(word);
    //}
    //return randomStrings;
    //}


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openForm();

            }
        });
        initResultListener();
        initList(view);

    }

    private void loadData() {
        List<Task> list = App.getDatabase().taskDao().getAll();
        adapter.addList(list);
    }

    private void initList(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);

    }

    private void initResultListener() {
        getParentFragmentManager().setFragmentResultListener("form",
                getViewLifecycleOwner(), new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        Task task;
                        task = (Task) result.getSerializable("task");
                        adapter.addItem(task);
                    }
                });
    }


    private void openForm() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.formFragment);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    public void sortByABC() {
        taskList.clear();
        taskList.addAll(App.getDatabase().taskDao().getAllSorted());
        adapter.notifyDataSetChanged();

    }

    public void initialList() {
        taskList.clear();
        taskList.addAll(App.getDatabase().taskDao().getAll());
        adapter.notifyDataSetChanged();
    }

    public void sortByTime() {
        taskList.clear();
        taskList.addAll(App.getDatabase().taskDao().getAllDateSorted());
        adapter.notifyDataSetChanged();

    }
}