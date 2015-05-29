/*
 *     Copyright (c) 2015 Udduk
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package udduk.stroke_coach;

import android.app.Fragment;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

public class StopFragment extends Fragment implements android.view.View.OnClickListener
{

    private static final byte RESET = 0;
    private static final byte RUNNING = 1;
    private static final byte STOP = 2;
    private byte state = RESET;

    private Button strokebutton;
    private Button lap;
    private TextView rate;
    private Button reset;
    private TextView result;
    private Button start;
    private DrawTextView time;

    private List<Long> list = new LinkedList<>();
    private long rateTime = 0L;
    private long startTime = 0L;
    private long saveTime = 0L;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_stop, container, false);

        start = (Button) rootView.findViewById(R.id.start_button);
        start.setOnClickListener(this);

        lap = (Button) rootView.findViewById(R.id.lap_button);
        lap.setOnClickListener(this);

        reset = (Button) rootView.findViewById(R.id.reset_button);
        reset.setOnClickListener(this);

        time = (DrawTextView) rootView.findViewById(R.id.time);
        time.setTimeprovider(this);
        time.setText("00 : 00 . 0");

        strokebutton = (Button) rootView.findViewById(R.id.button_stroke_stop);
        strokebutton.setOnClickListener(this);

        rate = (TextView) rootView.findViewById(R.id.rate_stop);

        result = (TextView) rootView.findViewById(R.id.result_stop);

        if(savedInstanceState != null)
        {
            state = savedInstanceState.getByte("state");

            if(state == RUNNING)
            {
                startTime = savedInstanceState.getLong("start_time");
                start.setText(R.string.stop_time);
            }
            else
            {
                saveTime = savedInstanceState.getLong("save_time");
            }

            long[] longs = savedInstanceState.getLongArray("list");
            for(long l : longs)
            {
                list.add(l);
            }

            rate.setText(savedInstanceState.getCharSequence("rate"));
        }
        else
        {
            list.add(20L);
            list.add(20L);
            list.add(20L);

            rate.setText("0");
        }

        setHasOptionsMenu(true);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);

        outState.putByte("state", state);

        if(state == RUNNING)
        {
            outState.putLong("start_time", startTime);
        }
        else
        {
            outState.putLong("save_time", saveTime);
        }

        long[] longs = new long[list.size()];
        for(int a=0; a<list.size(); a++)
        {
            longs[a] = list.get(a);
        }
        outState.putLongArray("list", longs );

        outState.putCharSequence("rate",rate.getText());
    }

    public void onClick(View view)
    {
        if (view == start)
        {
            if (state == RUNNING)
            {
                start.setText(R.string.start_time);
                start.setBackgroundColor(getResources().getColor(R.color.green_button));
                reset.setBackgroundColor(getResources().getColor(R.color.red_button));
                reset.setEnabled(true);
                state = STOP;
                saveTime = System.currentTimeMillis() - startTime;
            }
            else if (state == STOP || state == RESET)
            {
                start.setText(R.string.stop_time);
                start.setBackgroundColor(getResources().getColor(R.color.red_button));
                reset.setBackgroundColor(getResources().getColor(R.color.gray_button));
                reset.setEnabled(false);
                state = RUNNING;
                startTime = System.currentTimeMillis() - saveTime;
            }
        }
        else if (view == lap)
        {
            result.setText((new StringBuilder()).append(time.getText().toString()).append("\n").append(result.getText().toString()).toString());
        }
        else if (view == reset)
        {
            startTime = 0L;
            saveTime = 0L;
            state = RESET;
            result.setText("");
        }
        else if (view == strokebutton)
        {
            long strokesum = 0L;
            rateTime = System.currentTimeMillis() - rateTime;
            long strokerate = 60000L / rateTime;
            strokerate = (strokerate < 300L) ? strokerate : 300L;
            list.add(strokerate);
            list.remove(0);
            for(Long element : list)
            {
                strokesum += element;
            }
            rate.setText(String.valueOf(strokesum / (long) list.size()));
            rateTime = System.currentTimeMillis();
        }
    }

    /**
     * Returns a number as a string with a leading zero,
     * if the number is <10
     * @param number the number to check
     * @return the String with the leading zero if necessary
     */
    private String toZeroString(long number)
    {
        String prefix = (number < 10) ? "0" : "";
        return prefix + number;
    }

    /**
     * Returns the current stop watch time
     * @return The current stop watch time
     */
    public String getTime()
    {
        long total = 0L;
        switch (state)
        {
            case RUNNING:
                total = System.currentTimeMillis() - startTime;
                break;
            case STOP:
                total = saveTime;
                break;
            case RESET:
                return "00 : 00 . 0";
        }

        long min = total / 60000;
        total = total % 60000;

        long sec = total / 1000;
        total = total % 1000;

        return String.format("%s : %s . %s", toZeroString(min), toZeroString(sec), toZeroString(total / 10));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_stop, menu);
    }
}


