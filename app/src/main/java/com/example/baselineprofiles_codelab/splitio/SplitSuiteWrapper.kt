package com.example.baselineprofiles_codelab.splitio

import com.example.baselineprofiles_codelab.BuildConfig
import android.content.Context
import android.util.Log
import io.split.android.suite.SplitSuiteBuilder
import io.split.android.client.SplitClient
import io.split.android.client.SplitClientConfig
import io.split.android.client.api.Key
import io.split.android.client.events.SplitEvent
import io.split.android.client.events.SplitEventTask
import io.split.android.client.SplitResult
import io.split.android.client.utils.logger.SplitLogLevel
import java.io.IOException
import java.net.URISyntaxException
import java.util.UUID
import java.util.concurrent.TimeoutException

/*  The SplitSuiteWrapper class
*   - Wraps the Split Android Suite initialization and functions
*   - Creates one singleton object that is accessible from anywhere in the app module
*  
*   Note: The SplitSuiteWrapper class takes some milliseconds to initialize the SplitSuite with
*   updated feature flag and segment definitions. The isReady flag will show that the initialization
*   is complete.
*  
*   An object declaration is a concise way of creating a singleton class.
*   source: https://www.baeldung.com/kotlin/singleton-classes
*  
*   The initialization of an object declaration is thread-safe and done on first access.
*   source: https://kotlinlang.org/docs/object-declarations.html#object-declarations-overview
*  
*   Tip:
*   Consider delaying UI rendering until SplitSuiteWrapper.isReady if using feature flags to
*   render UI variations; otherwise, a user may see inconsistent UI renderings.
*/
object SplitSuiteWrapper {

    private var clientsideSdkKey: String? = BuildConfig.SPLIT_CLIENTSIDE_API_KEY
    private var userId: Key? = Key( UUID.randomUUID().toString().take(5) )  // in production (for accurate metric calculations) you will want to pass in a token that is unique for each user but persists over user sessions (like a login id)
    private var client: SplitClient? = null

    fun initialize(applicationContext: Context, onReady: () -> Unit) {

        // safety check
        // note: be wary of creating a synchronization lock here, as you must never block the UI thread
        if( client != null ) return // abort

        try {

            client =
                SplitSuiteBuilder.build(
                    clientsideSdkKey,
                    userId,
                    SplitClientConfig
                        .builder()
                        //.impressionsRefreshRate(30)   // we can alternately call client.flush() to immediately send impression (feature flag evaluation) data to Split cloud
                        //.logLevel(SplitLogLevel.VERBOSE)
                        .build(),
                    applicationContext
                ).client()

            client?.on(
                SplitEvent.SDK_READY,
                object: SplitEventTask() {
                    override fun onPostExecution(client: SplitClient?) {
                        onReady()
                    }
                }
            )

        } catch (e: Exception ) {
            when (e) {
                // log the exception perhaps thrown by the SplitSuiteBuilder.build() function
                is IOException,
                is URISyntaxException,
                is InterruptedException,
                is TimeoutException -> Log.e("SplitSuite.init", e.message.toString())

                else -> throw e
            }
        }
    }

    fun flush() = client?.flush()

    fun getSplitTreatment(flag: SplitWrapper.FeatureFlag, attributes: Map<String?, Any?>?): String? {
        return client?.getTreatment(flag.flagname, attributes)
    }

    fun getSplitTreatment(flag: SplitWrapper.FeatureFlag): String? {
        return client?.getTreatment(flag.flagname)
    }

    fun getSplitTreatmentWithConfig(flag: SplitWrapper.FeatureFlag, attributes: Map<String?, Any?>?): SplitResult? {
        return client?.getTreatmentWithConfig(flag.flagname, attributes)
    }

    fun sendTrackEvent(trackType: String?, metricName: String?, metricValue: Double): Boolean? {
        return client?.track(trackType, metricName, metricValue)
    }

    fun sendTrackEvent(trackType: String?, metricName: String?): Boolean? {
        return client?.track(trackType, metricName)
    }
}