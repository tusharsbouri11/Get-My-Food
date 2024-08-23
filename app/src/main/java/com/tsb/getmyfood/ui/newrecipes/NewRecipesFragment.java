package com.tsb.getmyfood.ui.newrecipes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.tsb.getmyfood.R;
import com.tsb.getmyfood.adapters.NewRecipeAdapter;
import com.tsb.getmyfood.models.HomeNewOfferingModel;
import com.tsb.getmyfood.models.ItemsModel;

import java.util.ArrayList;

public class NewRecipesFragment extends Fragment {

    RecyclerView newRecipeView;
    ArrayList<ItemsModel> homeNewOfferingList;
    NewRecipeAdapter newRecipeAdapter;
    FirebaseFirestore db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_new_recipes, container, false);
        db=FirebaseFirestore.getInstance();
        newRecipeView = root.findViewById(R.id.new_recipe);
        homeNewOfferingList = new ArrayList<>();

        db.collection("All")
                .whereArrayContains("arr", "New")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                HomeNewOfferingModel homeNewOfferingModel = document.toObject(HomeNewOfferingModel.class);
//                                homeNewOfferingList.add(homeNewOfferingModel);
                                ItemsModel itemsModel=document.toObject(ItemsModel.class);
                                homeNewOfferingList.add(itemsModel);
                                newRecipeAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getContext(), "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        newRecipeView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        newRecipeAdapter=new NewRecipeAdapter(getActivity(), homeNewOfferingList);
        newRecipeView.setAdapter(newRecipeAdapter);

        return root;
    }
}