package com.alahram.alahramapp.Admin;

import android.widget.Filter;

import java.util.ArrayList;

/**
 * Created by codelab on 8/7/2018.
 */

class CustomFilterClients extends Filter {

    ManageClientsAdpater adapter;
    ArrayList<ClientViewModel> filterList;

    public CustomFilterClients(ArrayList<ClientViewModel> filterList,ManageClientsAdpater adapter)
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
            ArrayList<ClientViewModel> filteredPlayers=new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getPhone().toLowerCase().contains(constraint.toString().toLowerCase()) || filterList.get(i).getName().toLowerCase().contains(constraint.toString().toLowerCase() ))
                {
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

        adapter.Userslist= (ArrayList<ClientViewModel>) results.values;

        //REFRESH
        adapter.notifyDataSetChanged();
    }
}
