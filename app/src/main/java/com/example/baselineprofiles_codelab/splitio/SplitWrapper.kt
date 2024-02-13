package com.example.baselineprofiles_codelab.splitio

/*   The SplitWrapper class
 *   - Acts as a namespace to hold the string values of Split feature flag names
 *   and the possible flag evaluations (treatment values) for each flag
 *
 *   You can see these feature flag names and definitions (treatment rules) in your
 *   Split account at app.split.io
 */
object SplitWrapper {

    enum class FeatureFlag(val flagname: String) {
        // list all feature flag names here
        ItemDetailFeature("item_detail_feature");

        val control: String = "control" // the default value returned when a flag evaluation (client.getTreatment() call) is unsuccessful
    }

    // create an enum for each Split feature flag to hold entries that match treatment values (flag variations)
    enum class ItemDetailFeatureFlagValues(val value: String) { on("on"), off("off") }
}