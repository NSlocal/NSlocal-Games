android {
    namespace = "com.nslocal.games"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.nslocal.games"
        minSdk = 36
        targetSdk = 36
        versionCode = 1
        versionName = "1.0.0"
    }

    signingConfigs {
        create("release") {
            storeFile = file(System.getenv("STORE_FILE") ?: "release.jks")
            storePassword = System.getenv("STORE_PASSWORD") ?: "dummy"
            keyAlias = System.getenv("KEY_ALIAS") ?: "dummy"
            keyPassword = System.getenv("KEY_PASSWORD") ?: "dummy"
            enableV1Signing = true
            enableV2Signing = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs["release"]
        }
    }
}
