package com.deitel.twittersearches;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;



import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class FirstFragment extends ListFragment{

    private  ArrayAdapter<String> listAdapter;
    private OnFragmentInteractionListener mListener;
    public ArrayList<String> tags;

    // TODO: Rename and change types of parameters
    public static FirstFragment newInstance(ArrayList<String> tag ) {
        FirstFragment fragment = new FirstFragment(tag);
        return fragment;
    }


    public FirstFragment() {
        // Required empty public constructor
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    private FirstFragment( ArrayList<String> tag) {
        this.tags=tag;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        listAdapter=new ArrayAdapter<String>(getActivity(),R.layout.list_item,tags);
        setListAdapter(listAdapter);
        return inflater.inflate(R.layout.fragment1,container,false);

    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);
        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // get the tag that the user long touched
                final String tag = ((TextView) view).getText().toString();

                // create a new AlertDialog
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(FirstFragment.this.getActivity());

                // set the AlertDialog's title
                builder.setTitle(
                        getString(R.string.shareEditDeleteTitle, tag));

                // set list of items to display in dialog
                builder.setItems(R.array.dialog_items,
                        new DialogInterface.OnClickListener() {
                            // responds to user touch by sharing, editing or
                            // deleting a saved search
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0: // share
                                        mListener.share(tag);
                                        break;
                                    case 1: // edit
                                        // set EditTexts to match chosen tag and query
                                        mListener.edit(tag);
                                        break;
                                    case 2: // delete
                                        mListener.delete(tag);
                                        break;
                                }
                            }
                        } // end DialogInterface.OnClickListener
                ); // end call to builder.setItems

                // set the AlertDialog's negative Button
                builder.setNegativeButton(getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            // called when the "Cancel" Button is clicked
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel(); // dismiss the AlertDialog
                            }
                        }
                ); // end call to setNegativeButton

                builder.create().show(); // display the AlertDialog
                return true;
            }
        });
    }
        @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
           String tag=((TextView) v).getText().toString();
           mListener.showResult(tag);
        }
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri url);
        public void showResult(String tag);
        public void share(String tag);
        public void edit(String tag);
        public void delete(String tag);
    }

}
