package com.example.intellisert_mobile_app.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.intellisert_mobile_app.R;

/**
 * Dialog Fragment for entering network credentials.
 */
public class BluetoothPairCredentialDialog extends AppCompatDialogFragment {

    private EditText nameField;
    private EditText passwdField;
    private BluetoothPairCredentialListener listener;

    private final String BT_PAIR_CRED_DIALOG = "BT_PAIR_CRED_DIALOG";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.bluetooth_pair_cred_activity, null);

        nameField = view.findViewById(R.id.bluetooth_pair_cred_name);
        passwdField = view.findViewById(R.id.bluetooth_pair_cred_pswd);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.ApplicationDialog);

        builder.setView(view)
                .setTitle("Network Credentials")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    // we dont want to do anything if cancel is selected
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    // send network information to BluetoothPairActivity by way of listener
                    // callback
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String password = passwdField.getText().toString();
                        String name = nameField.getText().toString();

                        listener.passCredentials(name, password);
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Context context){
        // attaches the fragments context to the bluetooth pair activities context so
        // a callback can be made
        super.onAttach(context);

        try {
            listener = (BluetoothPairCredentialListener) context;
        } catch (ClassCastException e) {
            Log.e(BT_PAIR_CRED_DIALOG, e.getMessage());
        }
    }

    /**
     * Used as a callback function in BluetoothPairActivity to
     * receive network credentials from this dialog fragment.
     */
    public interface BluetoothPairCredentialListener {
        void passCredentials(String networkName, String password);
    }
}
