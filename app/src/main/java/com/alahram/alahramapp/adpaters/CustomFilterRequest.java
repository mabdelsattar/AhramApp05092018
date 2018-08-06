package com.alahram.alahramapp.adpaters;

import android.widget.Filter;

import com.alahram.alahramapp.model.RequestModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by codelab on 8/7/2018.
 */

public class CustomFilterRequest extends Filter {

    ShowRequestAdapter adapter;
    ArrayList<RequestModel> filterList;

    public CustomFilterRequest(ArrayList<RequestModel> filterList,ShowRequestAdapter adapter)
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
            ArrayList<RequestModel> filteredPlayers=new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {


//                           
                //CHECK
                if(filterList.get(i).getClientname().toLowerCase().contains(constraint.toString().toLowerCase()) ||
                        filterList.get(i).getClientphone().toLowerCase().contains(constraint.toString().toLowerCase()) ||
                        filterList.get(i).getNotes().toLowerCase().contains(constraint.toString().toLowerCase()) ||
                        filterList.get(i).getOrdernumber().toLowerCase().contains(constraint.toString().toLowerCase()) ||
                        filterList.get(i).getPaid().toLowerCase().contains(constraint.toString().toLowerCase()) ||
                        filterList.get(i).getReciverdate().toLowerCase().contains(constraint.toString().toLowerCase()) ||
                        filterList.get(i).getRemain().toLowerCase().contains(constraint.toString().toLowerCase()) ||
                        filterList.get(i).getStrObject().toLowerCase().contains(constraint.toString().toLowerCase()) ||
                        filterList.get(i).getMadeBy().toLowerCase().contains(constraint.toString().toLowerCase()))
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

        adapter.requestModelList= (ArrayList<RequestModel>) results.values;

        //REFRESH
        adapter.notifyDataSetChanged();
    }
}