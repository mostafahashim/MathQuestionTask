package math.question.task.util

import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.Delegates

object Preferences {

    private var PREFS_NAME = "MathQuestionAppPref"
    private var USER_ID_PREF = "userID"
    private var APIToken_PREF = "api_token"
    private var USER_Email_PREF = "userEmail"
    private var USER_NAME_PREF = "userName"
    private var USER_socialId_PREF = "socialId"
    private var USER_PHONE_PREF = "userPhone"
    private var USER_Image_PREF = "userImage"
    private var CART_PREF = "cart"

    private var USER_PASSWORD_PREF = "userPassword"
    private var FavouriteModels_PREF = "FavouriteModels"
    private var BaseUrl_PREF = "BaseUrl"

    private var UserType_PREF = "userType"
    private var FacebookUrl_PREF = "FacebookUrl"
    private var YoutubeUrl_PREF = "YoutubeUrl"
    private var InstagramUrl_PREF = "InstagramUrl"

    private var accessId_PREF = "accessId"
    private var accessHash_PREF = "accessHash"
    private var isTokenPushed_PREF = "isTokenPushed"
    private var Registration_Token_PREF = "Registeration_token"
    private var appPrefence: SharedPreferences by Delegates.notNull<SharedPreferences>()

    // notifications preference
    private var ApplicationLocale_PREF = "ApplicationLocale"
    private var preferenceEditor: SharedPreferences.Editor by Delegates.notNull<SharedPreferences.Editor>()

