package com.example.spending_management.adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spending_management.R;
import com.example.spending_management.databinding.SampleCategoryItemBinding;
import com.example.spending_management.models.Category;

import java.util.ArrayList;
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    Context context;
    ArrayList<Category> categories;
    public interface CategoryClickListener{
        void onCategoryClicked(Category category);
    }
    CategoryClickListener categoryClickListener;
    public CategoryAdapter (Context context, ArrayList<Category> categories, CategoryClickListener categoryClickListener){
        this.context = context;
        this.categories = categories;
        this.categoryClickListener = categoryClickListener;
    }
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.sample_category_item, parent, false));

    }
    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.binding.categoryText.setText(category.getCategory_name());
        holder.binding.categoryIcon.setImageResource(category.getCategory_image());
        holder.binding.categoryIcon.setBackgroundTintList(context.getColorStateList(category.getCategory_color()));
        holder.itemView.setOnClickListener(c -> {
            categoryClickListener.onCategoryClicked(category);
        });
    }
    @Override
    public int getItemCount() {
        return categories.size();
    }
    public class CategoryViewHolder extends RecyclerView.ViewHolder{
        SampleCategoryItemBinding binding;
        public CategoryViewHolder (@NonNull View itemView){
            super(itemView);
            binding = SampleCategoryItemBinding.bind(itemView);
        }
    }
}
