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

package udduk.stroke_coach.settings;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import udduk.stroke_coach.R;


public class LicenseActivity extends AppCompatActivity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_license);

        WebView webView = (WebView) findViewById(R.id.license_webview);
        webView.loadUrl("file:///android_asset/html/license.html");

        //Toolbar as Actionbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_license);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == android.R.id.home)
        {
            // providing proper up navigation
            super.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
