package udduk.stroke_coach;
/**
 *  Copyright (c) 2015 Andreas Pabst
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;
import com.startapp.android.publish.StartAppSDK;
import com.startapp.android.publish.banner.Banner;
import udduk.stroke_coach.settings.SettingsActivity;

public class MainActivity extends ActionBarActivity
{
    private long backPressedTime = 0;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null)
        {
            getFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, new StrokeFragment()).commit();
        }

        //Toolbar as Actionbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Banner banner = (Banner) findViewById(R.id.startAppBanner);
        if(PreferenceManager.getDefaultSharedPreferences(this).getBoolean("ads_enabled", true))
        {
            StartAppSDK.init(this, "111333832", "201238307", false);
            banner.showBanner();
        }
        else
        {
            banner.hideBanner();
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);

        getSupportActionBar().setTitle(savedInstanceState.getCharSequence("title"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.action_stroke:
                getFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, new StopFragment()).commit();
                getSupportActionBar().setTitle(getResources().getString(R.string.stopwatch));
                break;
            case R.id.action_stop:
                getFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, new StrokeFragment()).commit();
                getSupportActionBar().setTitle(getResources().getString(R.string.strokecoach));
                break;
            case R.id.action_about:
                startActivity(new Intent(this,SettingsActivity.class));
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putCharSequence("title", getSupportActionBar().getTitle());
    }

    @Override
    public void onBackPressed()
    {
        long t = System.currentTimeMillis();
        if (t - backPressedTime > 2000)
        {
            backPressedTime = t;
            Toast.makeText(this, getResources().getString(R.string.backtoexit),
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            super.onBackPressed();
        }
    }
}
