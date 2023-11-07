package com.example.codefortest;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends Activity {
    Button insertButton;
    Button queryButton;
    Button updateButton;
    Button deleteButton;
    Button nextButton;
    Button previousButton;
    EditText popId;
    EditText popName;
    EditText popNum;
    EditText popType;
    EditText popFandom;
    Switch popOn;
    EditText popUlti;
    EditText popPrice;
    TextView idTv;
    TextView fnameTv;
    TextView lnameTv;

    Cursor mCursor;

    //Listeners

    View.OnClickListener updateListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ContentValues mUpdateValues = new ContentValues();

            mUpdateValues.put(PopProvider.COLUMN_ID, popId.getText().toString().trim());
            mUpdateValues.put(PopProvider.COLUMN_POP_NAME, popName.getText().toString().trim());

            String mSelectionClause = PopProvider.COLUMN_ID + " = ? ";
//                + " AND " + PopProvider.COLUMN_POP_NAME + " = ? ";

            String[] mSelectionArgs = { fnameTv.getText().toString().trim()};
//                , lnameTv.getText().toString().trim() };

            int numRowsUpdates= getContentResolver().update(PopProvider.CONTENT_URI, mUpdateValues,
                    mSelectionClause, mSelectionArgs);

            clear();
        }
    };


    View.OnClickListener deleteListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String mSelectionClause = PopProvider.COLUMN_ID + " = ? " + " AND " +
                    PopProvider.COLUMN_POP_NAME + " = ? " + " AND " +
                    PopProvider.COLUMN_POP_NUMBER+ " = ? "+ " AND " +
                    PopProvider.COLUMN_POP_TYPE + " = ? " + " AND " +
                    PopProvider.COLUMN_FANDOM + " = ? ";
                    PopProvider.

            String[] mSelectionArgs = { fnameTv.getText().toString().trim(),
                    lnameTv.getText().toString().trim() };

            int mRowsDeleted = getContentResolver().delete(PopProvider.CONTENT_URI, mSelectionClause,
                    mSelectionArgs);

            clear();
        }
    };

    View.OnClickListener insertListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            ContentValues mNewValues = new ContentValues();

            mNewValues.put(PopProvider.COLUMN_ID, popId.getText().toString().trim());
            mNewValues.put(PopProvider.COLUMN_POP_NAME, popName.getText().toString().trim());
            mNewValues.put(PopProvider.COLUMN_POP_NUMBER, popName.getText().toString().trim());
            mNewValues.put(PopProvider.COLUMN_POP_TYPE, popNum.getText().toString().trim());
            mNewValues.put(PopProvider.COLUMN_FANDOM, popFandom.getText().toString().trim());
            mNewValues.put(PopProvider.COLUMN_ON, popOn.getText().toString().trim());
            mNewValues.put(PopProvider.COLUMN_ULTIMATE, popUlti.getText().toString().trim());
            mNewValues.put(PopProvider.COLUMN_PRICE, popPrice.getText().toString().trim());
            getContentResolver().insert(PopProvider.CONTENT_URI, mNewValues);

            clear();
        }
    };

    View.OnClickListener queryListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mCursor = getContentResolver().query(PopProvider.CONTENT_URI, null, null, null, null);

            if (mCursor != null) {
                if (mCursor.getCount() > 0) {
                    mCursor.moveToNext();
                    setViews();
                }
            }
        }
    };

    View.OnClickListener previousListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mCursor != null) {
                if (!mCursor.moveToPrevious()) {
                    mCursor.moveToLast();
                }

                setViews();
            }
        }
    };

    View.OnClickListener nextListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mCursor != null) {
                if (!mCursor.moveToNext()) {
                    mCursor.moveToFirst();
                }
                setViews();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        popId = findViewById(R.id._id);
        popName = findViewById(R.id.pop_name);
        popNum = findViewById(R.id.pop_num);
        popType = findViewById(R.id.pop_type);
        popFandom = findViewById((R.id.pop_fandom));
        popOn = findViewById(R.id.pop_on);
        popUlti = findViewById(R.id.pop_ultimate);
        popPrice = findViewById(R.id.pop_price);


        insertButton = findViewById(R.id.insertButton);
        queryButton = findViewById(R.id.queryButton);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);

        idTv = findViewById(R.id.unique_id);
        fnameTv = findViewById(R.id.fnameTextView);
        lnameTv = findViewById(R.id.lnameTextView);

        nextButton = findViewById(R.id.nextButton);
        previousButton = findViewById(R.id.previousButton);

        insertButton.setOnClickListener(insertListener);

        updateButton.setOnClickListener(updateListener);

        deleteButton.setOnClickListener(deleteListener);

        queryButton.setOnClickListener(queryListener);

        previousButton.setOnClickListener(previousListener);

        nextButton.setOnClickListener(nextListener);
    }

    private void setViews() {
        idTv.setText(mCursor.getString(0));
        String text1 = mCursor.getString(1) + " ";
        fnameTv.setText(text1);
        lnameTv.setText(mCursor.getString(2));
    }

    private void clear() {
        //popId.setText(getResources().getString(R.string.Pop_ID));
        popId.setText("");
        popName.setText("");
        popNum.setText("");
        popType.setText("");
        popFandom.setText("");
        popOn.setChecked(false);
        popUlti.setText("");
        popPrice.setText("");

        idTv.setText("");
        fnameTv.setText("");
        lnameTv.setText("");

        mCursor = null;
    }
}
