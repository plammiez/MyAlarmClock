package ayp.aug.myalarmclock;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import java.util.UUID;

/**
 * Created by Waraporn on 8/10/2016.
 */
public class DeleteFragment extends DialogFragment
        implements DialogInterface.OnClickListener {

    protected static final String EXTRA_DELETE = "DeleteFragment.EXTRA_DELETE";

    public static DeleteFragment newInstance(UUID contactId) {
        DeleteFragment df = new DeleteFragment();
        Bundle args = new Bundle();
        args.putSerializable("ARG_DELETE", contactId);
        df.setArguments(args);
        return df;
    }

    private Callbacks callbacks;

    public interface Callbacks {
        void onContactDeleted();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callbacks = (Callbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = null;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final UUID uuid = (UUID) getArguments().getSerializable("ARG_DELETE");

        final AlarmClock alarmClock = AlarmLab.getInstance(getActivity()).getAlarmByID(uuid);

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_delete, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(v);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AlarmLab.getInstance(getActivity()).deleteAlarm(uuid);
//                callbacks.onContactDeleted();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, this);
        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }
}
