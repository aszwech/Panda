package pl.pandaonice.panda;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import pl.pandaonice.panda.data.PandaImages;


public class PandaListFragment extends Fragment {

    private static final int HEAD = 0;
    private static final int BODY = 1;
    private static final int LEG = 2;

    private OnImageClickListener mCallback;

    public interface OnImageClickListener {
        void onImageSelected(int position, int bodyPart);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnImageClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }

    public PandaListFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_panda_list, container, false);
        GridView heads = rootView.findViewById(R.id.head_list);
        GridView bodies = rootView.findViewById(R.id.body_list);
        GridView legs = rootView.findViewById(R.id.legs_list);

        PandaListAdapter headAdapter = new PandaListAdapter(getContext(), PandaImages.getHeads());
        heads.setAdapter(headAdapter);
        PandaListAdapter bodyAdapter = new PandaListAdapter(getContext(), PandaImages.getBodies());
        bodies.setAdapter(bodyAdapter);
        PandaListAdapter legAdapter = new PandaListAdapter(getContext(), PandaImages.getLegs());
        legs.setAdapter(legAdapter);

        heads.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mCallback.onImageSelected(position, HEAD);
            }
        });
        bodies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mCallback.onImageSelected(position, BODY);
            }
        });
        legs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mCallback.onImageSelected(position, LEG);
            }
        });

        return rootView;
    }
}
