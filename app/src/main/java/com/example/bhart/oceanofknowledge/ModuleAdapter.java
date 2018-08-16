package com.example.bhart.oceanofknowledge;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class ModuleAdapter extends RecyclerView.Adapter<ModuleAdapter.ViewHolder> {

    private Context context;
    private List<module> moduleList;

    public ModuleAdapter(Context context, List<module> moduleList) {
        this.context = context;
        this.moduleList = moduleList;
    }

    @NonNull
    @Override
    public ModuleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.module_list,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ModuleAdapter.ViewHolder holder, int position) {
        module m= moduleList.get(position);
        //Picasso.with(context).load(m.getImage()).into(holder.image);
        holder.title.setText(m.getTitle());
        holder.lang.setText(m.getLang());
        holder.moddir.setText(m.getModdir());
        holder.size.setText(m.getSize());

    }

    @Override
    public int getItemCount() {
        return moduleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView moddir;
        TextView lang;
        TextView size;
        public ViewHolder(View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.title);
            moddir=(TextView)itemView.findViewById(R.id.moddir);
            lang=(TextView)itemView.findViewById(R.id.lan);
            size=(TextView)itemView.findViewById(R.id.size);
            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String dir=moddir.getText().toString();
                    Intent intent= new Intent(v.getContext(),DirDetails.class);
                    intent.putExtra("directory",dir);
                    v.getContext().startActivity(intent);
                }
            });
        }

    }
}
