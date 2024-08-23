package com.tsb.getmyfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.tsb.getmyfood.adapters.CategoryAdapter;
import com.tsb.getmyfood.adapters.CategoryItemsAdapter;
import com.tsb.getmyfood.models.HomeCategoryModel;
import com.tsb.getmyfood.models.ItemsModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryDetails extends AppCompatActivity {

    TextView catName, catDesc;
    ImageView catImg;
    RecyclerView catView;
    CategoryItemsAdapter categoryItemsAdapter;
    List<ItemsModel> categoryItemsList;
    String getCatName, getCatDesc, getCatImg;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_details);

        catName=findViewById(R.id.category_name);
        catDesc=findViewById(R.id.category_desc);
        catImg=findViewById(R.id.category_image);
        catView=findViewById(R.id.category_recycler);

        getCatName=getIntent().getStringExtra("name");
        getCatDesc=getIntent().getStringExtra("desc");
        getCatImg=getIntent().getStringExtra("img");

        Glide.with(this).load(getCatImg).into(catImg);
        catName.setText(getCatName);
        catDesc.setText(getCatDesc);

        categoryItemsList=new ArrayList<>();

        catView.setLayoutManager(new LinearLayoutManager(CategoryDetails.this, RecyclerView.VERTICAL, false));
        categoryItemsAdapter=new CategoryItemsAdapter(CategoryDetails.this, categoryItemsList);
        catView.setAdapter(categoryItemsAdapter);

        db=FirebaseFirestore.getInstance();

        if(getCatName.equals("All Items")) {
            db.collection("All")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    ItemsModel itemsModel = document.toObject(ItemsModel.class);
                                    categoryItemsList.add(itemsModel);
                                    categoryItemsAdapter.notifyDataSetChanged();
                                }
                            } else {
                                Toast.makeText(CategoryDetails.this, "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else
        {
            db.collection("All")
                    .whereArrayContains("arr", getCatName)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    ItemsModel itemsModel = document.toObject(ItemsModel.class);
                                    categoryItemsList.add(itemsModel);
                                    categoryItemsAdapter.notifyDataSetChanged();
                                }
                            } else {
                                Toast.makeText(CategoryDetails.this, "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}