## Set up steps

- Install `Extension Pack for Java` by Microsoft with `Java(TM) by Red Hat` extention

- Ensure that your `JAVA_HOME` and `ANDROID_HOME` environment variables are set correctly.

```sh
export PATH="$PATH:$HOME/Android/Sdk/cmdline-tools/latest/bin"

export ANDROID_HOME=~/Android/Sdk
export PATH=$ANDROID_HOME/cmdline-tools/latest/bin:$PATH
export PATH=$ANDROID_HOME/platform-tools:$PATH

```

- [Gradle properties files](https://developer.android.com/build#properties-files)

Gradle also includes two properties files, located in your root project directory, that you can use to specify settings for the Gradle build toolkit itself:

- gradle.properties
  This is where you can configure project-wide Gradle settings, such as the Gradle daemon's maximum heap size. For more information, see Build Environment.
- local.properties
  Configures local environment properties for the build system, including the following:
  **ndk.dir** - Path to the NDK. This property has been deprecated. Any downloaded versions of the NDK are installed in the ndk directory within the Android SDK directory.
  **sdk.dir**- Path to the Android SDK.
  **cmake.dir** - Path to CMake.
  **ndk.symlinkdir** - In Android Studio 3.5 and higher, creates a symlink to the NDK that can be shorter than the installed NDK path.

```sh
# https://developer.android.com/tools/releases/platform-tools?hl=pt-br
sdkmanager --install "cmdline-tools;latest"
sdkmanager --install "platforms;android-34"
```

To sync Gradle from the command line, you can use the following command:

```sh
./gradlew --refresh-dependencies
```

Alternatively, if you want to build and sync at the same time, you can run:

```sh
./gradlew clean build
```

## **Clear SDK Cache**

Sometimes, cached files can cause issues. You can try clearing the SDK cache:

```sh
rm -rf $HOME/.android
```

## VSCode confing

- .vscode/settings.json

```json
{
  "java.jdt.ls.androidSupport.enabled": "auto",
  "java.jdt.ls.java.home": "/home/hitalo/.sdkman/candidates/java/current",
  "java.configuration.runtimes": [
    {
      "name": "JavaSE-23",
      "path": "/home/hitalo/.sdkman/candidates/java/23-oracle"
    }
  ],
  "files.exclude": {
    "**/.git": true,
    "**/.DS_Store": true
  },
  "editor.formatOnSave": true
}
```

## Build

- To build the APK for the debug variant (the default one), run:
  For debug build: app/build/outputs/apk/debug/app-debug.apk

```sh
./gradlew assembleDebug
```

- For a release build (to generate a signed APK), you would use:
  For release build: app/build/outputs/apk/release/app-release.apk

```sh
./gradlew assembleRelease
```

## [Debug with Android Debug Bridge (ADB)](https://developer.android.com/tools/adb)
```sh
# reset your adb host
adb kill-server

# List all connected devices:
adb devices -l
```

```sh
# Check the Java version on the device:
adb -s GILJGYKZWOSOQ4AU shell getprop ro.build.version.sdk 
```

```sh
# Install an APK:
adb -s GILJGYKZWOSOQ4AU install <path_to_apk>
# adb -s GILJGYKZWOSOQ4AU install app/build/outputs/apk/debug/app-debug.apk

# # Set up port forwarding
# adb forward tcp:6100 tcp:7100
```

- activity manager (am)
```sh
# Run an App in Debug Mode:
# Information found in `AndroidManifest.xml`
# -D: Enable debugging.
# -W: Wait for launch to complete.
adb -s GILJGYKZWOSOQ4AU shell am start -D -W -n <package_name>/<activity_name>
# adb -s GILJGYKZWOSOQ4AU shell am start -D -W -n com.example.usbexample/.MainActivity
# adb shell am start -a android.intent.action.VIEW
```

```sh
adb -s GILJGYKZWOSOQ4AU shell ps | grep <package_name>
# adb -s GILJGYKZWOSOQ4AU shell ps | grep com.example.usbexample
# USER      PID   PPID  VSIZE  RSS   WCHAN            PC  NAME
# u0_a123   12345 1234  123456 12345 futex_wait_queue_me  com.example.myapp

# or 

adb -s GILJGYKZWOSOQ4AU shell pidof com.example.usbexample


# Debug with jdb: If the application process ID is 1234:
# adb -s GILJGYKZWOSOQ4AU forward tcp:8700 jdwp:<PID>
adb -s GILJGYKZWOSOQ4AU forward tcp:8700 jdwp:15037 &&
jdb -attach localhost:8700
```

- find the process ID (PID) of your application running on the connected Android device
```sh
# list all installed packages in android adb shell
adb shell pm list packages
```


## [Debug with Android Debug Bridge (ADB) over Wifi](https://developer.android.com/tools/adb)
```sh
# Connect to a device over Wi-Fi:
adb tcpip 5555

# To verify device_ip_address, in your android device, go to Settings > About phone > Status > IP address
adb connect <device_ip_address>
# adb connect 192.168.1.100

# To disconnect:
adb disconnect <device_ip_address>
# adb disconnect 192.168.1.100

# Install an APK:
adb -s <device_ip_address> install <path_to_apk>
# adb -s 192.168.1.100 install app/build/outputs/apk/debug/app-debug.apk

# Debug
adb -s <device_ip_address> shell ps | grep <package_name>
# adb -s 192.168.1.100 shell ps | grep <package_name>

adb -s <device_ip_address> forward tcp:8700 jdwp:<PID>
# adb -s 192.168.1.100 forward tcp:8700 jdwp:<PID>

jdb -attach localhost:8700
```

# Android htop
```sh
adb shell top
```