package ldh.com.zcomic.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.widget.Toast;

import ldh.com.zcomic.MainActivity;
import ldh.com.zcomic.R;
import ldh.com.zcomic.entity.Constants;
import ldh.com.zcomic.utils.ClearCacheUtils;
import ldh.com.zcomic.utils.SharedPreUtils;

/**
 * Created by allen liu on 2018/5/4.
 */

public class SettingFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener{
    public static final String PREF_KEY_THEME = "pref_key_theme";
    public static final String AUTORUN = "autoRun";
    public static final String SIMULATE = "simulate";
    public static final String CLEAR_CACHE = "clear_cache";
    public static final String UPDATE = "update";
    public static final String FEEDBACK = "feedback";

    private ListPreference themePreference;
    private ListPreference autoPreference;
    private Preference simulate;
    private Preference clear_cache;
    private Preference update;
    private Preference feedback;
    private final Preference.OnPreferenceChangeListener listener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
//            if(preference.getKey() == PREF_KEY_THEME)
//                    getActivity().finish();
//                    startActivity(new Intent(getActivity(), MainActivity.class));
            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                CharSequence[] entries = listPreference.getEntries();
                int index = listPreference.findIndexOfValue((String) newValue);
                listPreference.setSummary(entries[index]);

                SharedPreUtils.setCurrentTheme(getActivity(),entries[index].toString());
                getActivity().finish();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(Constants.EXTRA_IS_UPDATE_THEME, true);
                startActivity(intent);
            }
            return true;
        }
    };
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        themePreference = (ListPreference) findPreference(PREF_KEY_THEME);
        themePreference.setOnPreferenceChangeListener(listener);

//        autoPreference = (ListPreference) findPreference(AUTORUN);
//        autoPreference.setOnPreferenceChangeListener(listener);

        simulate = findPreference(SIMULATE);
        simulate.setOnPreferenceClickListener(this);

        clear_cache = findPreference(CLEAR_CACHE);
        clear_cache.setOnPreferenceClickListener(this);

        update = findPreference(UPDATE);
        update.setOnPreferenceClickListener(this);

        feedback = findPreference(FEEDBACK);
        feedback.setOnPreferenceClickListener(this);
//         initPreferences();
    }

    private void initPreferences() {
        String nowtheme = SharedPreUtils.getString(getActivity(), Constants.PREF_KEY_THEME, "1");
        if (nowtheme.equals("1")) {
            autoPreference.setSummary("白天");
        } else {
            autoPreference.setSummary("晚上");
        }
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        switch (preference.getKey()) {
            case SIMULATE:

                break;
            case CLEAR_CACHE:
                ClearCacheUtils.deleteDir(getActivity().getCacheDir());
                clear_cache.setSummary(ClearCacheUtils.getCacheSize());
                Toast.makeText(getActivity(), "清楚缓存成功",Toast.LENGTH_SHORT);
                break;
            case UPDATE:
                break;
            case FEEDBACK:
                Intent emailintent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "Allen9981@163.com", null));
                startActivity(Intent.createChooser(emailintent, "请选择邮件客户端"));
                break;
        }
        return true;
    }
}
