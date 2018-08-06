package com.alahram.alahramapp.Admin;

import android.widget.Filter;

import com.alahram.alahramapp.model.AddRequestModel;

import java.util.ArrayList;

/**
 * Created by codelab on 8/7/2018.
 */

public class CustomFilterItems extends Filter {

    ManageItemsAdpater adapter;
    ArrayList<AddRequestModel> filterList;

    public CustomFilterItems(ArrayList<AddRequestModel> filterList, ManageItemsAdpater adapter)
    {
        this.adapter=adapter;
        this.filterList=filterList;

    }

    //FILTERING OCURS
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();

        //CHECK CONSTRAINT VALIDITY
        if(constraint != null && constraint.length() > 0)
        {
            //CHANGE TO UPPER
            constraint=constraint.toString().toUpperCase();
            //STORE OUR FILTERED AddRequestModelS
            ArrayList<AddRequestModel> filteredAddRequestModels=new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {

                //CHECK
                if(filterList.get(i).getOrdername().toLowerCase().contains(constraint.toString().toLowerCase()))
                {
                    //ADD AddRequestModel TO FILTERED AddRequestModelS
                    filteredAddRequestModels.add(filterList.get(i));
                }
            }

            results.count=filteredAddRequestModels.size();
            results.values=filteredAddRequestModels;
        }else
        {
            results.count=filterList.size();
            results.values=filterList;

        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapter.Itemlist = (ArrayList<AddRequestModel>) results.values;
        //REFRESH
        adapter.notifyDataSetChanged();
    }
}
