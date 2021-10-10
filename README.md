# VLBotRunner

Library helping you create socket bots. This helps you to schedule automatic tasks, manage their properties and execute
operations at runtime. Here are the key points for creating your bot.

### AbstractBotBuilder

This is your bot builder, for creating one for any account you want. You can specify :

- a list of expected login [properties](#botpropertydescriptor), these are the info you will need to use to log to the
  server. Usually a password.
- a list of [properties](#botpropertydescriptor) that will be used by your bot in any way you want, these may be of most
  primitive types and are usually used to determine you bot behavior. These will be accessible in the
  Bot.BotPropertyStore
- a list of [BotOperation](#botoperation) callable on your bot
- a list of initial [BotTask](#bottask) which will be run on your bot when calling bot.start()
- a [session manager](#abstractsessionmanager) builder

### BotPropertyDescriptor

This object will describe a property used by your bot, you'll specify :

- a key, which will also be the key in the `Map<String, String>` you'll pass when instantiating your bot
- a type, which will be the expected data type for this property (boolean, string, integer...)
- a default value, which will be set if you don't provide a value for this property
- a description of the property
- a boolean specifying if this property is needed or not (if not, a null value without any specified default value will
  be accepted)
- a boolean specifying if this property is nullable or not (if it is, a null value will be accepted even if the property
  is needed)
- a list of accepted values, empty to accept any value

### AbstractSessionManager

This object's goal is to manage your SessionHolder containing a session object, for example a token but anything you
need for accessing the website you're working on, and a WebClient (A Spring HTTP tool). This object's goal is to
automatically refresh it at the end of the given sessionDurability.

### BotTask

These are the main tasks of your bot. You need to implement the doExecute() method and return a TaskResult at the end,
which will determine when the task should be executed again, and which new tasks should be started. You can also start a
task on a bot at runtime using `bot.startTask(botTask: BotTask)`

### BotOperation

These are the operations callable at runtime and instantly executed by the bot. You can run a BotOperation on your bot
by using `BotOperationRunner.runOperation(...)` and specify the operation, the bot, and the needed params for the
operation to work.

### Shared properties

If you need to share some properties between tasks, you can use `Bot.sharedProperties` to store them, this is a
simple `Map<String, Any>` attached to your bot

### Start your bot

To start your bot, instantiate a BotBuilder, call `buildBot(...)` by passing two maps. These will contain expected
property values by their keys. Then, call `start()` on the created bot, and it will be alive !