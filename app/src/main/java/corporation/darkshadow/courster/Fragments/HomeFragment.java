package corporation.darkshadow.courster.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import corporation.darkshadow.courster.Adapters.FieldsAdapter;
import corporation.darkshadow.courster.R;
import corporation.darkshadow.courster.pojo.Fields;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by DELL on 5/7/2017.
 */

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private FieldsAdapter adapter;
    private List<Fields> fieldList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);

//      ----------------------- initCollapsingToolbar();-------------------------------

        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout)view.findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout)view.findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });

//      ----------------------- Ending Collapsing toolbaar-------------------------------

        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        fieldList = new ArrayList<>();
        adapter = new FieldsAdapter(getActivity(),fieldList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

//      ----------------------- Preparing Fields-------------------------------

        String[] covers = new String[]{
                "http://sci-forex.com/laptop.jpg",
                "https://www.creaform-engineering.com/sites/default/files/public/mechanical_-_utv_bodywork003.jpg",
                "http://www.naceweb.org/uploadedimages/images/2017/feature/electrical-engineering-get-top-salary.jpg",
                "https://www.army.mil/e2/c/images/2016/03/04/425816/size0.jpg",
                "http://sci-forex.com/it.png"};

        Fields f = new Fields("Computer Science",193,covers[0]);
        fieldList.add(f);

        f = new Fields("Mechanical",70,covers[1]);
        fieldList.add(f);

        f = new Fields("Electrical",218,covers[2]);
        fieldList.add(f);

        f = new Fields("Civil",249,covers[3]);
        fieldList.add(f);

        f = new Fields("IT",182,covers[4]);
        fieldList.add(f);

        adapter.notifyDataSetChanged();

        try {
            Glide.with(this).load("http://www.dypatil.edu/pune-talegaon/engineering-technology/wp-content/uploads/2015/02/Engineering-Solutions-940x350-cropped.jpg").into((ImageView)view.findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}
