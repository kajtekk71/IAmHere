package com.example.asd.iamhere;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.example.asd.iamhere.GroupFragment.filename;

public class MessageComposerFragment extends Fragment implements MultipleGroupSelectCallback {

    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL = 96;
    private List<Group> groups;
    private List<Group> selectedGroups;
    private MessageConstructor mMessageConstructor;
    private TextView additionalText;
    private boolean permissionGranted;
    private static final String SNAPSHOTFILENAME = "snapshot.png";
    private boolean imagesaved = false;

    public MessageComposerFragment() {
        selectedGroups = new LinkedList<>();
    }

    public void setMessageConstructor(MessageConstructor constructor) {
        this.mMessageConstructor = constructor;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                permissionGranted = true;
                saveImage();
            } else {
                Toast.makeText(getContext(), "Can't send image without permissions.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message_composer, container, false);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            permissionGranted = true;
        groups = readItemsFromFile();
        final CheckBox checkbox = (CheckBox) view.findViewById(R.id.checkbox_image);
        checkbox.setEnabled(mMessageConstructor.getBitmap() != null);
        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!permissionGranted && checkbox.isChecked())
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL);
                else saveImage();
            }
        });
        TextView messageBase = (TextView) view.findViewById(R.id.text_message_base);
        messageBase.setText(mMessageConstructor.buildMessage());
        TextView groupSelection = (TextView) view.findViewById(R.id.group_selection);
        groupSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogGroupSelectorFragment dialogGroupSelectorFragment = new DialogGroupSelectorFragment();
                dialogGroupSelectorFragment.setGroups(selectedGroups, groups);
                dialogGroupSelectorFragment.setTargetFragment(MessageComposerFragment.this, 0);
                dialogGroupSelectorFragment.show(getActivity().getSupportFragmentManager(), "selector");
            }
        });
        additionalText = (TextView) view.findViewById(R.id.message_text);
        Button button = (Button) view.findViewById(R.id.send_message_button);
        final RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radio_group);
        RadioButton messageRadio = (RadioButton) view.findViewById(R.id.message_radio);
        messageRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkbox.setChecked(false);
                    checkbox.setEnabled(false);
                } else
                    checkbox.setEnabled(true);
            }
        });
        RadioButton messengerRadio = (RadioButton) view.findViewById(R.id.messenger_radio);
        messengerRadio.setEnabled(isFacebookMessengerInstalled());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<GroupUser> users = new LinkedList<GroupUser>();
                for (Group group : selectedGroups)
                    for (GroupUser groupUser : group.users)
                        users.add(groupUser);
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.message_radio:
                        sendMessage(users);
                        break;
                    case R.id.email_radio:
                        sendEmail(users);
                        break;
                    case R.id.messenger_radio:
                        sendUsingMessenger();
                        break;
                }
            }
        });
        return view;
    }

    private boolean isFacebookMessengerInstalled() {
        try {
            ApplicationInfo info = getActivity().getPackageManager().
                    getApplicationInfo("com.facebook.orca", 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public ArrayList<Group> readItemsFromFile() {
        ArrayList<Group> groups = new ArrayList<>();
        FileInputStream fis;
        ObjectInputStream in;
        try {
            fis = getContext().openFileInput(filename);
            in = new ObjectInputStream(fis);
            while (true) {
                Group group = (Group) in.readObject();
                if (group != null) groups.add(group);
            }
        } catch (Exception ex) {
            return groups;
        }
    }

    @Override
    public void passDataBackToFragment(List<Group> selectedGroups) {
        this.selectedGroups = selectedGroups;
    }
    private void sendUsingMessenger(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT,additionalText.getText().toString() + "\n" + mMessageConstructor.buildMessage());
        intent.setType("text/plain");
//        if (imagesaved && permissionGranted) {
//            File file = new File(Environment.getExternalStorageDirectory() + "/iamheresnapshots", SNAPSHOTFILENAME);
//            Uri bmpUri = Uri.fromFile(file);
//            intent.putExtra(Intent.EXTRA_STREAM, bmpUri);
//            intent.setType("image/png");
//        }
        intent.setPackage("com.facebook.orca");
        startActivity(intent);
    }
    private void sendMessage(List<GroupUser> users) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(getNumbers(users)));
        intent.putExtra("sms_body", additionalText.getText().toString() + "\n" + mMessageConstructor.buildMessage());
        startActivity(intent);
    }

    @NonNull
    private String getNumbers(List<GroupUser> users) {
        StringBuilder sb = new StringBuilder();
        sb.append("smsto:");
        if (users.size() == 0)
            return sb.toString();
        else {
            for (GroupUser user : users)
                if (user.phoneNo != null || user.phoneNo != "")
                    sb.append(user.phoneNo + ";");
            return sb.deleteCharAt(sb.length() - 1).toString();
        }
    }

    private void sendEmail(List<GroupUser> users) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        //intent.setType("message/rfc822");
        intent.setType("application/image");
        intent.setData(Uri.parse(getEmails(users)));
        intent.putExtra(Intent.EXTRA_SUBJECT, "I am Here!");
        intent.putExtra(Intent.EXTRA_TEXT, additionalText.getText().toString() + "\n" + mMessageConstructor.buildMessage());
        if (imagesaved && permissionGranted) {
            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            File file = new File(Environment.getExternalStorageDirectory() + "/iamheresnapshots", SNAPSHOTFILENAME);
            Uri bmpUri = Uri.fromFile(file);
            intent.putExtra(Intent.EXTRA_STREAM, bmpUri);
        }
        startActivity(Intent.createChooser(intent, "Send mail via:"));
    }

    @NonNull
    private String getEmails(List<GroupUser> users) {
        StringBuilder sb = new StringBuilder();
        sb.append("mailto:");
        if (users.size() == 0)
            return sb.toString();
        else {
            for (GroupUser user : users)
                if (user.email != null || user.email != "")
                    sb.append(user.email + ";");
            return sb.deleteCharAt(sb.length() - 1).toString();
        }
    }

    private void saveImage() {
        if (mMessageConstructor.getBitmap() == null)
            return;
        FileOutputStream out = null;
        File myDir = new File(Environment.getExternalStorageDirectory().toString() + "/iamheresnapshots");
        myDir.mkdirs();
        File output = new File(myDir, SNAPSHOTFILENAME);
        try {
            out = new FileOutputStream(output);
            mMessageConstructor.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                    imagesaved = true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
