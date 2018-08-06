package com.alahram.alahramapp.Admin;

import android.widget.Filter;

import java.util.ArrayList;

/**
 * Created by codelab on 8/7/2018.
 */

class CustomFilterEmpoyee extends Filter {

    ManageEmpoyeesAdpater adapter;
    ArrayList<UserViewModel> filterList;

    public CustomFilterEmpoyee(ArrayList<UserViewModel> filterList,ManageEmpoyeesAdpater adapter)
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
            ArrayList<UserViewModel> filteredPlayers=new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getUserName().toLowerCase().contains(constraint.toString().toLowerCase()))
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

        adapter.Userslist= (ArrayList<UserViewModel>) results.values;
        //REFRESH
        adapter.notifyDataSetChanged();
    }
}
