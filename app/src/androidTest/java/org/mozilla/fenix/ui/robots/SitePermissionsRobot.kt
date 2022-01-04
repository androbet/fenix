package org.mozilla.fenix.ui.robots

import androidx.test.uiautomator.UiSelector
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.mozilla.fenix.helpers.TestAssetHelper.waitingTime
import org.mozilla.fenix.helpers.TestHelper.packageName

class SitePermissionsRobot {

    fun verifyMicrophonePermissionPrompt(url: String) {
        assertTrue(mDevice.findObject(UiSelector().text("Allow $url to use your microphone?"))
            .waitForExists(waitingTime)
        )
        assertTrue(denyPagePermissionButton.text.equals("Don’t allow"))
        assertTrue(allowPagePermissionButton.text.equals("Allow"))
    }

    fun verifyCameraPermissionPrompt(url: String) {
        assertTrue(mDevice.findObject(UiSelector().text("Allow $url to use your camera?"))
            .waitForExists(waitingTime)
        )
        assertTrue(denyPagePermissionButton.text.equals("Don’t allow"))
        assertTrue(allowPagePermissionButton.text.equals("Allow"))
    }

    fun verifyLocationPermissionPrompt(url: String) {
        assertTrue(mDevice.findObject(UiSelector().text("Allow $url to use your location?"))
            .waitForExists(waitingTime)
        )
        assertTrue(denyPagePermissionButton.text.equals("Don’t allow"))
        assertTrue(allowPagePermissionButton.text.equals("Allow"))
    }

    fun verifyNotificationsPermissionPrompt(url: String, blocked: Boolean = false) {
        if (!blocked) {
            assertTrue(
                mDevice.findObject(UiSelector().text("Allow $url to send notifications?"))
                    .waitForExists(waitingTime)
            )
            assertTrue(denyPagePermissionButton.text.equals("Never"))
            assertTrue(allowPagePermissionButton.text.equals("Always"))
        } else {
            // if "Never" was selected in a previous step,
                // the Notifications permission prompt won't be displayed anymore
            assertFalse(
                mDevice.findObject(UiSelector().text("Allow $url to send notifications?"))
                    .waitForExists(waitingTime)
            )
        }
    }

    class Transition {
        fun clickPagePermissionButton(allowed: Boolean, interact: BrowserRobot.() -> Unit): BrowserRobot.Transition {
            if (allowed) {
                allowPagePermissionButton.waitForExists(waitingTime)
                allowPagePermissionButton.click()
            } else {
                denyPagePermissionButton.waitForExists(waitingTime)
                denyPagePermissionButton.click()
            }

            BrowserRobot().interact()
            return BrowserRobot.Transition()
        }
    }
}

// Page permission prompts buttons
private val allowPagePermissionButton =
    mDevice.findObject(UiSelector().resourceId("$packageName:id/allow_button"))

private val denyPagePermissionButton =
    mDevice.findObject(UiSelector().resourceId("$packageName:id/deny_button"))
