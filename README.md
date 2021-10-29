# SmartCredentials-SDK-react
- Here is a short introduction of how to integrate native Android SmartCredentials SDK into a React Native Application.

- Please first look at the Tutorial of React Native to have a understand of Native Module Setup in React Native Application: 
https://reactnative.dev/docs/native-modules-intro

## Setup

- Open up the Android project within your React Native application in Android Studio. 
- Adding all neccesary dependencies for SmartCredential SDK into build.gradle app module: 
```sh
implementation("de.telekom.smartcredentials:core:${smartCredentialsVersion}")
implementation("de.telekom.smartcredentials:security:${smartCredentialsVersion}")
implementation("de.telekom.smartcredentials:storage:${smartCredentialsVersion}")
implementation("de.telekom.smartcredentials:authentication:${smartCredentialsVersion}")
```
- The version extension can be defined in build.gradle project level: 

```sh

    project.ext {
        smartCredentialsVersion = "6.2.6"
    }

```
- For this SampleApp, the `authorization` module of SmartCredential will be used to demo a feature of authrization with Google Account with a `login()` method. 
- To do that, please add the following `<RedirectUriReceiverActivity>` Tag into the main application Manifest file `AndroidManifest.xml` in order to receive the redirect URI of Google: 

```sh
    <activity
        android:name="net.openid.appauth.RedirectUriReceiverActivity"
        tools:node="replace">
      <intent-filter>
        <action android:name="android.intent.action.VIEW" />
        <category android:name="android.intent.category.DEFAULT" />
        <category android:name="android.intent.category.BROWSABLE" />
        <data android:scheme="com.googleusercontent.apps.1065878372659-arafja1ap6i3b6vs7d746pocdh6uu4o3" />
      </intent-filter>
    </activity>
```
- To handle cases of complete and cancel login, please add also 2 activities tag: 

```sh
    <activity android:name=".CancelActivity" />
    <activity android:name=".CompletionActivity" />
```

## Create A Custom Native Module File

- The first step is to create the `SmartCredentialModule.java` Java file, which contains your native module Java class: 

```sh
public class SmartCredentialModule extends ReactContextBaseJavaModule {
    AuthenticationApi authenticationApi;
    ReactApplicationContext context;
    @RequiresApi(api = Build.VERSION_CODES.M)
    SmartCredentialModule(ReactApplicationContext context) {
        super(context);
    }
}
```

- All Java native modules in Android need to implement the getName() method. This method returns a string, which represents the name of the native module: 

```sh
    @NonNull
    @Override
    public String getName() {
        return "SmartCredentialModule";
    }
```
- Export a Native Method to JavaScript: All native module methods meant to be invoked from JavaScript must be annotated with `@ReactMethod`.

```sh
@ReactMethod
public boolean logIn() {
}
```

## Register the Module (Android Specific)

Once a native module is written, it needs to be registered with React Native. In order to do so, you need to add your native module to a `ReactPackage` and register the `ReactPackage` with React Native. During initialization, React Native will loop over all packages, and for each `ReactPackage`, register each native module within.

React Native invokes the method `createNativeModules()` on a ReactPackage in order to get the list of native modules to register. For Android, if a module is not instantiated and returned in `createNativeModules()` it will not be available from JavaScript.

To add your Native Module to `ReactPackage`, first create a new Java Class named MyAppPackage.java that implements ReactPackage:

```sh
public class MyAppPackage implements ReactPackage {

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }
    
    @Override
    public List<NativeModule> createNativeModules(
            ReactApplicationContext reactContext) {
        List<NativeModule> modules = new ArrayList<>();

        modules.add(new SmartCredentialModule(reactContext));

        return modules;
    }
}
```

To register the `SmartCredentialModule` package, you must add `MyAppPackage` to the list of packages returned in ReactNativeHost's `getPackages()` method. Open up your `MainApplication.java` file and locate the `getPackages()` method, then add your package to the packages list `getPackages()` return: 

```sh
    @Override
    protected List<ReactPackage> getPackages() {
      List<ReactPackage> packages = new PackageList(this).getPackages();
      packages.add(new ModuleRegistryAdapter(mModuleRegistryProvider));
      packages.add(new MyAppPackage());
      return packages;
    }
````

## Test what you have built

At this point, you have set up the basic scaffolding for your native module in Android. Test that out by accessing the native module and invoking its exported method in JavaScript.





