package com.rinnai.fireplacewifimodulenz;

/**
 * Created by cconci on 22/03/2018.
 */

public class PersistentStorageDefines {

    //Share Preference Key
    public static final String SHARED_PREF_KEY = "com.rinnai.fireplacewifimodule.saveItems";

    //Shared Preference IDs

    public static final String SHARED_PREF_ID_RFWM_USER_FLAG                = SHARED_PREF_KEY+".rfwmUserFlag";
    public static final String SHARED_PREF_ID_RFWM_EMAIL                    = SHARED_PREF_KEY+".rfwmEmail";
    public static final String SHARED_PREF_ID_RFWM_PASSWORD                 = SHARED_PREF_KEY+".rfwmPassword";
    public static final String SHARED_PREF_ID_SHOW_HINT                 = SHARED_PREF_KEY+".showHint";

    //Shared Preference Defaults
    public static final int    SHARED_PREF_DEFAULT_ID_RFWM_USER_FLAG        = 0;
    public static final String SHARED_PREF_DEFAULT_ID_RFWM_EMAIL        = "";
    public static final String SHARED_PREF_DEFAULT_ID_RFWM_PASSWORD         = "";
    public static final Boolean SHARED_PREF_DEFAULT_ID_SHOW_HINT        = false;


}
