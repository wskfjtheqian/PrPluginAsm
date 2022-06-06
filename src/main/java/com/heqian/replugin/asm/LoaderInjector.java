package com.heqian.replugin.asm;

public class LoaderInjector {


    public static boolean excludeActivity(String type) {
        return type.startsWith("Landroid")
                || type.startsWith("Ljava")
                || type.startsWith("Lkotlin/")
                || type.startsWith("L$")
                || type.startsWith("Lcom/qihoo360/replugin/")
                || type.startsWith("Lcom/android/");
    }

    public static String replaceActivity(String name) {
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


    public static boolean excludeBroadcast(String type) {
        return type.startsWith("Landroid/support/v4/content/LocalBroadcastManager")
                || type.startsWith("Landroidx/localbroadcastmanager/content/LocalBroadcastManager")
                || type.startsWith("Lkotlin/")
                || type.startsWith("L$");
    }

    public static String replaceBroadcast(String name) {
        switch (name) {
            case "Landroidx/localbroadcastmanager/content/LocalBroadcastManager;":
                return "Lcom/qihoo360/replugin/loader/b/PluginLocalBroadcastManager;";

        }
        return name;
    }


}
