package com.alahram.alahramapp.adpaters;

import android.widget.Filter;

import com.alahram.alahramapp.model.AddRequestModel;

import java.util.ArrayList;

/**
 * Created by codelab on 8/4/2018.
 */

public class CustomFilter extends Filter {

    AddRequestAdpater adapter;
    ArrayList<AddRequestModel> filterList;

    public CustomFilter(ArrayList<AddRequestModel> filterList,AddRequestAdpater adapter)
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
            //STORE OUR FILTERED PLAYERS
            ArrayList<AddRequestModel> filteredPlayers=new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {

                //CHECK
                if(filterList.get(i).getOrdername().toLowerCase().contains(constraint.toString().toLowerCase()))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredPlayers.add(filterList.get(i));
                }
            }

            results.count=filteredPlayers.size();
            results.values=filteredPlayers;
        }else
        {
            results.count=filterList.size();
            results.values=filterList;

        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapter.Itemlist= (ArrayList<AddRequestModel>) results.values;

        //REFRESH
        adapter.notifyDataSetChanged();
    }
}