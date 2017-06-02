package com.example.asd.iamhere;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseFragment extends Fragment {


    public ChooseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose, container, false);
        ImageButton chat = (ImageButton) view.findViewById(R.id.chat_button);
        ImageButton map = (ImageButton) view.findViewById(R.id.map_button);
        TextView chat_text = (TextView) view.findViewById(R.id.text_chat_1);
        TextView map_text = (TextView) view.findViewById(R.id.text_map_1);
        TextView title_text = (TextView) view.findViewById(R.id.title_choose_1);

        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(), "Barley.ttf");
        chat_text.setTypeface(custom_font);
        map_text.setTypeface(custom_font);
        title_text.setTypeface(custom_font);

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MapActivity.class));
            }
        });
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),ChatActivity.class));
            }
        });
        return view;
    }

}
