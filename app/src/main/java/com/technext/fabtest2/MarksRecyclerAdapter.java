package com.technext.fabtest2;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.FragmentManager;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.rey.material.widget.Slider;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Alessandro on 27/03/2015.
 */
public class MarksRecyclerAdapter extends RecyclerView.Adapter<MarksRecyclerAdapter.MyHolder> implements DatePickerDialog.OnDateSetListener {

    AppCompatActivity context;
    LayoutInflater inflater;
    List<CustomDataTable> data = new ArrayList<>();
    DatabaseAdapter adapter;
    MarksRecyclerAdapter currentAdapter;
    Integer open = 0;

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

    }

    public enum Type {oral, written, practical};

    int mOriginalHeight;
    int mFinalHeight;

    public MarksRecyclerAdapter(AppCompatActivity context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        adapter = new DatabaseAdapter(context);
        data = adapter.getAllSubjects();
        currentAdapter = this;
    }

    public void updateData(){
        data = adapter.getAllSubjects();
        this.notifyDataSetChanged();
    }

    @Override
    public MarksRecyclerAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.mark_single_view, parent, false);
//        RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.cardFab);
//        layout.setVisibility(View.GONE);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MarksRecyclerAdapter.MyHolder holder, final int position) {
        final int finPosition = position;
        holder.textView.setText(data.get(position).SUBJECT);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            holder.cardView.setClipToOutline(false);
        holder.fab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        MaterialDialog dialog = new MaterialDialog.Builder(context).title("INSERT NEW " + data.get(finPosition).SUBJECT.toUpperCase() + " MARK").customView(R.layout.add_mark_dialog_custom_layout, true).positiveText("CONFIRM").negativeText("CANCEL").callback(
                                new MaterialDialog.ButtonCallback() {
                                    @Override
                                    public void onPositive(MaterialDialog dialog) {
                                        super.onPositive(dialog);
                                        DatabaseAdapter adata = new DatabaseAdapter(context);
                                        View v = dialog.getCustomView();
                                        EditText mark = (EditText)v.findViewById(R.id.editText2);
                                        int subj_id = data.get(position).UID;
                                        Button dateBtn = (Button)v.findViewById(R.id.button);
                                        Spinner spinner = (Spinner)v.findViewById(R.id.spinner);
                                        if(mark.getText() != null && mark.getText().toString() != "") {
                                            adata.insertMark(mark.getText().toString(), subj_id, dateBtn.getText().toString(), spinner.getSelectedItemPosition());
                                        }
                                        else{
                                            Toast.makeText(context, "You must write a mark", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                        ).show();
                        View view = dialog.getCustomView();
                        Spinner type = (Spinner)view.findViewById(R.id.spinner);
                        final TextView percent = (TextView)view.findViewById(R.id.textView8);
                        TextView textViewSubj = (TextView)view.findViewById(R.id.textView4);
                        textViewSubj.setText(data.get(finPosition).SUBJECT);
                        Slider slider = (Slider)view.findViewById(R.id.slider);
                        slider.setOnPositionChangeListener(
                                new Slider.OnPositionChangeListener() {
                                    @Override
                                    public void onPositionChanged(Slider view, boolean fromUser, float oldPos, float newPos, int oldValue, int newValue) {
                                        percent.setText(newValue+"%");
                                    }
                                }
                        );
                        ArrayAdapter typeAdapter = ArrayAdapter.createFromResource(context, R.array.marks_types, R.layout.spinner_view);
                        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        type.setAdapter(typeAdapter);
                        final Button datePicker = (Button)(view.findViewById(R.id.button));
                        final SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy");
                        Calendar calendarbase = Calendar.getInstance();
                        datePicker.setText(" "+calendarbase.get(Calendar.DAY_OF_MONTH)+" "+new DateFormatSymbols().getMonths()[calendarbase.get(Calendar.MONTH)]+" "+calendarbase.get(Calendar.YEAR));
                        final DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                String monthString = new DateFormatSymbols().getMonths()[monthOfYear];
                                String text = " "+dayOfMonth+" "+monthString+" "+ year;
                                        datePicker.setText(text);
                            }
                        };
                        datePicker.setOnClickListener(
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Calendar now = Calendar.getInstance();
                                        DatePickerDialog dialog = DatePickerDialog.newInstance(
                                                listener,
                                                now.get(Calendar.YEAR),
                                                now.get(Calendar.MONTH),
                                                now.get(Calendar.DAY_OF_MONTH)
                                        );
                                        dialog.show(context.getFragmentManager(), "Datepickerdialog");
                                    }
                                }

                        );
                    }
                }
        );
