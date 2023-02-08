package play.wellsport.app.data


import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import kotlinx.coroutines.delay


var firstUrlFB = ""
suspend fun firebaseConnect() : String {
        val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
        // тут возможно надо поменять дефолтное значение
        //remoteConfig.setDefaultsAsync
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        val result = firebaseConnectSimple(remoteConfig)
        return result
    /*delay(1500)
    return "https://dzen.ru/"*/
}

@SuppressLint("LogNotTimber")
suspend fun firebaseConnectSimple (remoteConfig: FirebaseRemoteConfig): String {
    var flag = 0

    while (flag < 2) {
        if (firstUrlFB.isEmpty()){
            delay(1500)
            try {
                fetch(remoteConfig)
            } catch (e: Exception) {
                Log.d("Error", "fetch Exception- $e")
            }

            //Тут надо вписать нужный ключ!!!
            firstUrlFB = remoteConfig.getString("url")////////////////////////////////////// вписать ключь
        }
        flag ++
    }
    Log.d("TAG", "URLanswer- $firstUrlFB")
    return firstUrlFB
}

private fun fetch(remoteConfig: FirebaseRemoteConfig) {
    FirebaseRemoteConfig.getInstance().apply {
        fetch(0)
            .addOnCompleteListener {
                remoteConfig.activate()
            }
    }
}

