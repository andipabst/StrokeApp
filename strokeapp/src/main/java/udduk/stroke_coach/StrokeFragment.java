/*
 *     Copyright (c) 2015 Andreas Pabst
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


public class StrokeFragment extends Fragment
{
    private TextView rate;

    private List<Long> list = new LinkedList<>();
    private long time = 0L;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_stroke, container, false);
        time = System.currentTimeMillis();
        Button button = (Button) rootView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                long strokesum = 0L;
                time = System.currentTimeMillis() - time;
                long strokerate = 60000L / time;
                strokerate = Math.min(strokerate, 300L);
                list.add(strokerate);
                list.remove(0);
                for(Long aList : list)
                {
                    strokesum += aList;
                }
                rate.setText(String.valueOf(strokesum / (long) list.size()));
                time = System.currentTimeMillis();
            }
        });

        rate = (TextView) rootView.findViewById(R.id.rate);
        if(savedInstanceState != null)
        {
            long[] longs = savedInstanceState.getLongArray("list");
            for(long l : longs)
            {
                list.add(l);
            }

            rate.setText(savedInstanceState.getString("rate"));
        }
        else
        {
            initializeStrokeRate();
        }

        setHasOptionsMenu(true);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);

        long[] longs = new long[list.size()];
        for(int a=0; a<list.size(); a++)
        {
            longs[a] = list.get(a);
        }
        outState.putLongArray("list", longs );

        outState.putString("rate", rate.getText().toString());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_stroke,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_reset) {
            initializeStrokeRate();
        }

        return super.onOptionsItemSelected(item);
    }

    private void initializeStrokeRate() {
        list.clear();

        list.add(20L);
        list.add(20L);
        list.add(20L);

        rate.setText("0");
    }
}