package corporation.darkshadow.courster.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import corporation.darkshadow.courster.MechanicalActivity;
import corporation.darkshadow.courster.R;
import corporation.darkshadow.courster.pojo.CourseList;
import corporation.darkshadow.courster.pojo.Result;

/**
 * Created by darkshadow on 29/1/18.
 */

public class MechanicalAdapter extends RecyclerView.Adapter<MechanicalAdapter.MyViewHolder> implements Filterable {

    private List<Result> courseList;
    private List<Result> courseListFiltered;
    private Context context;

    public MechanicalAdapter(MechanicalActivity context, List<Result> courseList){
        this.context = context;
        this.courseList = courseList;
        this.courseListFiltered = courseList;
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
        final Result course = courseListFiltered.get(position);
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
        return courseListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()){
//                    courseListFiltered.clear();
//                    courseListFiltered.addAll(courseList);
                    courseListFiltered = courseList;
                }
                else {
                    List<Result> filteredList = new ArrayList<>();
                    for (Result row: courseList) {
                        if (row.getCoursename().toLowerCase().contains(charString.toLowerCase())){
                            filteredList.add(row);
                        }
                    }
//                    courseListFiltered.clear();
//                    courseListFiltered.addAll(filteredList);
                    courseListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = courseListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                courseListFiltered = (ArrayList<Result>) results.values;
                notifyDataSetChanged();
            }
        };
    }


}
