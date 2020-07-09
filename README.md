# Omni
## What  is omni?
Omni provides a way to save, acess, manage and categorize pregenerated test data.<br/>
It provides a service for your automation tests and a web ui for your manual tests.
## Why should I use omni?
Test data generation takes a lot of time. Furthermore it's really error prone. <br/>
To generate test data you have to call multiple services.
If one service is temporarly unvailable or doesn't work properly, all of your test, which needs this application for data generation, will fail.
This can block testing in a big part of a company and delays releases.
Omni provides a way to generate test data in advance and retrieve it with just one call. So you can survive temprorary
down times of partner systems. 

As you need only one call in your test instead of multiple hundreds, your test will be way faster.
## How can i use omni?
Omnis api is defined via open api. So you can easily use it with all common languages.
For using omni you need three components.
* Omni
* An integration into your test framework <br/>
You can find a detailed explanation and an example implementation in test-client
* A way to generate data<br/>
You can find a detailed explanation and an example in data-generation client
## How to deploy?
