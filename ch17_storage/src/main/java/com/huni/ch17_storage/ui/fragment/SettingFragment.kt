package com.huni.ch17_storage.ui.fragment

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.huni.ch17_storage.R

class SettingFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.sample, rootKey)
    }
}