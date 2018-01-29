package corporation.darkshadow.courster.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import corporation.darkshadow.courster.MechanicalActivity;
import corporation.darkshadow.courster.R;
import corporation.darkshadow.courster.pojo.CourseList;
import corporation.darkshadow.courster.pojo.Result;

/**
 * Created by darkshadow on 29/1/18.
 */

public class MechanicalAdapter extends RecyclerView.Adapter<MechanicalAdapter.MyViewHolder> {

    private List<Result> courseList;
    private Context context;

    public MechanicalAdapter(MechanicalActivity context, List<Result> courseList){
        this.context = context;
        this.courseList = courseList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView coursename,university;

        public MyViewHolder(View itemView) {
            super(itemView);
            coursename = (TextView)itemView.findViewById(R.id.coursename);
            university = (TextView)itemView.findViewById(R.id.university);
        }
    }

    @Override
    public MechanicalAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.mechanical_list,parent,false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(MechanicalAdapter.MyViewHolder holder, int position) {
        final Result course = courseList.get(position);
        holder.coursename.setText(course.getCoursename());
        holder.university.setText(course.getUniversity());

//        holder.coursename.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context,course.getUrl(),Toast.LENGTH_LONG).show();
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }


}
