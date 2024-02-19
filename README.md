# Google Analytics 4 Debugging with BrowserMob Proxy

This Repo demonstrates how you can receive GA4 analytics events when using a Mobile Browser / Mobile App on BrowserStack.

A typical url for google analytics event reporting looks like https://analytics.google.com/g/collect and has few query params associated with it. With this repo we add _dbg=1 along with other existing query params so that a debug event is reported to your google analytics dashboard.

## Setup

 - Java 17 or Higher
 - Maven

## Running the Program

Export your BrowserStack Access Key for BrowserStack Local
```shell
export BROWSERSTACK_ACCESS_KEY=<your browserstack access key>
```

Run the Program
```shell
mvn clean
mvn compile
mvn exec:java
```

Once the program starts you can go to [BrowserStack Live](https://live.browserstack.com) and start a session.
You should see, Force Local Enabled.