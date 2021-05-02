# Pattern Lock App

Sample project for created Pattern Lock View using custom view.

## Preview

<img src="https://github.com/furkanozcan/pattern-lock/blob/main/preview/pattern_lock.gif" align="center" width="200" height="400" />

## Usage

### Step 1

Add the PatterLockView in your XML layout file.

```xml
    <com.furkanozcan.patternlock.PatternLockView
        android:id="@+id/patternLockView"
        android:layout_width="match_parent"
        android:layout_height="400dp"
        android:layout_margin="32dp"
        app:patternLock_errorLineColor="@color/error_line"
        app:patternLock_successLineColor="@color/success_line" />
```

### Step 2 (Optional)

You can listen state changes with adding listener to the view. Callback return state such as Initial, Started, Success and Error.

```kotlin
        binding.patternLockView.setOnChangeStateListener { state ->
            //handle state
        }
```

Also you can access created stage passwords like below.

```kotlin
        val firstStagePassword = binding.patternLockView.getPassword(PatternViewStageState.FIRST)
        val secondStagePassword = binding.patternLockView.getPassword(PatternViewStageState.SECOND)
```

# Customization

### XML 

You can add various attributes to the PatternLockView from your XML layout.

```xml
        app:patternLock_successLineColor="@color/blue"
        app:patternLock_successDotColor="@color/orange"
        app:patternLock_errorLineColor="@color/red"
        app:patternLock_errorDotColor="@color/red"
        app:patternLock_lineColor="@color/black"
        app:patternLock_dotColor="@color/black"
        app:patternLock_isAnimate="false"
```
