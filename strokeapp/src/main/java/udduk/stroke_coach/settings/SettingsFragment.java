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


import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import udduk.stroke_coach.BuildConfig;
import udduk.stroke_coach.R;

public class SettingsFragment extends PreferenceFragment
{
    SettingsActivity activity;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);

        activity = (SettingsActivity) getActivity();

        Preference license_preference = findPreference("license");
        if(license_preference != null)
        {
            license_preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
            {
                @Override
                public boolean onPreferenceClick(Preference preference)
                {
                    activity.startActivity(new Intent(activity, LicenseActivity.class));

                    return true;
                }
            });
        }

        Preference version_number = findPreference("version_nr");
        if(version_number != null)
        {
            version_number.setSummary(BuildConfig.VERSION_NAME);
        }
    }
}