//        holder.fabHolder.bringToFront();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener, RecyclerView.OnLongClickListener{
        TextView textView;
        CardView cardView;
        FloatingActionButton fab;
        boolean closed = true;
        boolean closing = false;
        boolean animating = false;
        boolean first = true;

        int mOriginalMargin = Helper.getPx(context, 4);
        int mFinalMargin = Helper.getPx(context, 45);
        public MyHolder(View itemView) {
            super(itemView);

//            mOriginalHeight = Helper.getPx(context, 90);
//            mFinalHeight = mOriginalHeight * 2;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            textView = (TextView) itemView.findViewById(R.id.markMainText);
            cardView = (CardView) itemView.findViewById(R.id.card);
            fab = (FloatingActionButton)itemView.findViewById(R.id.cardFab);
            final FloatingActionButton mFAB = (FloatingActionButton) context.findViewById(R.id.addSubjFab);

        }

        @Override
        public void onClick(final View v) {
//            RelativeLayout cardViewHolder = (RelativeLayout) v.findViewById(R.id.cardHolder);
//            RecyclerView recyclerView = (RecyclerView) context.findViewById(R.id.markRecycler);
            final FloatingActionButton mFAB = (FloatingActionButton) context.findViewById(R.id.addSubjFab);
            final RelativeLayout cont = (RelativeLayout) v.findViewById(R.id.detail_container);
            final FloatingActionButton layout = (FloatingActionButton) v.findViewById(R.id.cardFab);


            TextView mText = new TextView(context);
            ValueAnimator heightAnimator, marginAnimator;
            RelativeLayout.LayoutParams mParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


            //This gets the right height of the view to restore when it closes
            //
            //
            if(first) {
                mOriginalHeight = v.getHeight();
                mFinalHeight = mOriginalHeight * 2;
                mFinalMargin = mFAB.getHeight()/2;
                first = false;
            }
            //
            //
            //END



            mParams.setMargins(Helper.getPx(context, 24), 0, 0, 0);
            mText.setLayoutParams(mParams);

            if(closed){
                heightAnimator = ValueAnimator.ofInt(mOriginalHeight, mFinalHeight);
                marginAnimator = ValueAnimator.ofInt(mOriginalMargin, mFinalMargin);
                open++;
                //test.setExampleString("Test 1");
                if(open == 1){
                    mFAB.setVisibility(View.GONE);
                }
//                AddMarkFAB markFAB = new AddMarkFAB();
                mText.setText("Test 1");
                mText.setId(R.id.test1);
                cont.addView(mText);
                closed = false;
//                closing=false;
            }else{
                heightAnimator = ValueAnimator.ofInt(mFinalHeight, mOriginalHeight);
                marginAnimator = ValueAnimator.ofInt(mFinalMargin, mOriginalMargin);
                //cardView1.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                //currentAdapter.notifyItemChanged(getAdapterPosition());
                open--;
                //make card's FAB disappear
                layout.setVisibility(View.GONE);
                if(open == 0) {
//
                    mFAB.setVisibility(View.VISIBLE);
                }
                closed = true;
//                closing=true;
            }
            heightAnimator.setDuration(200);
            marginAnimator.setDuration(200);
            heightAnimator.setInterpolator(new LinearInterpolator());
            marginAnimator.setInterpolator(new LinearInterpolator());
            heightAnimator.addUpdateListener(
                    new ValueAnimator.AnimatorUpdateListener() {
                        boolean firstround = true;

                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            Integer value = (Integer) animation.getAnimatedValue();
                            v.getLayoutParams().height = value;
                            if (closing & firstround) {
                                firstround = false;
                                layout.setVisibility(View.GONE);
                            }
                            //v.setClipBounds(new Rect(0,0,v.getLayoutParams().width, value+(mGenFabContainer.getHeight()/2)));
                            v.requestLayout();
                        }
                    }
            );
            marginAnimator.addUpdateListener(
                    new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            Integer value = (Integer) valueAnimator.getAnimatedValue();
                            RelativeLayout.LayoutParams lay = (RelativeLayout.LayoutParams) cardView.getLayoutParams();
                            lay.bottomMargin = value;
                            cardView.setLayoutParams(lay);
                            cardView.requestLayout();
                        }
                    }
            );
            heightAnimator.addListener(
                    new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            animating = true;
                            if (closing) {
//                                Toast.makeText(context, "GONE", Toast.LENGTH_LONG).show();
                                layout.setVisibility(View.GONE);
                            } else {

                                //MANAGE FAB APPEARING/DISAPPEARING TIMING
                            }
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            animating = false;
                            if (closing) {
                                TextView test1 = (TextView) v.findViewById(R.id.test1);
                                cont.removeView(test1);
                                layout.setVisibility(View.GONE);
                                closing = false;
                            } else {
                                closing = true;
                                //make card's FAB reappear
                                layout.setVisibility(View.VISIBLE);
                            }
//                            Toast.makeText(context, "END: "+closing, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }
            );
            heightAnimator.start();
            marginAnimator.start();
        }

        @Override
        public boolean onLongClick(View v) {
            CharSequence[] options = {"Edit", "Delete"};
            MaterialDialog dialog = new MaterialDialog.Builder(context)
                    .items(options)
                    .itemsCallback(
                            new MaterialDialog.ListCallback() {
                                @Override
                                public void onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
                                    View mView = context.findViewById(R.id.addSubjFab);
                                    Snackbar.make(mView, "Selected" + charSequence, Snackbar.LENGTH_SHORT).show();
                                }
                            }
                    )
                    .autoDismiss(true)
                    .show();
            return false;
        }
    }
}
