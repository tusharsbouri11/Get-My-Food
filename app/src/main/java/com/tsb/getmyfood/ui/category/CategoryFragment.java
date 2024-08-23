package com.tsb.getmyfood.ui.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.tsb.getmyfood.R;
import com.tsb.getmyfood.adapters.CategoryAdapter;
import com.tsb.getmyfood.adapters.HomeCategoryAdapter;
import com.tsb.getmyfood.models.HomeCategoryModel;

import java.util.ArrayList;

public class CategoryFragment extends Fragment {

    RecyclerView categoryView;
    ArrayList<HomeCategoryModel> categoryList;
    CategoryAdapter categoryAdapter;
    FirebaseFirestore db;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_category, container, false);

        db=FirebaseFirestore.getInstance();

        categoryView =root.findViewById(R.id.category);
        categoryList=new ArrayList<>();

        db.collection("Category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                HomeCategoryModel homeCategoryModel=document.toObject(HomeCategoryModel.class);
                                categoryList.add(homeCategoryModel);
                                categoryAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        categoryView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        categoryAdapter=new CategoryAdapter(getActivity(), categoryList);
        categoryView.setAdapter(categoryAdapter);

        return root;
    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//    }
}