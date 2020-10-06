# SafariNets
Capture mobs using spawn eggs and place them elsewhere, saving all of their data, including name, age, owner, inventory contents, and anything else!

## Configuration
There is one configuration file found in the plugins folder to set the custom model data for the net.

Configuration files:
- [config.yml](https://github.com/thebirmanator/SafariNets/blob/master/src/main/resources/config.yml "Config.yml")

## Commands and Permissions
There is only one command for this plugin (see permission below)! <br/>
Usage is `/givenet [player] <single_use|multi_use> [amount]` <br/>
> `player` is an optional player name to give to; if none is given, it defaults to who ran the command. <br/>
> `single_use` or `multi_use` is required and sets the type of net the player will be give. <br/>
> `amount` is an optional number determining the amount of nets to give to the player.

Permission | Description
--- | ---
`safarinets.command.give` | Allows access to `/givenet`
