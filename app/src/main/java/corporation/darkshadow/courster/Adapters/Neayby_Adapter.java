package corporation.darkshadow.courster.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import corporation.darkshadow.courster.R;
import corporation.darkshadow.courster.pojo.nearby_response.Result;


/**
 * Created by darkshadow on 8/3/18.
 */

public class Neayby_Adapter extends RecyclerView.Adapter<Neayby_Adapter.MyViewHolder> {

    private Context context;
    private List<Result> coachingsList;

    public Neayby_Adapter(Context context, List<Result> coachingsList) {
        this.context = context;
        this.coachingsList = coachingsList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView coachingname,rating;

        public MyViewHolder(View itemView) {
            super(itemView);
            coachingname = (TextView)itemView.findViewById(R.id.nearby_coaching_name);
            rating = (TextView)itemView.findViewById(R.id.nearby_coaching_rating);
        }
    }

    @Override
    public Neayby_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.nearby_adapter,parent,false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(Neayby_Adapter.MyViewHolder holder, int position) {
        final Result coaching = coachingsList.get(position);
        holder.coachingname.setText(coaching.getName());
        holder.rating.setText(String.valueOf(coaching.getRating()));

    }

    @Override
    public int getItemCount() {
        return coachingsList.size();
    }
}