    fun initializePreferences(context: Context) {
        appPrefence = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun getApplicationLocale(): String {
        return appPrefence.getString(ApplicationLocale_PREF, "")!!
    }

    fun saveApplicationLocale(local: String?) {
        preferenceEditor = appPrefence.edit()
        preferenceEditor.putString(ApplicationLocale_PREF, local)
        preferenceEditor.commit()
    }


    fun getIsPushedToken(): Boolean {
        return appPrefence.getBoolean(isTokenPushed_PREF, false)!!
    }

    fun saveIsPushedToken(local: Boolean?) {
        preferenceEditor = appPrefence.edit()
        preferenceEditor.putBoolean(isTokenPushed_PREF, local!!)
        preferenceEditor.commit()
    }


    fun getSocialId(): String {
        return appPrefence.getString(USER_socialId_PREF, "")!!
    }

    fun saveSocialId(local: String?) {
        preferenceEditor = appPrefence.edit()
        preferenceEditor.putString(USER_socialId_PREF, local)
        preferenceEditor.commit()
    }

    fun getUserImage(): String {
        return appPrefence.getString(USER_Image_PREF, "")!!
    }

    fun saveUserImage(local: String?) {
        preferenceEditor = appPrefence.edit()
        preferenceEditor.putString(USER_Image_PREF, local)
        preferenceEditor.commit()
    }

    fun getBaseUrl(): String {
        return appPrefence.getString(BaseUrl_PREF, "")!!
    }

    fun saveBaseUrl(baseUrl: String?) {
        preferenceEditor = appPrefence.edit()
        preferenceEditor.putString(BaseUrl_PREF, baseUrl)
        preferenceEditor.commit()
    }

    fun getUserType(): String {
        return appPrefence.getString(UserType_PREF, "")!!
    }

    fun saveUserType(userType: String?) {
        preferenceEditor = appPrefence.edit()
        preferenceEditor.putString(UserType_PREF, userType)
        preferenceEditor.commit()
    }

    fun saveUserToken(Token: String?) {
        preferenceEditor = appPrefence.edit()
        preferenceEditor.putString(Registration_Token_PREF, Token)
        preferenceEditor.commit()
    }

    fun getUserToken(): String {
        return appPrefence.getString(Registration_Token_PREF, "")!!
    }

    fun saveAccessId(accessId: Int) {
        preferenceEditor = appPrefence.edit()
        preferenceEditor.putInt(accessId_PREF, accessId)
        preferenceEditor.commit()
    }

    fun getAccessId(): Int {
        return appPrefence.getInt(accessId_PREF, 0)
    }

    fun saveAccessHash(accessHash: String?) {
        preferenceEditor = appPrefence.edit()
        preferenceEditor.putString(accessHash_PREF, accessHash)
        preferenceEditor.commit()
    }

    fun getAccessHash(): String {
        return appPrefence.getString(accessHash_PREF, "")!!
    }

    fun saveUserEmail(userEmail: String?) {
        preferenceEditor = appPrefence.edit()
        preferenceEditor.putString(USER_Email_PREF, userEmail)
        preferenceEditor.commit()
    }

    fun getUserEmail(): String {
        return appPrefence.getString(USER_Email_PREF, "")!!
    }


    fun saveFavouriteModels(pointModels: String?) {
        preferenceEditor = appPrefence.edit()
        preferenceEditor.putString(FavouriteModels_PREF, pointModels)
        preferenceEditor.commit()
    }

    fun getFavouriteModels(): String {
        return appPrefence.getString(FavouriteModels_PREF, "")!!
    }

    fun saveFacebookUrl(FacebookUrl: String?) {
        preferenceEditor = appPrefence.edit()
        preferenceEditor.putString(FacebookUrl_PREF, FacebookUrl)
        preferenceEditor.commit()
    }

    fun getFacebookUrl(): String {
        return appPrefence.getString(FacebookUrl_PREF, "")!!
    }

    fun saveYoutubeUrl(YoutubeUrl: String?) {
        preferenceEditor = appPrefence.edit()
        preferenceEditor.putString(YoutubeUrl_PREF, YoutubeUrl)
        preferenceEditor.commit()
    }

    fun getYoutubeUrl(): String {
        return appPrefence.getString(YoutubeUrl_PREF, "")!!
    }

    fun saveInstagramUrl(InstagramUrl: String?) {
        preferenceEditor = appPrefence.edit()
        preferenceEditor.putString(InstagramUrl_PREF, InstagramUrl)
        preferenceEditor.commit()
    }

    fun getInstagramUrl(): String {
        return appPrefence.getString(InstagramUrl_PREF, "")!!
    }

    fun savePassword(password: String?) {
        preferenceEditor = appPrefence.edit()
        preferenceEditor.putString(USER_PASSWORD_PREF, password)
        preferenceEditor.commit()
    }

    fun getPassword(): String {
        return appPrefence.getString(USER_PASSWORD_PREF, "")!!
    }

    fun getUserName(): String {
        return appPrefence.getString(USER_NAME_PREF, "")!!
    }

    fun saveUserName(userName: String?) {
        preferenceEditor = appPrefence.edit()
        preferenceEditor.putString(USER_NAME_PREF, userName)
        preferenceEditor.commit()
    }

    fun getUserPhone(): String {
        return appPrefence.getString(USER_PHONE_PREF, "")!!
    }

    fun saveUserPhone(userPhone: String?) {
        preferenceEditor = appPrefence.edit()
        preferenceEditor.putString(USER_PHONE_PREF, userPhone)
        preferenceEditor.commit()
    }

    fun getUserID(): String {
        return appPrefence.getString(USER_ID_PREF, "")!!
    }

    fun saveUserID(userID: String?) {
        preferenceEditor = appPrefence.edit()
        preferenceEditor.putString(USER_ID_PREF, userID)
        preferenceEditor.commit()
    }

    fun saveAPIToken(APIToken: String?) {
        preferenceEditor = appPrefence.edit()
        preferenceEditor.putString(APIToken_PREF, APIToken)
        preferenceEditor.commit()
    }

    fun getAPIToken(): String {
        return appPrefence.getString(APIToken_PREF, "")!!
    }

    fun saveCart(cart: String?) {
        preferenceEditor = appPrefence.edit()
        preferenceEditor.putString(CART_PREF, cart)
        preferenceEditor.commit()
    }

    fun getCart() = appPrefence.getString(CART_PREF, "[]")

    fun clearUserData() {
        /*preferenceEditor = appPrefence.edit()
        preferenceEditor.clear().commit()*/
        saveAPIToken("")
        saveUserID("")
        saveUserEmail("")
        saveUserName("")
        saveUserPhone("")
        savePassword("")
        saveSocialId("")
        saveBaseUrl("")
        saveFavouriteModels("")
    }
}