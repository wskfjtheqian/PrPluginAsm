package com.heqian.replugin.asm;

public class LoaderInjector {

    public static String activity(String name) {
        switch (name) {
            case "Landroid/app/Activity;":
                return "Lcom/qihoo360/replugin/loader/a/PluginActivity;";
            case "Landroid/app/TabActivity;":
                return "Lcom/qihoo360/replugin/loader/a/PluginTabActivity;";
            case "Landroid/app/ListActivity;":
                return "Lcom/qihoo360/replugin/loader/a/PluginListActivity;";
            case "Landroid/app/ActivityGroup;":
                return "Lcom/qihoo360/replugin/loader/a/PluginActivityGroup;";
            case "Landroid/support/v4/app/FragmentActivity;":
                return "Lcom/qihoo360/replugin/loader/a/PluginFragmentActivity;";
            case "Landroid/support/v7/app/AppCompatActivity;":
                return "Lcom/qihoo360/replugin/loader/a/PluginAppCompatActivity;";
            case "Landroidx/fragment/app/FragmentActivity;":
                return "Lcom/qihoo360/replugin/loader/a/PluginFragmentXActivity;";
            case "Landroidx/appcompat/app/AppCompatActivity;":
                return "Lcom/qihoo360/replugin/loader/a/PluginAppCompatXActivity;";
            case "Landroid/preference/PreferenceActivity;":
                return "Lcom/qihoo360/replugin/loader/a/PluginPreferenceActivity;";
            case "Landroid/app/ExpandableListActivity;":
                return "Lcom/qihoo360/replugin/loader/a/PluginExpandableListActivity;";
        }
        return name;
    }
}
