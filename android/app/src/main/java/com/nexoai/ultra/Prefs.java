package com.nexoai.ultra;

import android.content.Context;
import android.content.SharedPreferences;

public final class Prefs {
    private static final String P="nexoai";
    public static SharedPreferences sp(Context c){ return c.getSharedPreferences(P, Context.MODE_PRIVATE); }
    public static String backend(Context c){ return sp(c).getString("backend", "https://nexoai.page.gd/api.php"); }
    public static String token(Context c){ return sp(c).getString("token", "TROQUE-ESTE-TOKEN"); }
    public static String style(Context c){ return sp(c).getString("style", "Natural, inteligente, educado, claro, objetivo e sem parecer texto de IA."); }
}
