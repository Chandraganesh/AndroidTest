package lu.post.eval

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class for the Eval app.
 * Annotated with @HiltAndroidApp to enable Hilt dependency injection.
 */
@HiltAndroidApp
class EvalApplication : Application()
