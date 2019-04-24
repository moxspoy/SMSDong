![alt text](http://www.encuesta.biz/wp-content/uploads/2016/05/sms-icon-blue-300x300.png)

# SMSDong
An android based application to send multiple short message service (SMS) up to thousand times without sending via Intent. It is, simple, clear, and work perfectly.
This app development initiated when Indonesia held General Election. Someone (who was a candidate of Upper House Legislative (DPR RI) from East Jakarta. He want
to send promotion/campaign message to contact that he had.

# Before Use
1. Normally, android has limit to send only up to 30 SMS per 30 minutes. So you must install adb (ignore it if you have android studio). 
   Follow [this link](https://www.howtogeek.com/125769/how-to-install-and-use-abd-the-android-debug-bridge-utility/) to get tutorial on how to install adb on your computer
2. Activate your USB Debugging Mode. Follow [this link](https://www.companionlink.com/support/kb/Enable_Android_USB_Debugging_Mode)
3. Open terminal/command prompt, type ```adb devices```, if ADB recognized it, you can go to the next step.
4. type ```adb shell```, then  you can either change Android SMS limit and decrease or increase the number of SMS that could be sent in a 
   30 Minutes window. So enter the following command: ```settings put global sms_outgoing_check_max_count 1000```
5. You may also modify the time frame and change it accordingly using the following command: ```settings put global sms_outgoing_check_interval_ms 36000000```
6. Quit

# How to Use
1. Import your contact number in csv format. The content should look  like this
    ```08383838388,
    0882626272767,
    08373636362,
    ...
    083672683736```
2. Type your message body and press Send button. 
3. Now wait until android finished its work.

# Issue
1. Tested with 1000 SMS, app crash (because too heavy works android do). It should be splitted.
2. Only work for Android Lollipop and higher
3. Please send pull request or create issue if there are any problem

# Author
* [M Nurilman Baehaqi](https://instagram.com/moxspoy)
* [Computer Science FMIPA UNJ](https://unj.ac.id)
* All right reserved
