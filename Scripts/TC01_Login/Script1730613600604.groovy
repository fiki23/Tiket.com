import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

def email = findTestData("credentials").getValue("email", 1)
def password = findTestData("credentials").getValue("password", 1)

//login tiket.com
WebUI.openBrowser('https://account.bliblitiket.com/login?ref=https%3A%2F%2Fwww.tiket.com%2F&clientId=9dc79e3916a042abc86c2aa525bff009&device_id=1c98cf3d-7259-4d2d-bfd7-78f8fc8437cb&lang=id&utm_section=navigationBar%3Blogin_label&utm_logic=none')

//login page assertion
WebUI.verifyElementPresent(findTestObject('Object Repository/tiket.com/nomor-hp-atau-email_Input'), 10, FailureHandling.STOP_ON_FAILURE)

//email input
WebUI.setText(findTestObject('Object Repository/tiket.com/nomor-hp-atau-email_input'), email)

WebUI.click(findTestObject('Object Repository/tiket.com/submit_button'))

//otp input present assertion
WebUI.verifyElementPresent(findTestObject('Object Repository/tiket.com/otp/otpinput_1'), 10, FailureHandling.STOP_ON_FAILURE)

//open gmail

WebUI.executeJavaScript("window.open('https://mail.google.com/', '_blank')", [])

WebUI.switchToWindowIndex(1)

WebUI.click(findTestObject('Object Repository/gmail/masuk_span'))

WebUI.setText(findTestObject('Object Repository/gmail/identifierid_input'), email)

WebUI.click(findTestObject('Object Repository/gmail/span_next'))

WebUI.waitForPageLoad(4000)

WebUI.setText(findTestObject('Object Repository/gmail/password_input'),password)

WebUI.click(findTestObject('Object Repository/gmail/span_passwordNext'))

WebUI.waitForPageLoad(10)

//email list assertion
WebUI.verifyElementPresent(findTestObject('Object Repository/gmail/mailCard'), 10, FailureHandling.STOP_ON_FAILURE)

//select mail card
WebUI.click(findTestObject('Object Repository/gmail/mailCard'))

//get otp number
String otpNumber = WebUI.getText(findTestObject('Object Repository/gmail/otpRetrieve')).trim()
println("Retrieved OTP Code: " + otpNumber)

//verify otp number assertion
assert otpNumber.length() == 6 : "OTP length is not 6 digits"
assert otpNumber.isNumber() : "OTP does not consist of digits only"

//Input OTP

WebUI.switchToWindowIndex(0)

WebUI.waitForPageLoad(10)

WebUI.setText(findTestObject('Object Repository/tiket.com/otp/otpinput_1'), otpNumber[0].toString())

WebUI.setText(findTestObject('Object Repository/tiket.com/otp/otpinput_2'), otpNumber[1].toString())

WebUI.setText(findTestObject('Object Repository/tiket.com/otp/otpinput_3'), otpNumber[2].toString())

WebUI.setText(findTestObject('Object Repository/tiket.com/otp/otpinput_4'), otpNumber[3].toString())

WebUI.setText(findTestObject('Object Repository/tiket.com/otp/otpinput_5'), otpNumber[4].toString())

WebUI.setText(findTestObject('Object Repository/tiket.com/otp/otpinput_6'), otpNumber[5].toString())

WebUI.waitForPageLoad(10)
