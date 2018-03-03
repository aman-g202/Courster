package corporation.darkshadow.courster.Adapters;

/**
 * Created by darkshadow on 16/12/17.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import corporation.darkshadow.courster.CivilActivity;
import corporation.darkshadow.courster.CsActivity;
import corporation.darkshadow.courster.EcActivity;
import corporation.darkshadow.courster.Fragments.HomeFragment;
import corporation.darkshadow.courster.ItActivity;
import corporation.darkshadow.courster.MechanicalActivity;
import corporation.darkshadow.courster.R;
import corporation.darkshadow.courster.pojo.Fields;

public class FieldsAdapter extends RecyclerView.Adapter<FieldsAdapter.MyViewHolder> {

    private Context mContext;
    private List<Fields> fieldList;

    public FieldsAdapter(FragmentActivity mContext, List<Fields> fieldList) {
        this.mContext = mContext;
        this.fieldList = fieldList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail;
        public Button buttoncourse;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            buttoncourse = (Button) view.findViewById(R.id.buttoncourse);
        }
    }


    @Override
    public FieldsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.field_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FieldsAdapter.MyViewHolder holder, int position) {

        Fields fields = fieldList.get(position);
        holder.title.setText(fields.getCoursename());
        holder.count.setText(String.valueOf(fields.getNumOfcourse())+" Courses");

        // loading album cover using Glide library
        Glide.with(mContext).load(fields.getThumbnail()).into(holder.thumbnail);

        if(fields.getNumOfcourse() == 70){
            holder.buttoncourse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, MechanicalActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }

        if(fields.getNumOfcourse() == 193){
            holder.buttoncourse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, CsActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }

        if(fields.getNumOfcourse() == 218){
            holder.buttoncourse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, EcActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }

        if(fields.getNumOfcourse() == 249){
            holder.buttoncourse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, CivilActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }

        if(fields.getNumOfcourse() == 182){
            holder.buttoncourse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ItActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return fieldList.size();
    }
}
